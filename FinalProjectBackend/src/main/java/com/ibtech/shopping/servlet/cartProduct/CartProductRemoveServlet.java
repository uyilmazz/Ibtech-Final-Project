package com.ibtech.shopping.servlet.cartProduct;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibtech.business.abstracts.CartProductService;
import com.ibtech.business.concretes.CartProductManager;
import com.ibtech.core.utilities.helper.StreamHelper;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.repository.CartProductRepository;

@WebServlet("/api/cartProducts/delete")
public class CartProductRemoveServlet extends HttpServlet {
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CartProductService cartProductService = new CartProductManager(new CartProductRepository());
			long cartProductId = Long.parseLong(request.getParameter("cartProductId"));
			Result deletedResult  = cartProductService.delete(cartProductId);
			response.setContentType("text/plain");
			StreamHelper.write(response.getOutputStream(),deletedResult.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
