package com.ibtech.inventory.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.inventory.entities.Category;
import com.ibtech.inventory.entities.Product;



public class CategoryXml {
	public static Document format(Category category) throws Exception {
		Document document = XmlHelper.create("category");
		Element root = document.getDocumentElement();
		XmlHelper.addSingleElement(document, root, "categoryId", category.getCategoryId(), null, null);
		XmlHelper.addSingleElement(document, root, "categoryName", category.getCategoryName(), null, null);
		return document;
	}

	public static Document formatAll(List<Category> categories) throws Exception {
		Document document = XmlHelper.create("categories");
		Element root = document.getDocumentElement();
		for(int i = 0 ;i < categories.size();i++) {
			XmlHelper.addSingleElement(document, root, "category", null, "id", Integer.toString(categories.get(i).getCategoryId()));
			Element element = (Element)document.getElementsByTagName("category").item(i);
			XmlHelper.addSingleElement(document, element, "categoryName", categories.get(i).getCategoryName(), null, null);
		}
		return document;
	}
	
	public static Category parse(Document document) {
		Element root = document.getDocumentElement();
		return elementToCategory(root);

	}
	
	public static List<Category> parseList(Document document){
		List<Category> categoryList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("category");
		for(int i = 0; i < nodeList.getLength() ; i++) {
			Element categoryElement = (Element) nodeList.item(i);
			Category category = elementToCategory(categoryElement);
			categoryList.add(category);
		}
		return categoryList;
	}
	
	private static Category elementToCategory(Element element) {
		int categoryId = Integer.parseInt(element.getAttribute("id"));
		String categoryName = XmlHelper.getSingleElementText(element, "categoryName", "");
		Category category = new Category(categoryId,categoryName);
		return category;
	}
}
