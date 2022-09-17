package com.ibtech.business.concretes;

import java.util.List;

import com.ibtech.business.abstracts.OrderService;
import com.ibtech.business.contants.message.ErrorResultMessage;
import com.ibtech.business.contants.message.OrderResultMessage;
import com.ibtech.core.utilities.result.*;
import com.ibtech.entities.Order;
import com.ibtech.repository.OrderRepository;

public class OrderManager implements OrderService{

	private OrderRepository orderRepository;
	
	public OrderManager(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Override
	public DataResult<List<Order>> getAll() {
		try {
			return new SuccessDataResult<List<Order>>(orderRepository.getAll());
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<Order>>(ErrorResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<Order> getById(long orderId) {
		try {
			Order order = orderRepository.getById(orderId);
			return order != null ? new SuccessDataResult<Order>(order) : new ErrorDataResult<Order>(OrderResultMessage.OrderNotFound);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<Order>(ErrorResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<List<Order>> getByCustomerName(String customerName) {
		try {
			return new SuccessDataResult<List<Order>>(orderRepository.getByCustomerName(customerName));
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<Order>>(ErrorResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result create(Order order) {
		try {
			boolean createdResult = orderRepository.create(order);
			return createdResult ? new SuccessResult(OrderResultMessage.OrderCreated) : new ErrorResult(OrderResultMessage.OrderCouldNotCreated); 
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorResult(ErrorResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result update(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<Integer> getCount() {
		try {
			return new SuccessDataResult<Integer>(orderRepository.getCount());
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<Integer>();
		}
	}

}
