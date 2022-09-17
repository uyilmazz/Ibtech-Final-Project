package com.ibtech.business.xml;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.entities.Order;

public class OrderXml {
	public static Document format(Order order) throws Exception {
		Document document = XmlHelper.create("order");
		Element root = document.getDocumentElement();
		root.setAttribute("id", Long.toString(order.getId()));
		return formatHelper(document, root, order);
	}

	public static Document formatAll(List<Order> orders) throws Exception {
		Document document = XmlHelper.create("orders");
		Element root = document.getDocumentElement();
		for(int i = 0 ;i < orders.size();i++) {
			XmlHelper.addSingleElement(document, root, "order", null, "id", Long.toString(orders.get(i).getId()));
			Element element = (Element)document.getElementsByTagName("order").item(i);
			document = formatHelper(document, element, orders.get(i));
		}
		return document;
	}
	
	public static Order parse(Document document) {
		Element root = document.getDocumentElement();
		return parseHelper(root);

	}
	
	public static List<Order> parseList(Document document){
		List<Order> orderList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("order");
		for(int i = 0; i < nodeList.getLength() ; i++) {
			Element orderElement = (Element) nodeList.item(i);
			Order order = parseHelper(orderElement);
			orderList.add(order);
		}
		return orderList;
	}
	
	private static Document formatHelper(Document document,Element root,Order order) {
		XmlHelper.addSingleElement(document, root, "addressLine1", order.getAddressLine1(), null, null);
		XmlHelper.addSingleElement(document, root, "addressLine2", order.getAddressLine2(), null, null);
		XmlHelper.addSingleElement(document, root, "customerName", order.getCustomerName(), null, null);
		XmlHelper.addSingleElement(document, root, "totalAmount", order.getTotalAmount(), null, null);
		return document;
	}
	
	private static Order parseHelper(Element element) {
		long orderId = element.getAttribute("id") != "" ? Long.parseLong(element.getAttribute("id")) : 0;
		String addressLine1 = XmlHelper.getSingleElementText(element, "addressLine1", "");
		String addressLine2 = XmlHelper.getSingleElementText(element, "addressLine2", "");
		String userName = XmlHelper.getSingleElementText(element, "customerName", "");
		double totalAmount = XmlHelper.getSingleElementText(element, "totalAmount", 0);
		Order order = new Order(orderId,addressLine1,addressLine2,userName,totalAmount);
		return order;
	}
}	
