package com.ibtech.shopping.service;

import java.net.URLConnection;
import org.w3c.dom.Document;

import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.shopping.entities.Cart;
import com.ibtech.shopping.xml.CartXml;

public class CartService {
	
	public Cart createCart(Cart cart) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/carts/create";
			URLConnection connection = WebHelper.connect(address);
			Document document = CartXml.format(cart);
			XmlHelper.dump(document, connection.getOutputStream());
			Document responseDocument = XmlHelper.parse(connection.getInputStream());
			Cart responseCart = CartXml.parse(responseDocument);
			return responseCart;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Cart getById(long cartId) {
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/carts/find?cartId=%d", cartId);
			URLConnection connection = WebHelper.connect(address);
			Document document = XmlHelper.parse(connection.getInputStream());
			Cart cart = CartXml.parse(document);
			return cart;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
