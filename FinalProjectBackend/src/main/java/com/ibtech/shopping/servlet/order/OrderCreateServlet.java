package com.ibtech.shopping.servlet.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.ibtech.business.abstracts.OrderService;
import com.ibtech.business.concretes.OrderManager;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.business.xml.OrderXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Order;
import com.ibtech.repository.OrderRepository;

@WebServlet("/api/orders/create")
public class OrderCreateServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			OrderService orderService = new OrderManager(new OrderRepository());
			Document document = XmlHelper.parse(request.getInputStream());
			Document responseDocument;
			if(document != null) {
				Order order = OrderXml.parse(document);
				Result result = orderService.create(order);	
				if(!result.isSuccess()) {
					responseDocument = XmlHelper.resultDocument(response, result, 400);
				}else {
					responseDocument = XmlHelper.resultDocument(response, result, 200);
				}
			}else {
				Result result = new Result(false,ErrorResultMessage.XMLParseError);
				responseDocument = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(responseDocument, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
