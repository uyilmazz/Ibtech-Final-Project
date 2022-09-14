package com.ibtech.shopping.service;
import java.net.HttpURLConnection;
import java.util.List;
import org.w3c.dom.Document;
import com.ibtech.core.utilities.helper.*;
import com.ibtech.core.utilities.result.*;
import com.ibtech.core.xml.ResultXml;
import com.ibtech.shopping.entities.CartProduct;
import com.ibtech.shopping.xml.CartProductXml;

public class CartProductService {
	
	public DataResult<CartProduct> addCartProduct(CartProduct cartProduct) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/cartProducts/create";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document requestDocument = CartProductXml.format(cartProduct);
			XmlHelper.dump(requestDocument, connection.getOutputStream());
			Document responseDocument = null;
			if( connection.getResponseCode() == 200) {
				responseDocument = XmlHelper.parse(connection.getInputStream());
				CartProduct responseCartProduct = CartProductXml.parse(responseDocument);
				return new SuccessDataResult<CartProduct>(responseCartProduct);
			}else {
				responseDocument = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(responseDocument);
				return new ErrorDataResult<CartProduct>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<CartProduct>();
		}
	}
	
	public DataResult<List<CartProduct>> getByCartId(long cartId){
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/cart/find/cartProducts?cartId=%d", cartId);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				List<CartProduct> cartProductList = CartProductXml.parseList(document);
				return new SuccessDataResult<List<CartProduct>>(cartProductList);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<List<CartProduct>>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<List<CartProduct>>();
		}
	}
	
	public Result updateBulkCartProduct(List<CartProduct> cartProducts) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/cartProducts/updateBulk";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document requestDocument = CartProductXml.formatAll(cartProducts);
			XmlHelper.dump(requestDocument, connection.getOutputStream());
			Document responseDocument = null;
			if( connection.getResponseCode() == 200) {
				responseDocument = XmlHelper.parse(connection.getInputStream());
				Result responseCartProduct = ResultXml.parse(responseDocument);
				return new SuccessResult(responseCartProduct.getMessage());
			}else {
				responseDocument = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(responseDocument);
				return new ErrorResult(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorResult();
		}
	}
	
	public Result removeCartProduct(long cartProductId){
		try {
			System.out.println(cartProductId);
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/cartProducts/delete?cartProductId=%d", cartProductId);
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
