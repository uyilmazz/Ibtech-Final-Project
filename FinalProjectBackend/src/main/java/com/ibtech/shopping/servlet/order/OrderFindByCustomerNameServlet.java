package com.ibtech.shopping.servlet.order;

import java.io.IOException;
import java.util.List;

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
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Order;
import com.ibtech.repository.OrderRepository;

@WebServlet("/api/orders/findByCustomerName")
public class OrderFindByCustomerNameServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String customerName = request.getParameter("customerName");
			Document document;
			if(customerName == null) {
				Result result = new Result(false,ErrorResultMessage.RequestParameterError);
				document = XmlHelper.resultDocument(response, result, 400);
			}else {
				OrderService orderService = new OrderManager(new OrderRepository());
				DataResult<List<Order>> result = orderService.getByCustomerName(customerName);
				if(result.isSuccess()) {
					document = OrderXml.formatAll(result.getData());
					response.setStatus(200);
				}else {
					document = XmlHelper.resultDocument(response, result, 400);
				}
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
