package com.ibtech.business.concretes;

import java.util.List;

import com.ibtech.business.abstracts.CartService;
import com.ibtech.business.contants.message.ShoppingResultMessage;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.ErrorDataResult;
import com.ibtech.core.utilities.result.SuccessDataResult;
import com.ibtech.entities.Cart;
import com.ibtech.repository.CartRepository;

public class CartManager implements CartService {

	private CartRepository cartRepository;
	public CartManager(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	
	@Override
	public DataResult<Long> create(Cart cart) {
		try {
//			DataResult<Cart> result = getByUserName(cart.getCustomerName());
//			if(result.isSuccess()) {
//				return new ErrorDataResult<Long>(ShoppingResultMessage.CartAlreadyExist);
//			}
			Long cartId = cartRepository.add(cart);
			return cartId > 0 ?  new SuccessDataResult<Long>(cartId,ShoppingResultMessage.CartAdded) : new ErrorDataResult<Long>(cartId,ShoppingResultMessage.CartCouldNotAdded);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<Long>(ShoppingResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<List<Cart>> getAll() {
		try {
			String sql = "Select * from carts";
			return new SuccessDataResult<List<Cart>>(cartRepository.listAll(sql));
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<Cart>>(ShoppingResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<Cart> getById(long cartId) {
		try {
			String sql = "Select * from carts where id = ?";;
			Cart dbCart = cartRepository.find(sql, cartId);
			if(dbCart != null) {
				return new SuccessDataResult<Cart>(dbCart);
			}
			return new ErrorDataResult<Cart>(ShoppingResultMessage.CartNotFound);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<Cart>(ShoppingResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<Cart> getByUserName(String userName) {
		try {
			String sql = "Select * from carts where  customer_name = ?";
			Cart dbCart = cartRepository.findByName(sql, userName);
			if(dbCart != null) {
				return new SuccessDataResult<Cart>(dbCart);
			}
			return new ErrorDataResult<Cart>(ShoppingResultMessage.CartNotFound);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<Cart>(ShoppingResultMessage.ErrorMessage);
		}
	}

}
