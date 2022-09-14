package com.ibtech.inventory.servlet.category;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.ibtech.business.abstracts.CategoryService;
import com.ibtech.business.concretes.CategoryManager;
import com.ibtech.business.xml.CategoryXml;
import com.ibtech.business.xml.ResultXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.entities.Category;
import com.ibtech.repository.CategoryRepository;

@WebServlet("/api/categories")
public class CategoryFindAllServlet  extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CategoryService categoryManager = new CategoryManager(new CategoryRepository());
			DataResult<List<Category>> result = categoryManager.getAll();
			Document document;
			if(result.isSuccess()) {
				document = CategoryXml.formatAll(result.getData());
				response.setStatus(200);
			}else {
				document = XmlHelper.resultDocument(response, result,HttpServletResponse.SC_BAD_REQUEST);;
			}
			XmlHelper.dump(document, response.getOutputStream());
			response.setContentType("application/xml;charset=UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
