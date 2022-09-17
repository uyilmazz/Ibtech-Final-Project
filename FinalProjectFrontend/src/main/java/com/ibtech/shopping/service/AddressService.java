package com.ibtech.shopping.service;

import java.net.HttpURLConnection;
import java.util.List;
import org.w3c.dom.Document;
import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.*;
import com.ibtech.core.xml.ResultXml;
import com.ibtech.shopping.entities.Address;
import com.ibtech.shopping.xml.AddressXml;

public class AddressService {
	
	public DataResult<List<Address>> getAllByUserName(String userName){
		try {
			String address = String.format("http://localhost:8080/FinalProjectBackend/api/addresses/findByCustomerName?customerName=%s", userName);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				List<Address> addressList = AddressXml.parseList(document);
				return new SuccessDataResult<List<Address>>(addressList);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<List<Address>>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<List<Address>>();
		}
	}
	
	public DataResult<Address> getById(long addressId){
		try {
			String url = String.format("http://localhost:8080/FinalProjectBackend/api/addresses/find?addressId=%d", addressId);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(url);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				Address address = AddressXml.parse(document);
				return new SuccessDataResult<Address>(address);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<Address>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<Address>();
		}
	}
	
	public Result createAddress(Address address) {
		try {
			String url = "http://localhost:8080/FinalProjectBackend/api/addresses/create";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(url);
			Document requestDocument = AddressXml.format(address);
			XmlHelper.dump(requestDocument, connection.getOutputStream());
			Document responseDocument = null;
			if( connection.getResponseCode() == 200) {
				responseDocument = XmlHelper.parse(connection.getInputStream());
				Result result = ResultXml.parse(responseDocument);
				return new SuccessResult(result.getMessage());
			}else {
				responseDocument = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(responseDocument);
				return new ErrorResult(result.getMessage());
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorResult();
		}
	}
}
