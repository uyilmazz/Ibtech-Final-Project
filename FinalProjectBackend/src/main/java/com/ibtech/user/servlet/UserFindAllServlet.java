package com.ibtech.user.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.ibtech.business.abstracts.CartProductService;
import com.ibtech.business.abstracts.UserService;
import com.ibtech.business.concretes.CartProductManager;
import com.ibtech.business.concretes.UserManager;
import com.ibtech.business.xml.CartProductXml;
import com.ibtech.business.xml.UserXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.entities.CartProduct;
import com.ibtech.entities.User;
import com.ibtech.repository.CartProductRepository;
import com.ibtech.repository.UserRepository;

@WebServlet("/api/users")
public class UserFindAllServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserService userService = new UserManager(new UserRepository());
			DataResult<List<User>> result = userService.getAll();
			Document document;
			if(result.isSuccess()) {
				document = UserXml.formatAll(result.getData());
				response.setStatus(200);
			}else {
				document = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

