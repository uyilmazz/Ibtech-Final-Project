package com.ibtech.business.abstracts;

import java.util.List;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.entities.OrderProduct;

public interface OrderProductService {
	public DataResult<List<OrderProduct>> getAll();
	public DataResult<OrderProduct> getById(long orderProductId);
	public DataResult<List<OrderProduct>> getByOrderId(long orderId);
}
