package com.ibtech.inventory.service;

import java.net.HttpURLConnection;
import java.util.List;
import org.w3c.dom.Document;
import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.*;
import com.ibtech.core.xml.ResultXml;
import com.ibtech.inventory.entities.Category;
import com.ibtech.inventory.xml.CategoryXml;

public class CategoryService {
	
	public DataResult<List<Category>> getAll(){
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/categories";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				List<Category> categoryList = CategoryXml.parseList(document);
				return new SuccessDataResult<List<Category>>(categoryList);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<List<Category>>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<List<Category>>();
		}
	}
}


