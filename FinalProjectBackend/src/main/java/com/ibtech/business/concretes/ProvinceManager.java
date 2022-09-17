package com.ibtech.business.concretes;

import java.sql.SQLException;
import java.util.List;
import com.ibtech.business.abstracts.ProvinceService;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.business.contants.message.ProvinceResultMessage;
import com.ibtech.core.utilities.result.*;
import com.ibtech.entities.Province;
import com.ibtech.repository.ProvinceRepository;

public class ProvinceManager implements ProvinceService{

	private ProvinceRepository provinceRepository;

	public ProvinceManager(ProvinceRepository provinceRepository) {
		this.provinceRepository = provinceRepository;
	}

	@Override
	public DataResult<List<Province>> getAll() {
		try {
			return new SuccessDataResult<List<Province>>(provinceRepository.getAll());
		} catch (SQLException e) {
			return new ErrorDataResult<List<Province>>(ErrorResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<Province> getById(int provinceId) {
		try {
			Province province = provinceRepository.findById(provinceId);
			return province != null ? new SuccessDataResult<Province>(province) : new ErrorDataResult<Province>(ProvinceResultMessage.ProvinceNotFound);
		} catch (SQLException e) {
			return new ErrorDataResult<Province>(ErrorResultMessage.ErrorMessage);
		}
	}

}
