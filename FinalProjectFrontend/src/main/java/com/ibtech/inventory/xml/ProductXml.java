package com.ibtech.inventory.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.inventory.entities.Category;
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
		long productId = element.getAttribute("id") != "" ? Long.parseLong(element.getAttribute("id")) : 0;
		String productName = XmlHelper.getSingleElementText(element, "productName", "");
		double salesPrice = Double.parseDouble(XmlHelper.getSingleElementText(element, "salesPrice", "0"));
		String imagePath = XmlHelper.getSingleElementText(element, "imagePath", "");
		Element categoryElement = (Element) element.getElementsByTagName("category").item(0);
		int categoryId = Integer.parseInt(categoryElement.getAttribute("id"));
		String categoryName = XmlHelper.getSingleElementText(categoryElement, "categoryName", "");
		Category category = new Category(categoryId,categoryName);
		Product product = new Product(productId,productName,imagePath,salesPrice);
		product.setCategory(category);
		return product;
	}
	
}
