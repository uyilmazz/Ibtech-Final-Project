package com.ibtech.business.xml;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.entities.Cart;

public class CartXml {
	public static Document format(Cart cart) throws Exception {
		Document document = XmlHelper.create("cart");
		Element root = document.getDocumentElement();
		root.setAttribute("id", Long.toString(cart.getId()));
		XmlHelper.addSingleElement(document, root, "customerName", cart.getCustomerName(), null, null);
		XmlHelper.addSingleElement(document, root, "totalAmount", cart.getTotalAmount(), null, null);
		return document;
	}

	public static Document formatAll(List<Cart> carts) throws Exception {
		Document document = XmlHelper.create("carts");
		Element root = document.getDocumentElement();
		for (int i = 0; i < carts.size(); i++) {
			XmlHelper.addSingleElement(document, root, "cart", null, "id", Long.toString(carts.get(i).getId()));
			Element element = (Element) document.getElementsByTagName("cart").item(i);
			XmlHelper.addSingleElement(document, element, "customerName", carts.get(i).getCustomerName(), null, null);
			XmlHelper.addSingleElement(document, element, "totalAmount", carts.get(i).getTotalAmount(), null, null);
		}
		return document;
	}

	public static Cart parse(Document document) {
		Element root = document.getDocumentElement();
		long cartId = root.getAttribute("id") != "" ? Long.parseLong(root.getAttribute("id")) : 0;
		String customerName = XmlHelper.getSingleElementText(root, "customerName", "");
		double totalAmount = XmlHelper.getSingleElementText(root, "totalAmount", 0);
		Cart cart = new Cart(cartId, customerName, totalAmount);
		return cart;
	}

}
