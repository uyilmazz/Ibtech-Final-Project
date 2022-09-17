package com.ibtech.business.xml;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.entities.Address;
import com.ibtech.entities.Province;


public class AddressXml {
	public static Document format(Address address) throws Exception {
		Document document = XmlHelper.create("address");
		Element root = document.getDocumentElement();
		root.setAttribute("id", Long.toString(address.getId()));
		return formatHelper(document, root, address);
	}

	public static Document formatAll(List<Address> addresses) throws Exception {
		Document document = XmlHelper.create("adresses");
		Element root = document.getDocumentElement();
		for(int i = 0 ;i < addresses.size();i++) {
			XmlHelper.addSingleElement(document, root, "address", null, "id", Long.toString(addresses.get(i).getId()));
			Element element = (Element)document.getElementsByTagName("address").item(i);
			document = formatHelper(document, element, addresses.get(i));
		}
		return document;
	}
	
	public static Address parse(Document document) {
		Element root = document.getDocumentElement();
		return parseHelper(root);

	}
	
	public static List<Address> parseList(Document document){
		List<Address> addressList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("address");
		for(int i = 0; i < nodeList.getLength() ; i++) {
			Element addressElement = (Element) nodeList.item(i);
			Address address = parseHelper(addressElement);
			addressList.add(address);
		}
		return addressList;
	}
	
	private static Document formatHelper(Document document,Element root,Address address) {
		XmlHelper.addSingleElement(document, root, "addressLine1", address.getAddressLine1(), null, null);
		XmlHelper.addSingleElement(document, root, "addressLine2", address.getAddressLine2(), null, null);
		XmlHelper.addSingleElement(document, root, "userName", address.getUserName(), null, null);
		XmlHelper.addSingleElement(document, root, "province", null, "id", Integer.toString(address.getProvince().getId()));
		Element provinceElement = (Element)root.getElementsByTagName("province").item(0);
		XmlHelper.addSingleElement(document, provinceElement, "provinceName", address.getProvince().getName(), null, null);
		return document;
	}
	
	private static Address parseHelper(Element element) {
		long addressId = element.getAttribute("id") != "" ? Long.parseLong(element.getAttribute("id")) : 0;
		String addressLine1 = XmlHelper.getSingleElementText(element, "addressLine1", "");
		String addressLine2 = XmlHelper.getSingleElementText(element, "addressLine2", "");
		String userName = XmlHelper.getSingleElementText(element, "userName", "");
		Element provinceElement = (Element) element.getElementsByTagName("province").item(0);
		int provinceId = Integer.parseInt(provinceElement.getAttribute("id"));
		String provinceName = XmlHelper.getSingleElementText(provinceElement, "provinceName", "");
		Province province = new Province(provinceId,provinceName);
		Address address = new Address(addressId,addressLine1,addressLine2,userName);
		address.setProvince(province);
		return address;
	}
}
