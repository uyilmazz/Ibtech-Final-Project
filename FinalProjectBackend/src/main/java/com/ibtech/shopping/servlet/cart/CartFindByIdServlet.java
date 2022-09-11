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
import com.ibtech.entities.Cart;
import com.ibtech.repository.CartRepository;

@WebServlet("/api/carts/find")
public class CartFindByIdServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CartService cartService = new CartManager(new CartRepository());
			long cartId = Long.parseLong(request.getParameter("cartId"));
			Cart cart = cartService.getById(cartId).getData();
			Document responseDocument = CartXml.format(cart);
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(responseDocument, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
