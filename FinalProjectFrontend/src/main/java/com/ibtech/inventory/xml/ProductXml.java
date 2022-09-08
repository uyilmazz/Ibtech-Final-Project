package com.ibtech.inventory.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
		return document;
	}
	
	public static Document formatAll(List<Product> products) throws Exception {
		Document document = XmlHelper.create("products");
		Element root = document.getDocumentElement();
		for(int i = 0 ;i < products.size();i++) {
			XmlHelper.addSingleElement(document, root, "product", null, "id", Long.toString(products.get(i).getProductId()));
			Element element = (Element)document.getElementsByTagName("product").item(i);
			XmlHelper.addSingleElement(document, element, "productName", products.get(i).getProductName(), null, null);
			XmlHelper.addSingleElement(document, element, "imagePath", products.get(i).getImagePath(), null, null);
			XmlHelper.addSingleElement(document, element, "salesPrice", products.get(i).getSalesPrice(), null, null);
		}
		return document;
	}
	
	public static Product parse(Document document) {
		Element root = document.getDocumentElement();
		return elementToProduct(root);

	}
	
	public static List<Product> parseList(Document document){
		List<Product> productList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("product");
		for(int i = 0; i < nodeList.getLength() ; i++) {
			Element productElement = (Element) nodeList.item(i);
			Product product = elementToProduct(productElement);
			productList.add(product);
		}
		return productList;
	}
	
	private static Product elementToProduct(Element element) {
		long productId = Long.parseLong(element.getAttribute("id"));
		String productName = XmlHelper.getSingleElementText(element, "productName", "");
		double salesPrice = Double.parseDouble(XmlHelper.getSingleElementText(element, "salesPrice", "0"));
		String imagePath = XmlHelper.getSingleElementText(element, "imagePath", "");
		Product product = new Product(productId,productName,imagePath,salesPrice);
		return product;
	}
	
}
