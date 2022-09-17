package com.ibtech.business.abstracts;

import java.util.List;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Address;

public interface AddressService {
	public DataResult<List<Address>> getAll();
	public DataResult<List<Address>> getAllByCustomerName(String customerName);
	public DataResult<Address> getById(long addressId);
	public Result add(Address address);
	public Result update(Address address);
	public Result remove(long id);
}
