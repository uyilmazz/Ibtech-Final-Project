package com.ibtech.inventory.service;

import java.net.HttpURLConnection;
import java.util.List;

import org.w3c.dom.Document;
import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.ErrorDataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.core.utilities.result.SuccessDataResult;
import com.ibtech.core.xml.ResultXml;
import com.ibtech.inventory.entities.Product;
import com.ibtech.inventory.xml.ProductXml;

public class ProductService {

	public DataResult<List<Product>> getAll() {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/products";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				List<Product> productList = ProductXml.parseList(document);
				return new SuccessDataResult<List<Product>>(productList);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<List<Product>>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<List<Product>>();
		}
	}
	
	public DataResult<List<Product>> getLimitedData(int limit){
		try {
			String address = String.format("http://localhost:8080/FinalProjectBackend/api/products/limited?limit=%d", limit);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				List<Product> productList = ProductXml.parseList(document);
				return new SuccessDataResult<List<Product>>(productList);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<List<Product>>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<List<Product>>();
		}
	}
	
	public DataResult<List<Product>> getByCategoryId(int categoryId) {
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/products/findByCategory?categoryId=%d", categoryId);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				List<Product> productList = ProductXml.parseList(document);
				return new SuccessDataResult<List<Product>>(productList);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<List<Product>>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<List<Product>>();
		}
	}
	
	public DataResult<Product> getById(long productId) {	
		try {
			String address = String.format("http://localhost:8080/FinalProjectBackend/api/products/find?productId=%d", productId);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				Product product = ProductXml.parse(document);
				return new SuccessDataResult<Product>(product);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<Product>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<Product>();
		}
	}
}
