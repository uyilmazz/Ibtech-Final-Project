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
import com.ibtech.entities.CartProduct;
import com.ibtech.repository.CartProductRepository;

@WebServlet("/api/cart/find/cartProducts")
public class CartProductFindByCartId extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			CartProductService cartProductService = new CartProductManager(new CartProductRepository());
			long cartId = Long.parseLong(request.getParameter("cartId"));
			List<CartProduct> cartProduct = cartProductService.getByCartId(cartId).getData();
			Document responseDocument = CartProductXml.formatAll(cartProduct);
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(responseDocument, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
