package com.ibtech.shopping.servlet.cartProduct;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.ibtech.business.abstracts.CartProductService;
import com.ibtech.business.concretes.CartProductManager;
import com.ibtech.business.xml.CartProductXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.entities.CartProduct;
import com.ibtech.repository.CartProductRepository;

@WebServlet("/api/cartProducts")
public class CartProductFindAllServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CartProductService cartProductService = new CartProductManager(new CartProductRepository());
			DataResult<List<CartProduct>> result = cartProductService.getAll();
			Document document;
			if(result.isSuccess()) {
				document = CartProductXml.formatAll(result.getData());
				response.setStatus(200);
			}else {
				document = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
