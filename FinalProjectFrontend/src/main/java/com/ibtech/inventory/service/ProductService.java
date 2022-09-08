package com.ibtech.inventory.service;

import java.net.URLConnection;
import java.util.List;

import org.w3c.dom.Document;
import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.inventory.entities.Product;
import com.ibtech.inventory.xml.ProductXml;

public class ProductService {

	public List<Product> getAll() {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/products";
			URLConnection connection = WebHelper.connect(address);
			Document document = XmlHelper.parse(connection.getInputStream());
			List<Product> productList = ProductXml.parseList(document);
			return productList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Product> getByCategoryId(int categoryId) {
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/products/findByCategory?categoryId=%d", categoryId);
			URLConnection connection = WebHelper.connect(address);
			Document document = XmlHelper.parse(connection.getInputStream());
			List<Product> productList = ProductXml.parseList(document);
			return productList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Product getById(long productId) {
		try {
			String address = String.format("http://localhost:8080/FinalProjectBackend/api/products/find?productId=%d", productId);
			URLConnection connection = WebHelper.connect(address);
			Document document = XmlHelper.parse(connection.getInputStream());
			Product product = ProductXml.parse(document);
			return product;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
