package com.ibtech.business.xml;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.entities.OrderProduct;

public class OrderProductXml {
	public static Document format(OrderProduct orderProduct) throws Exception {
		Document document = XmlHelper.create("orderProduct");
		Element root = document.getDocumentElement();
		root.setAttribute("id", Long.toString(orderProduct.getId()));
		return formatHelper(document, root, orderProduct);
	}

	public static Document formatAll(List<OrderProduct> orderProducts) throws Exception {
		Document document = XmlHelper.create("orderProducts");
		Element root = document.getDocumentElement();
		for(int i = 0 ;i < orderProducts.size();i++) {
			XmlHelper.addSingleElement(document, root, "orderProduct", null, "id", Long.toString(orderProducts.get(i).getId()));
			Element element = (Element)document.getElementsByTagName("orderProduct").item(i);
			document = formatHelper(document, element, orderProducts.get(i));
		}
		return document;
	}
	
	public static OrderProduct parse(Document document) {
		Element root = document.getDocumentElement();
		return parseHelper(root);

	}
	
	public static List<OrderProduct> parseList(Document document){
		List<OrderProduct> orderProductList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("orderProduct");
		for(int i = 0; i < nodeList.getLength() ; i++) {
			Element orderProductElemenet = (Element) nodeList.item(i);
			OrderProduct orderProduct = parseHelper(orderProductElemenet);
			orderProductList.add(orderProduct);
		}
		return orderProductList;
	}
	
	private static Document formatHelper(Document document,Element root,OrderProduct orderProduct) {
		XmlHelper.addSingleElement(document, root, "orderId", orderProduct.getOrderId(), null, null);
		XmlHelper.addSingleElement(document, root, "productName", orderProduct.getProductName(), null, null);
		XmlHelper.addSingleElement(document, root, "imagePath", orderProduct.getImagePath(), null, null);
		XmlHelper.addSingleElement(document, root, "salesPrice", orderProduct.getSalesPrice(), null, null);
		XmlHelper.addSingleElement(document, root, "salesQuantity", orderProduct.getSalesQuantity(), null, null);
		XmlHelper.addSingleElement(document, root, "taxRate", orderProduct.getTaxRate(), null, null);
		XmlHelper.addSingleElement(document, root, "lineAmount", orderProduct.getLineAmount(), null, null);
		return document;
	}
	
	private static OrderProduct parseHelper(Element element) {
		long id = element.getAttribute("id") != "" ? Long.parseLong(element.getAttribute("id")) : 0;
		long orderId = (long) XmlHelper.getSingleElementText(element, "orderId", 0);
		String productName = XmlHelper.getSingleElementText(element, "productName", "");
		String imagePath = XmlHelper.getSingleElementText(element, "imagePath", "");
		double salesPrice = XmlHelper.getSingleElementText(element, "salesPrice", 0);
		int salesQuantity =  (int) Double.parseDouble(XmlHelper.getSingleElementText(element, "salesQuantity","0"));
		double taxRate = XmlHelper.getSingleElementText(element, "taxRate", 0);
		double lineAmount = XmlHelper.getSingleElementText(element, "lineAmount", 0);
		OrderProduct orderProduct = new OrderProduct(id,orderId,productName,imagePath,salesPrice,salesQuantity,taxRate,lineAmount);
		return orderProduct;
	}
}
