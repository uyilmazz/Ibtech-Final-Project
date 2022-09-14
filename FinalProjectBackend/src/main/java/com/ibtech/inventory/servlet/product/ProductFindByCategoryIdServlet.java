package com.ibtech.inventory.servlet.product;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.ibtech.business.abstracts.ProductService;
import com.ibtech.business.concretes.ProductManager;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.business.xml.ResultXml;
import com.ibtech.business.xml.ProductXml;
import com.ibtech.core.utilities.helper.ParseHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Product;
import com.ibtech.repository.ProductRepository;

@WebServlet("/api/products/findByCategory")
public class ProductFindByCategoryIdServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isInteger = ParseHelper.isInteger(request.getParameter("categoryId"));
			Document document;
			if(!isInteger) {
				Result result = new Result(false,ErrorResultMessage.RequestParameterError);
				document =XmlHelper.resultDocument(response, result, 400);
			}else {
				ProductService productService = new ProductManager(new ProductRepository());
				int categoryId = Integer.parseInt(request.getParameter("categoryId"));
				DataResult<List<Product>> result = productService.getByCategory(categoryId);
				if(result.isSuccess()) {
					document = ProductXml.formatAll(result.getData());
					response.setStatus(200);
				}else {
					document =XmlHelper.resultDocument(response, result, 400);
				}
			}
			XmlHelper.dump(document, response.getOutputStream());
			response.setContentType("application/xml;charset=UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}