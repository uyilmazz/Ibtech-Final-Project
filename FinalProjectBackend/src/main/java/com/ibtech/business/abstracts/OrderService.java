package com.ibtech.business.abstracts;

import java.util.List;

import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Order;

public interface OrderService {
	public DataResult<List<Order>> getAll();
	public DataResult<Order> getById(long orderId);
	public DataResult<Integer> getCount();
	public DataResult<List<Order>> getByCustomerName(String userName);
	public Result create(Order order);
	public Result update(Order order);
}
