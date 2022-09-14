package com.ibtech.business.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.entities.CartProduct;
import com.ibtech.entities.Category;
import com.ibtech.entities.Product;

public class CartProductXml {
	public static Document format(CartProduct cartProduct) throws Exception {
		Document document = XmlHelper.create("cartProduct");
		Element root = document.getDocumentElement();
		root.setAttribute("id",Long.toString(cartProduct.getId()));
		return formatHelper(document, root, cartProduct);
	}
	
	public static Document formatAll(List<CartProduct> cartProducts) throws Exception {
		Document document = XmlHelper.create("cartProducts");
		Element root = document.getDocumentElement();
		for(int i = 0 ;i < cartProducts.size();i++) {
			XmlHelper.addSingleElement(document, root, "cartProduct", null, "id", Long.toString(cartProducts.get(i).getId()));
			Element element = (Element)document.getElementsByTagName("cartProduct").item(i);
			document = formatHelper(document, element, cartProducts.get(i));
		}
		return document;
	}
	
	public static List<CartProduct> parseList(Document document){
		List<CartProduct> cartProducts = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList products = root.getElementsByTagName("cartProduct");
		for(int i = 0; i < products.getLength() ; i++) {
			Element cartProductElement = (Element) products.item(i);
			CartProduct cartProduct = parseHelper(cartProductElement);
			cartProducts.add(cartProduct);
		}
		return cartProducts;
	}
	
	public static CartProduct parse(Document document) {
		Element root = document.getDocumentElement();
		return parseHelper(root);
	}
	
	private static CartProduct parseHelper(Element cartProductElement) {
		long id = cartProductElement.getAttribute("id") != "" ? Long.parseLong(cartProductElement.getAttribute("id")) : 0;
		long cartId = (long) XmlHelper.getSingleElementText(cartProductElement, "cartId", 0);
		double salesPrice = XmlHelper.getSingleElementText(cartProductElement, "salesPrice", 0);
		int salesQuantity = (int) Double.parseDouble(XmlHelper.getSingleElementText(cartProductElement, "salesQuantity","0"));
		double taxRate = XmlHelper.getSingleElementText(cartProductElement, "taxRate", 0);
		double lineAmount = XmlHelper.getSingleElementText(cartProductElement, "lineAmount", 0);
		
		Element productElement = (Element)cartProductElement.getElementsByTagName("product").item(0);
		long productId = Long.parseLong(productElement.getAttribute("id"));
		String productName = XmlHelper.getSingleElementText(productElement, "productName", "");
		double productSalesPrice = XmlHelper.getSingleElementText(productElement, "productSalesPrice", 0);
		String productImagePath = XmlHelper.getSingleElementText(productElement, "productImagePath", "");
		Product product = new Product(productId,productName,productImagePath,productSalesPrice);
		
		Element categoryElement = (Element)productElement.getElementsByTagName("category").item(0);
		int categoryId = Integer.parseInt(categoryElement.getAttribute("id"));
		String categoryName = XmlHelper.getSingleElementText(categoryElement, "categoryName", "");
		Category category = new Category(categoryId,categoryName);
		product.setCategory(category);
		CartProduct cartProduct = new CartProduct(id,cartId,salesPrice,salesQuantity,taxRate,lineAmount);
		cartProduct.setProduct(product);
		return cartProduct;
	}
	
	private static Document formatHelper(Document document,Element root,CartProduct cartProduct) {
		XmlHelper.addSingleElement(document, root, "cartId", cartProduct.getCartId(), null, null);
		XmlHelper.addSingleElement(document, root, "salesPrice", cartProduct.getSalesPrice(), null, null);
		XmlHelper.addSingleElement(document, root, "salesQuantity", cartProduct.getSalesQuantity(), null, null);
		XmlHelper.addSingleElement(document, root, "taxRate", cartProduct.getTaxRate(), null, null);
		XmlHelper.addSingleElement(document, root, "lineAmount", cartProduct.getLineAmount(), null, null);

		XmlHelper.addSingleElement(document, root, "product", null, "id", Long.toString(cartProduct.getProduct().getProductId()));
		Element productElement = (Element)root.getElementsByTagName("product").item(0);
		Product product = cartProduct.getProduct();
		XmlHelper.addSingleElement(document, productElement, "productName", product.getProductName(), null, null);
		XmlHelper.addSingleElement(document, productElement, "productSalesPrice", product.getSalesPrice(), null, null);
		XmlHelper.addSingleElement(document, productElement, "productImagePath", product.getImagePath(), null, null);
		
		Category category = cartProduct.getProduct().getCategory();
		XmlHelper.addSingleElement(document, productElement, "category", null, "id", Integer.toString(category.getCategoryId()));
		Element categoryElement = (Element) productElement.getElementsByTagName("category").item(0);
		XmlHelper.addSingleElement(document, categoryElement, "categoryName", category.getCategoryName(), null, null);
		return document;
	}
	
}
