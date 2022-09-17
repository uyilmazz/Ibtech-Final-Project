package com.ibtech.shopping.service;

import java.net.HttpURLConnection;
import java.util.List;

import org.w3c.dom.Document;

import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.*;
import com.ibtech.core.xml.ResultXml;
import com.ibtech.shopping.entities.OrderProduct;
import com.ibtech.shopping.xml.OrderProductXml;

public class OrderProductService {
	
	public DataResult<List<OrderProduct>> getByOrderId(long orderId){
		try {
			String address = String.format(
					"http://localhost:8080/FinalProjectBackend/api/orderProducts/findByOrderId?orderId=%d", orderId);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				List<OrderProduct> orderProductList = OrderProductXml.parseList(document);
				return new SuccessDataResult<List<OrderProduct>>(orderProductList);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<List<OrderProduct>>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<List<OrderProduct>>();
		}		
	}
}
