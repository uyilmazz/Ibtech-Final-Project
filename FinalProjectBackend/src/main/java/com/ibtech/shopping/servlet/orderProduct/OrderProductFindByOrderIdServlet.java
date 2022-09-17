package com.ibtech.shopping.servlet.orderProduct;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.ibtech.business.abstracts.OrderProductService;
import com.ibtech.business.concretes.OrderProductManager;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.business.xml.OrderProductXml;
import com.ibtech.core.utilities.helper.ParseHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.OrderProduct;
import com.ibtech.repository.OrderProductRepository;

@WebServlet("/api/orderProducts/findByOrderId")
public class OrderProductFindByOrderIdServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isLong = ParseHelper.isLong(request.getParameter("orderId"));
			Document document;
			if(!isLong) {
				Result result = new Result(false,ErrorResultMessage.RequestParameterError);
				document = XmlHelper.resultDocument(response, result, 400);
			}else {
				OrderProductService orderProductService = new OrderProductManager(new OrderProductRepository());
				long orderId = Long.parseLong(request.getParameter("orderId"));
				DataResult<List<OrderProduct>> result = orderProductService.getByOrderId(orderId);
				if(result.isSuccess()) {
					document = OrderProductXml.formatAll(result.getData());
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
