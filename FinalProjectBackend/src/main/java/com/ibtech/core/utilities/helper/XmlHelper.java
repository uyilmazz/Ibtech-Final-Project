package com.ibtech.core.utilities.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHelper {
	private static DocumentBuilderFactory documentBuilderFactory;
	private static DocumentBuilderFactory getDocumentBuilderFactory() {
		if(documentBuilderFactory == null) {
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
		}
		return documentBuilderFactory;
	}
	
	private static TransformerFactory transformerFactory;
	private static TransformerFactory getTransformerFactory() {
		if(transformerFactory == null) {
			transformerFactory = TransformerFactory.newInstance();
		}
		return transformerFactory;
	}
	
	public static Document create(String root) throws Exception {
		DocumentBuilder builder = getDocumentBuilderFactory().newDocumentBuilder();
		Document document = builder.newDocument();
		Element element = document.createElement(root);
		document.appendChild(element);
		return document;
	}
		
	public static void dump(Document document,OutputStream out) throws TransformerException, IOException  {
		Transformer transformer =  getTransformerFactory().newTransformer();
		DOMSource data = new DOMSource(document);
		StreamResult result = new StreamResult(out);
		transformer.transform(data, result);
		out.close();
	}
	
	public static Document parse(InputStream in) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = getDocumentBuilderFactory().newDocumentBuilder();
		Document document = builder.parse(in);
		return document;
	}
	
	public static String getSingleElementText(Element parent,String tag,String defaultValue) {
		NodeList elementList = parent.getElementsByTagName(tag);
		if(elementList.getLength() == 0) {
			return defaultValue;
		}
		return elementList.item(0).getTextContent();
	}
	
	public static double getSingleElementText(Element parent,String tag,double defaultValue) {
		String string = getSingleElementText(parent,tag,Double.toString(defaultValue));
		return Double.parseDouble(string);
	}
	
	public static void addSingleElement(Document document,Element parent,String tag,String content,String attribute,String attributeValue) {
		Element element = document.createElement(tag);
		if(content != null) {
			element.setTextContent(content);
		}
		
		if(attribute != null) {
			element.setAttribute(attribute, attributeValue);
		}
		
		if(parent == null) {
			document.appendChild(element);
		}else {
			parent.appendChild(element);
		}
	}
	
	public static void addSingleElement(Document document,Element parent,String tag,double content,String attribute,String attributeValue) {
		addSingleElement(document, parent, tag, Double.toString(content),attribute,attributeValue);
	}
}
