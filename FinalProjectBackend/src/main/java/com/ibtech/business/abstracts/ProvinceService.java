package com.ibtech.business.abstracts;

import java.util.List;

import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.entities.Province;

public interface ProvinceService {
	DataResult<List<Province>> getAll();
	DataResult<Province> getById(int provinceId);
}
