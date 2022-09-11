package com.ibtech.shopping.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.inventory.entities.Category;
import com.ibtech.inventory.entities.Product;
import com.ibtech.shopping.entities.CartProduct;

public class CartProductXml {
	public static Document format(CartProduct cartProduct) throws Exception {
		Document document = XmlHelper.create("cartProduct");
		Element root = document.getDocumentElement();
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
	
	public static CartProduct parse(Document document) {
		Element root = document.getDocumentElement();
		CartProduct cartProduct = parseHelper(root);
		return cartProduct;
	}
	
	public static List<CartProduct> parseList(Document document){
		List<CartProduct> cartProductList = new ArrayList<>();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("cartProduct");
		for(int i = 0; i < nodeList.getLength(); i++) {
			Element product = (Element)nodeList.item(i);
			CartProduct cartProduct = parseHelper(product);
			cartProductList.add(cartProduct);
		}
		return cartProductList;
	}
	
	private static CartProduct parseHelper(Element root) {
		long id = root.getAttribute("id") != "" ? Long.parseLong(root.getAttribute("id")) : 0;
		long cartId = (long) XmlHelper.getSingleElementText(root, "cartId", 0);
		double salesPrice = XmlHelper.getSingleElementText(root, "salesPrice", 0);
		int salesQuantity = (int) Double.parseDouble(XmlHelper.getSingleElementText(root, "salesQuantity","0"));
		double taxRate = XmlHelper.getSingleElementText(root, "taxRate", 0);
		double lineAmount = XmlHelper.getSingleElementText(root, "lineAmount", 0);
		
		Element productElement = (Element)root.getElementsByTagName("product").item(0);
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
