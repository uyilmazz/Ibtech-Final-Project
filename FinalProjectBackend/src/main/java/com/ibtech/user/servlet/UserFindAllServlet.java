package com.ibtech.user.servlet;

import java.io.IOException;
import java.util.List;

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
import com.ibtech.entities.User;
import com.ibtech.repository.UserRepository;

@WebServlet("/api/users")
public class UserFindAllServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserService userService = new UserManager(new UserRepository());
			List<User> userList = userService.getAll().getData();
			Document responseDocument = UserXml.formatAll(userList);
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(responseDocument, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
