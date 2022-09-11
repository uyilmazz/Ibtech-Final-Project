package com.ibtech.user.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.jasper.tagplugins.jstl.core.Url;
import org.w3c.dom.Document;

import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.user.entities.User;
import com.ibtech.user.xml.UserXml;

public class UserService {

	public User register(User user) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/users/create";
			URLConnection  connection =  WebHelper.connect(address);
			Document document = UserXml.format(user);
			XmlHelper.dump(document, connection.getOutputStream());
			if(((HttpURLConnection) connection).getResponseCode() == 201) {
				Document responseDocument = XmlHelper.parse(connection.getInputStream());
				User responseUser = UserXml.parse(responseDocument);
				return responseUser;
			}
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User login(User user) {
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/users/check";
			URLConnection connection = WebHelper.connect(address);
			Document document = UserXml.format(user);
			XmlHelper.dump(document, connection.getOutputStream());
			if(((HttpURLConnection) connection).getResponseCode() == 200) {
				Document responseDocument = XmlHelper.parse(connection.getInputStream());
				User responseUser = UserXml.parse(responseDocument);
				return responseUser;
			}
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
