package com.ibtech.inventory.servlet.category;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.inventory.business.abstracts.CategoryService;
import com.ibtech.inventory.business.concretes.CategoryManager;
import com.ibtech.inventory.business.xml.CategoryXml;
import com.ibtech.inventory.entities.Category;
import com.ibtech.inventory.repository.CategoryRepository;

@WebServlet("/api/categories")
public class CategoryFindAllServlet  extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CategoryService categoryManager = new CategoryManager(new CategoryRepository());
			DataResult<List<Category>> result = categoryManager.getAll();
			Document document = CategoryXml.formatAll(result.getData());
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
