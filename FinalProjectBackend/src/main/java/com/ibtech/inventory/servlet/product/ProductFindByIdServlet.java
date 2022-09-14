package com.ibtech.inventory.servlet.product;

import java.io.IOException;

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

@WebServlet("/api/products/find")
public class ProductFindByIdServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isLong = ParseHelper.isLong(request.getParameter("productId"));
			Document document;
			if(!isLong) {
				Result result = new Result(false,ErrorResultMessage.RequestParameterError);
				document = ResultXml.format(result);
				response.setStatus(400);
			}else {
				ProductService productManager = new ProductManager(new ProductRepository());
				long productId = Long.parseLong(request.getParameter("productId"));
				DataResult<Product> result = productManager.getById(productId);
				if(result.isSuccess()) {
					document = ProductXml.format(result.getData());
					response.setStatus(200);
				}else {
					document =XmlHelper.resultDocument(response, result, 400);
				}
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
