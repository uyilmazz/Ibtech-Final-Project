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
import com.ibtech.business.xml.CartXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.entities.Cart;
import com.ibtech.repository.CartRepository;

@WebServlet("/api/carts/create")
public class CartCreateServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("create cart servlet");
			CartService cartService = new CartManager(new CartRepository());
			Document document = XmlHelper.parse(request.getInputStream());
			Cart cart = CartXml.parse(document);
			DataResult<Long> result = cartService.create(cart);
			Cart createdCart = cartService.getById(result.getData()).getData();
			Document responseDocument = CartXml.format(createdCart);
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(responseDocument, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
