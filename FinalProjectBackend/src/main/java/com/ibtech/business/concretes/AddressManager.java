package com.ibtech.business.concretes;

import java.util.List;

import com.ibtech.business.abstracts.AddressService;
import com.ibtech.business.contants.message.AddressResultMessage;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.core.utilities.result.*;
import com.ibtech.entities.Address;
import com.ibtech.repository.AddressRepository;

public class AddressManager implements AddressService{
	
	private AddressRepository addressRespository;
	
	public AddressManager(AddressRepository addressRespository) {
		this.addressRespository = addressRespository;
	}

	@Override
	public DataResult<List<Address>> getAll() {
		try {
			return new SuccessDataResult<List<Address>>(addressRespository.getAll());
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<Address>>(ErrorResultMessage.ErrorMessage);
		}
	}
	
	@Override
	public DataResult<List<Address>> getAllByCustomerName(String customerName) {
		try {
			return new SuccessDataResult<List<Address>>(addressRespository.getAllByCustomerName(customerName));
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<Address>>(ErrorResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<Address> getById(long addressId) {
		try {
			Address address = addressRespository.getById(addressId);
			return address != null ? new SuccessDataResult<Address>(address) : new ErrorDataResult<Address>(AddressResultMessage.AddressNotFound);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<Address>(ErrorResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result add(Address address) {
		try {
			boolean createdResult = addressRespository.add(address);
			return createdResult ? new SuccessResult(AddressResultMessage.AddressCreated) : new ErrorResult(AddressResultMessage.AddressNotCreated);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorResult(ErrorResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result update(Address address) {
		try {
			boolean updatedResult = addressRespository.update(address);
			return updatedResult ? new SuccessResult(AddressResultMessage.AddressUpdated) : new ErrorResult(AddressResultMessage.AddressCouldNotUpdated);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorResult(ErrorResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result remove(long id) {
		try {
			DataResult<Address> result = getById(id);
			if(result.isSuccess()) {
				boolean updatedResult = addressRespository.remove(result.getData());
				return updatedResult ? new SuccessResult(AddressResultMessage.AddressDeleted) : new ErrorResult(AddressResultMessage.AddressCouldNotDeleted);
			}
			return new ErrorResult(AddressResultMessage.AddressNotFound);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorResult(ErrorResultMessage.ErrorMessage);
		}
	}

}
