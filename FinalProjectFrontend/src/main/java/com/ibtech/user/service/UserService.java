package com.ibtech.user.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.jasper.tagplugins.jstl.core.Url;
import org.w3c.dom.Document;

import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.*;
import com.ibtech.core.xml.ResultXml;
import com.ibtech.shopping.entities.Cart;
import com.ibtech.shopping.xml.CartXml;
import com.ibtech.user.entities.User;
import com.ibtech.user.xml.UserXml;

public class UserService {

	public DataResult<User> register(User user) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/users/create";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document requestDocument = UserXml.format(user);
			XmlHelper.dump(requestDocument, connection.getOutputStream());
			Document responseDocument = null;
			if(connection.getResponseCode() == 200) {
				responseDocument = XmlHelper.parse(connection.getInputStream());
				User responseUser = UserXml.parse(responseDocument);
				return new SuccessDataResult<User>(responseUser);
			}else {
				responseDocument = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(responseDocument);
				return new ErrorDataResult<User>(result.getMessage());
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public DataResult<User> login(User user) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/users/check";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document requestDocument = UserXml.format(user);
			XmlHelper.dump(requestDocument, connection.getOutputStream());
			Document responseDocument = null;
			if(connection.getResponseCode() == 200) {
				responseDocument = XmlHelper.parse(connection.getInputStream());
				User responseUser = UserXml.parse(responseDocument);
				return new SuccessDataResult<User>(responseUser);
			}else {
				responseDocument = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(responseDocument);
				return new ErrorDataResult<User>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<User>();
		}
	}
}




