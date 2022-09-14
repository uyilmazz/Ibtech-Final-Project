package com.ibtech.shopping.servlet.cart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.ibtech.business.abstracts.CartService;
import com.ibtech.business.concretes.CartManager;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.business.xml.CartXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Cart;
import com.ibtech.repository.CartRepository;

@WebServlet("/api/carts/create")
public class CartCreateServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CartService cartService = new CartManager(new CartRepository());
			Document document = XmlHelper.parse(request.getInputStream());
			Document responseDocument;
			if(document != null) {
				Cart cart = CartXml.parse(document);
				DataResult<Cart> result = cartService.create(cart);	
				if(!result.isSuccess()) {
					responseDocument = XmlHelper.resultDocument(response, result, 400);
				}else {
					responseDocument = CartXml.format(result.getData());
					response.setStatus(200);
				}
			}else {
				Result result = new Result(false,ErrorResultMessage.XMLParseError);
				responseDocument = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(responseDocument, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
