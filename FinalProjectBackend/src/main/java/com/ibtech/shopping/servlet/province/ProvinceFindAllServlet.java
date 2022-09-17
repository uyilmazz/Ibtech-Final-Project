package com.ibtech.shopping.servlet.province;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import com.ibtech.business.abstracts.ProvinceService;
import com.ibtech.business.concretes.ProvinceManager;
import com.ibtech.business.xml.ProvinceXml;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.entities.Province;
import com.ibtech.repository.ProvinceRepository;

@WebServlet("/api/provinces")
public class ProvinceFindAllServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ProvinceService provinceService = new ProvinceManager(new ProvinceRepository());
			DataResult<List<Province>> result = provinceService.getAll();
			Document document;
			if(result.isSuccess()) {
				document = ProvinceXml.formatAll(result.getData());
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
