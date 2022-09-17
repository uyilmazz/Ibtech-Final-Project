package com.ibtech.shopping.service;

import java.net.HttpURLConnection;
import java.util.List;
import org.w3c.dom.Document;
import com.ibtech.core.utilities.helper.WebHelper;
import com.ibtech.core.utilities.helper.XmlHelper;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.ErrorDataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.core.utilities.result.SuccessDataResult;
import com.ibtech.core.xml.ResultXml;
import com.ibtech.shopping.entities.Province;
import com.ibtech.shopping.xml.ProvinceXml;

public class ProvinceService {	

	public DataResult<List<Province>> getAll(){
		try {
			String address = "http://localhost:8080/FinalProjectBackend/api/provinces";
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				List<Province> provinceList = ProvinceXml.parseList(document);
				return new SuccessDataResult<List<Province>>(provinceList);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<List<Province>>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<List<Province>>();
		}
	}
	
	public DataResult<Province> getById(int provinceId){
		try {
			String address = String.format("http://localhost:8080/FinalProjectBackend/api/provinces/find?provinceId=%d", provinceId);
			HttpURLConnection connection = (HttpURLConnection) WebHelper.connect(address);
			Document document = null;
			if( connection.getResponseCode() == 200) {
				document = XmlHelper.parse(connection.getInputStream());
				Province province = ProvinceXml.parse(document);
				return new SuccessDataResult<Province>(province);
			}else {
				document = XmlHelper.parse(connection.getErrorStream());
				Result result = ResultXml.parse(document);
				return new ErrorDataResult<Province>(result.getMessage());
			}
		}catch(Exception e) {
			return new ErrorDataResult<Province>();
		}
	}
}
