package com.ibtech.shopping.servlet.address;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.ibtech.business.abstracts.AddressService;
import com.ibtech.business.concretes.AddressManager;
import com.ibtech.business.xml.AddressXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.entities.Address;
import com.ibtech.repository.AddressRepository;


@WebServlet("/api/addresses")
public class AddressFindAllServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			AddressService addressService = new AddressManager(new AddressRepository());
			DataResult<List<Address>> result = addressService.getAll();
			Document document;
			if(result.isSuccess()) {
				document = AddressXml.formatAll(result.getData());
				response.setStatus(200);
			}else {
				document = XmlHelper.resultDocument(response, result, 400);
			}
			response.setContentType("application/xml;charset=UTF-8");
			XmlHelper.dump(document, response.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
