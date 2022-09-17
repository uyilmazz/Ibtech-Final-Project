package com.ibtech.shopping.service;
import java.net.HttpURLConnection;
import org.w3c.dom.Document;
import com.ibtech.core.utilities.helper.*;
import com.ibtech.core.utilities.result.*;
import com.ibtech.core.xml.ResultXml;
import com.ibtech.shopping.entities.Cart;
import com.ibtech.shopping.xml.CartXml;

public class CartService {
	
	public DataResult<Cart> createCart(Cart cart) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/carts/create";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document requestDocument = CartXml.format(cart);
			XmlHelper.dump(requestDocument, connection.getOutputStream());
			Document responseDocument = null;
			if( connection.getResponseCode() == 200) {
				responseDocument = XmlHelper.parse(connection.getInputStream());
				Cart responseCart = CartXml.parse(responseDocument);
				return new SuccessDataResult<Cart>(responseCart);
			}else {
				responseDocument = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(responseDocument);
				System.out.println(result.getMessage() + " " + result.isSuccess());
				return new ErrorDataResult<Cart>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<Cart>();
		}
	}
	
	public DataResult<Cart> getById(long cartId) {
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/carts/find?cartId=%d", cartId);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				Cart cart = CartXml.parse(document);
				return new SuccessDataResult<Cart>(cart);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<Cart>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<Cart>();
		}
	}
	
	public DataResult<Cart> getByCustomerName(String customerName){
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/carts/findByCustomerName?customerName=%s", customerName);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				Cart cart = CartXml.parse(document);
				return new SuccessDataResult<Cart>(cart);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<Cart>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<Cart>();
		}
	}
	
	public DataResult<Cart> updateCart(Cart cart) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/carts/update";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document requestDocument = CartXml.format(cart);
			XmlHelper.dump(requestDocument, connection.getOutputStream());
			Document responseDocument = null;
			if( connection.getResponseCode() == 200) {
				responseDocument = XmlHelper.parse(connection.getInputStream());
				Cart responseCart = CartXml.parse(responseDocument);
				return new SuccessDataResult<Cart>(responseCart);
			}else {
				responseDocument = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(responseDocument);
				return new ErrorDataResult<Cart>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<Cart>();
		}
	}
	
	public Result deleteCart(long cartId) {
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/carts/remove?cartId=%d", cartId);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				Result result = ResultXml.parse(document);
				return new SuccessResult(result.getMessage());
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorResult(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorResult();
		}
	}
}
