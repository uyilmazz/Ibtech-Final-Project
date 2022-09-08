package com.ibtech.inventory.service;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.inventory.entities.Category;
import com.ibtech.inventory.xml.CategoryXml;

public class CategoryService {
	
	public List<Category> getAll(){
		List<Category> categoryList = new ArrayList<>();
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/categories";
			URLConnection connection = WebHelper.connect(address);
			Document document = XmlHelper.parse(connection.getInputStream());
			categoryList = CategoryXml.parseList(document);
			return categoryList;
		}catch(Exception e) {
			e.printStackTrace();
			return categoryList;
		}
	}
}
