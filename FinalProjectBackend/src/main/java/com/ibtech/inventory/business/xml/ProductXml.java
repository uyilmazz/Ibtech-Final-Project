package com.ibtech.inventory.business.xml;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.inventory.entities.Product;

public class ProductXml {
	public static Document format(Product product) throws Exception {
		Document document = XmlHelper.create("product");
		Element root = document.getDocumentElement();
		root.setAttribute("id", Long.toString(product.getProductId()));
		XmlHelper.addSingleElement(document, root, "productName", product.getProductName(), null, null);
		XmlHelper.addSingleElement(document, root, "imagePath", product.getImagePath(), null, null);
		XmlHelper.addSingleElement(document, root, "salesPrice", product.getSalesPrice(), null, null);
		XmlHelper.addSingleElement(document, root, "category", null, "id", Integer.toString(product.getCategory().getCategoryId()));
		Element categoryElement = (Element)root.getElementsByTagName("category").item(0);
		XmlHelper.addSingleElement(document, categoryElement, "categoryName", product.getCategory().getCategoryName(), null, null);
		return document;
	}

	public static Document formatAll(List<Product> products) throws Exception {
		Document document = XmlHelper.create("products");
		Element root = document.getDocumentElement();
		for(int i = 0 ;i < products.size();i++) {
			XmlHelper.addSingleElement(document, root, "product", null, "id", Long.toString(products.get(i).getProductId()));
			Element productElement = (Element)root.getElementsByTagName("product").item(i);
			XmlHelper.addSingleElement(document, productElement, "productName", products.get(i).getProductName(), null, null);
			XmlHelper.addSingleElement(document, productElement, "imagePath", products.get(i).getImagePath(), null, null);
			XmlHelper.addSingleElement(document, productElement, "salesPrice", products.get(i).getSalesPrice(), null, null);
			XmlHelper.addSingleElement(document, productElement, "category", null, "id", Integer.toString(products.get(i).getCategory().getCategoryId()));
			Element categoryElement = (Element)productElement.getElementsByTagName("category").item(0);
			XmlHelper.addSingleElement(document, categoryElement, "categoryName", products.get(i).getCategory().getCategoryName(), null, null);
		}
		return document;
	}
}
