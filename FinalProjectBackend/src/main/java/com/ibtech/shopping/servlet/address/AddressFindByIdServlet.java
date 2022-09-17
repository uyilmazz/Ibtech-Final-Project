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
import com.ibtech.core.utilities.helper.ParseHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Address;
import com.ibtech.repository.AddressRepository;

@WebServlet("/api/addresses/find")
public class AddressFindByIdServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isLong = ParseHelper.isLong(request.getParameter("addressId"));
			Document document;
			if(!isLong) {
				Result result = new Result(false,ErrorResultMessage.RequestParameterError);
				document = XmlHelper.resultDocument(response, result, 400);
			}else {
				AddressService addressService = new AddressManager(new AddressRepository());
				long addressId = Long.parseLong(request.getParameter("addressId"));
				DataResult<Address> result = addressService.getById(addressId);
				if(result.isSuccess()) {
					document = AddressXml.format(result.getData());
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
