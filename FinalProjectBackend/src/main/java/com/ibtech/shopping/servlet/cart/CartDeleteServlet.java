package com.ibtech.shopping.servlet.cart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.ibtech.business.abstracts.CartService;
import com.ibtech.business.concretes.CartManager;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.core.utilities.helper.ParseHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.repository.CartRepository;

@WebServlet("/api/carts/remove")
public class CartDeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isLong = ParseHelper.isLong(request.getParameter("cartId"));
			Document document;
			if(!isLong) {
				Result result = new Result(false,ErrorResultMessage.RequestParameterError);
				document = XmlHelper.resultDocument(response, result, 400);
			}else {
				CartService cartService = new CartManager(new CartRepository());
				long cartId = Long.parseLong(request.getParameter("cartId"));
				Result result = cartService.delete(cartId);
				if(result.isSuccess()) {
					document = XmlHelper.resultDocument(response, result, 200);
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
