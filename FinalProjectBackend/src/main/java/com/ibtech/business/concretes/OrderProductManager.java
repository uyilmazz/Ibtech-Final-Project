package com.ibtech.business.concretes;

import java.util.List;

import com.ibtech.business.abstracts.OrderProductService;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.business.contants.message.OrderResultMessage;
import com.ibtech.core.utilities.result.*;
import com.ibtech.entities.OrderProduct;
import com.ibtech.repository.OrderProductRepository;

public class OrderProductManager implements OrderProductService{

	private OrderProductRepository orderProductRepository;
	
	public OrderProductManager(OrderProductRepository orderProductRepository) {
		this.orderProductRepository = orderProductRepository;
	}

	@Override
	public DataResult<List<OrderProduct>> getAll() {
		try {
			return new SuccessDataResult<List<OrderProduct>>(orderProductRepository.getAll());
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<OrderProduct>>(ErrorResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<OrderProduct> getById(long orderProductId) {
		try {
			OrderProduct orderProduct = orderProductRepository.getById(orderProductId);
			return orderProduct != null ? new SuccessDataResult<OrderProduct>(orderProduct) : new ErrorDataResult<OrderProduct>(OrderResultMessage.OrderProductNotFound);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<OrderProduct>();
		}
	}

	@Override
	public DataResult<List<OrderProduct>> getByOrderId(long orderId) {
		try {
			return new SuccessDataResult<List<OrderProduct>>(orderProductRepository.getByOrderId(orderId));
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<OrderProduct>>(ErrorResultMessage.ErrorMessage);
		}
	}

}
