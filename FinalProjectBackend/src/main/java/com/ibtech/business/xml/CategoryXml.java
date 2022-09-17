package com.ibtech.business.xml;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.entities.Category;

public class CategoryXml {
	public static Document format(Category category) throws Exception {
		Document document = XmlHelper.create("category");
		Element root = document.getDocumentElement();
		root.setAttribute("id", Integer.toString(category.getCategoryId()));
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
}
