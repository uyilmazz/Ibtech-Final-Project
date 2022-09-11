package com.ibtech.shopping.service;

import java.net.URLConnection;
import java.util.List;

import org.w3c.dom.Document;

import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.shopping.entities.CartProduct;
import com.ibtech.shopping.xml.CartProductXml;

public class CartProductService {
	
	public CartProduct addCartProduct(CartProduct cartProduct) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/cartProducts/create";
			URLConnection connection = WebHelper.connect(address);
			Document document = CartProductXml.format(cartProduct);
			XmlHelper.dump(document, connection.getOutputStream());
			Document responseDocument = XmlHelper.parse(connection.getInputStream());
			CartProduct responseCartProduct = CartProductXml.parse(responseDocument);
			return responseCartProduct;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<CartProduct> getByCartId(long cartId){
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/cart/find/cartProducts?cartId=%d", cartId);
			URLConnection connection = WebHelper.connect(address);
			Document document = XmlHelper.parse(connection.getInputStream());
			List<CartProduct> cartProductList = CartProductXml.parseList(document);
			return cartProductList;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
