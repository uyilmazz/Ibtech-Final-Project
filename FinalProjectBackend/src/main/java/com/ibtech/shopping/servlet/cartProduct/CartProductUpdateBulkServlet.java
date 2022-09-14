package com.ibtech.shopping.servlet.cartProduct;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.ibtech.business.abstracts.CartProductService;
import com.ibtech.business.concretes.CartProductManager;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.business.xml.CartProductXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.CartProduct;
import com.ibtech.repository.CartProductRepository;

@WebServlet("/api/cartProducts/updateBulk")
public class CartProductUpdateBulkServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CartProductService cartProductManager = new CartProductManager(new CartProductRepository());
			Document document = XmlHelper.parse(request.getInputStream());
			Document responseDocument;
			if(document != null) {
				List<CartProduct> cartProducts = CartProductXml.parseList(document);
				for(CartProduct cartProduct : cartProducts) {
					System.out.println(cartProduct.getCartId());
					System.out.println(cartProduct.getSalesPrice());
				}
				Result createdResult = cartProductManager.updateBulk(cartProducts);
				if(!createdResult.isSuccess()) {
					responseDocument = XmlHelper.resultDocument(response, createdResult, 400);
				}else {
					responseDocument = XmlHelper.resultDocument(response, createdResult, 200);
				}
			}else {
				Result result = new Result(false,ErrorResultMessage.XMLParseError);
				responseDocument = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(responseDocument, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
