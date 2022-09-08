package com.ibtech.inventory.servlet.product;

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
import com.ibtech.inventory.business.abstracts.ProductService;
import com.ibtech.inventory.business.concretes.ProductManager;
import com.ibtech.inventory.business.xml.ProductXml;
import com.ibtech.inventory.entities.Product;
import com.ibtech.inventory.repository.ProductRepository;

@WebServlet("/api/products/findByCategory")
public class ProductFindByCategoryIdServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ProductService productManager = new ProductManager(new ProductRepository());
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			DataResult<List<Product>> result = productManager.getByCategory(categoryId);
			Document document = ProductXml.formatAll(result.getData());
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}