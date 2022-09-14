package com.ibtech.shopping.servlet.cartProduct;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.ibtech.business.abstracts.CartProductService;
import com.ibtech.business.concretes.CartProductManager;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.core.utilities.helper.ParseHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.repository.CartProductRepository;

@WebServlet("/api/cartProducts/delete")
public class CartProductRemoveServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("Delete Servlet");
			boolean isLong = ParseHelper.isLong(request.getParameter("cartProductId"));
			Document document;
			if(isLong) {
				CartProductService cartProductService = new CartProductManager(new CartProductRepository());
				long cartProductId = Long.parseLong(request.getParameter("cartProductId"));
				System.out.println(cartProductId);
				Result deletedResult  = cartProductService.delete(cartProductId);
				if(deletedResult.isSuccess()) {
					document = XmlHelper.resultDocument(response, deletedResult, 200);
				}else {
					document = XmlHelper.resultDocument(response, deletedResult, 400);
				}
			}else {
				Result result = new Result(false,ErrorResultMessage.RequestParameterError);
				document = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}


