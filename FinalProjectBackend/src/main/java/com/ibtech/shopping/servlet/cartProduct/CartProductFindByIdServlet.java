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
import com.ibtech.business.xml.CartProductXml;
import com.ibtech.core.utilities.helper.ParseHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.CartProduct;
import com.ibtech.repository.CartProductRepository;

@WebServlet("/api/cartProducts/find")
public class CartProductFindByIdServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isLong = ParseHelper.isLong(request.getParameter("cartProductId"));
			Document document;
			if(!isLong) {
				Result result = new Result(false,ErrorResultMessage.RequestParameterError);
				document = XmlHelper.resultDocument(response, result, 400);
			}else {
				CartProductService cartProductService = new CartProductManager(new CartProductRepository());
				long cartProductId = Long.parseLong(request.getParameter("cartProductId"));
				DataResult<CartProduct> result = cartProductService.getById(cartProductId);
				if(result.isSuccess()) {
					document = CartProductXml.format(result.getData());
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
