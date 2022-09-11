package com.ibtech.user.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.ibtech.business.abstracts.UserService;
import com.ibtech.business.concretes.UserManager;
import com.ibtech.business.xml.UserXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.entities.User;
import com.ibtech.repository.UserRepository;

@WebServlet("/api/users/create")
public class UserCreateServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserService userService = new UserManager(new UserRepository());
			Document document = XmlHelper.parse(request.getInputStream());
			User user = UserXml.parse(document);
			DataResult<Long> createdResult = userService.add(user);
			if(createdResult.isSuccess()) {
				DataResult<User> result = userService.getById(createdResult.getData());
				Document responseDocument = UserXml.format(result.getData());
				response.setContentType("application/xml;charset=UTF-8");
				response.setStatus(201);
				XmlHelper.dump(responseDocument, response.getOutputStream());
			}
			response.setStatus(400);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
