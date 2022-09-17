package com.ibtech.shopping.servlet.address;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.ibtech.business.abstracts.AddressService;
import com.ibtech.business.concretes.AddressManager;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.business.xml.AddressXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Address;
import com.ibtech.repository.AddressRepository;

@WebServlet("/api/addresses/create")
public class AddressCreateServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			AddressService cartService = new AddressManager(new AddressRepository());
			Document document = XmlHelper.parse(request.getInputStream());
			Document responseDocument;
			if(document != null) {
				Address address = AddressXml.parse(document);
				Result result = cartService.add(address);	
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
