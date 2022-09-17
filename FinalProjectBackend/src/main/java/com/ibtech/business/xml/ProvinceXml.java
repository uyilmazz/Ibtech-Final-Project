package com.ibtech.business.xml;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.entities.Province;

public class ProvinceXml {
	public static Document format(Province province) throws Exception {
		Document document = XmlHelper.create("province");
		Element root = document.getDocumentElement();
		root.setAttribute("id", Long.toString(province.getId()));
		return formatHelper(document, root, province);
	}

	public static Document formatAll(List<Province> provinces) throws Exception {
		Document document = XmlHelper.create("provinces");
		Element root = document.getDocumentElement();
		for(int i = 0 ;i < provinces.size();i++) {
			XmlHelper.addSingleElement(document, root, "province", null, "id", Long.toString(provinces.get(i).getId()));
			Element element = (Element)document.getElementsByTagName("province").item(i);
			document = formatHelper(document, element, provinces.get(i));
		}
		return document;
	}
	
	public static Province parse(Document document) {
		Element root = document.getDocumentElement();
		return parseHelper(root);

	}
	
	public static List<Province> parseList(Document document){
		List<Province> provinceList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("province");
		for(int i = 0; i < nodeList.getLength() ; i++) {
			Element provinceElement = (Element) nodeList.item(i);
			Province province = parseHelper(provinceElement);
			provinceList.add(province);
		}
		return provinceList;
	}
	
	private static Document formatHelper(Document document,Element root,Province province) {
		XmlHelper.addSingleElement(document, root, "name", province.getName(), null, null);
		return document;
	}
	
	private static Province parseHelper(Element element) {
		int provinceId =element.getAttribute("id") != "" ? Integer.parseInt(element.getAttribute("id")) : 0;
		String name = XmlHelper.getSingleElementText(element, "name", "");
		Province province = new Province(provinceId,name);
		return province;
	}
}
