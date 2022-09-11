package com.ibtech.user.xml;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.user.entities.User;

public class UserXml {
	public static Document format(User user) throws Exception {
		Document document = XmlHelper.create("user");
		Element root = document.getDocumentElement();
		root.setAttribute("id", Long.toString(user.getId()));
		XmlHelper.addSingleElement(document, root, "name", user.getName(), null, null);
		XmlHelper.addSingleElement(document, root, "password", user.getPassword(), null, null);
		return document;
	}
	
	public static Document formatAll(List<User> users) throws Exception {
		Document document = XmlHelper.create("users");
		Element root = document.getDocumentElement();
		for(int i = 0 ;i < users.size();i++) {
			XmlHelper.addSingleElement(document, root, "user", null, "id", Long.toString(users.get(i).getId()));
			Element element = (Element)document.getElementsByTagName("user").item(i);
			XmlHelper.addSingleElement(document, element, "name", users.get(i).getName(), null, null);
			XmlHelper.addSingleElement(document, element, "password", users.get(i).getPassword(), null, null);
		}
		return document;
	}
	
	public static User parse(Document document) {
		Element root = document.getDocumentElement();
		long id = root.getAttribute("id") != "" ? Long.parseLong(root.getAttribute("id")) : 0;
		String name = XmlHelper.getSingleElementText(root, "name", "");
		String password = XmlHelper.getSingleElementText(root, "password", "");
		User user = new User(id,name,password);
		return user;
	}
}
