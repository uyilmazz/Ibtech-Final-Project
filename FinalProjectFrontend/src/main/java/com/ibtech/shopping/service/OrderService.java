package com.ibtech.shopping.service;

import java.net.HttpURLConnection;
import java.util.List;

import org.w3c.dom.Document;

import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.*;
import com.ibtech.core.xml.ResultXml;
import com.ibtech.shopping.entities.Order;
import com.ibtech.shopping.xml.OrderXml;

public class OrderService {

	public Result createOrder(Order order) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/orders/create";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document requestDocument = OrderXml.format(order);
			XmlHelper.dump(requestDocument, connection.getOutputStream());
			Document responseDocument = null;
			if( connection.getResponseCode() == 200) {
				responseDocument = XmlHelper.parse(connection.getInputStream());
				Result result = ResultXml.parse(responseDocument);;
				return new SuccessResult(result.getMessage());
			}else {
				responseDocument = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(responseDocument);
				return new ErrorResult(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorResult();
		}
	}
	
	public DataResult<Order> getById(long orderId) {
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/orders/find?orderId=%d", orderId);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				Order order = OrderXml.parse(document);
				return new SuccessDataResult<Order>(order);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<Order>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<Order>();
		}
	}
	
	public DataResult<List<Order>> getByCustomerName(String customerName){
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/orders/findByCustomerName?customerName=%s", customerName);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				List<Order> orderList = OrderXml.parseList(document);
				return new SuccessDataResult<List<Order>>(orderList);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<List<Order>>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<List<Order>>();
		}
	}
}
