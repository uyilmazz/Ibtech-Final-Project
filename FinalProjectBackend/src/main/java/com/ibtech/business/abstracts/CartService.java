package com.ibtech.business.abstracts;

import java.util.List;

import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Cart;

public interface CartService {
	public DataResult<List<Cart>> getAll();
	public DataResult<Cart> getById(long cartId);
	public DataResult<Cart> getByUserName(String userName);
	public DataResult<Cart> create(Cart cart);
	public Result update(Cart cart);
	
}
