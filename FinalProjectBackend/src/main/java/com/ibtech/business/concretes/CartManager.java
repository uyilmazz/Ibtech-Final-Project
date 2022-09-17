package com.ibtech.business.concretes;

import java.util.List;

import com.ibtech.business.abstracts.CartService;
import com.ibtech.business.contants.message.ShoppingResultMessage;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.ErrorDataResult;
import com.ibtech.core.utilities.result.ErrorResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.core.utilities.result.SuccessDataResult;
import com.ibtech.core.utilities.result.SuccessResult;
import com.ibtech.entities.Cart;
import com.ibtech.repository.CartRepository;

public class CartManager implements CartService {

	private CartRepository cartRepository;
	public CartManager(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	
	@Override
	public DataResult<Cart> create(Cart cart) {
		try {
			Long cartId = cartRepository.add(cart);
			return cartId > 0 ?  new SuccessDataResult<Cart>(getById(cartId).getData()) : new ErrorDataResult<Cart>(ShoppingResultMessage.CartCouldNotAdded);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<Cart>(ShoppingResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<List<Cart>> getAll() {
		try {
			return new SuccessDataResult<List<Cart>>(cartRepository.getAll());
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<Cart>>(ShoppingResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<Cart> getById(long cartId) {
		try {
			Cart dbCart = cartRepository.getById(cartId);
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
			Cart dbCart = cartRepository.findByUserName(userName);
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
	public Result update(Cart cart) {
		try {
			DataResult<Cart> dbCart = getById(cart.getId());
			if(!dbCart.isSuccess()) {
				return new ErrorResult(ShoppingResultMessage.CartNotFound);
			}
			boolean updatedResult = cartRepository.update(cart);
			return updatedResult ? new SuccessResult(ShoppingResultMessage.CartUpdated) : new ErrorResult(ShoppingResultMessage.CartProductCouldNotUpdated);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<Cart>(ShoppingResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result delete(long cartId) {
		try {
			DataResult<Cart> dbCart = getById(cartId);
			if(!dbCart.isSuccess()) {
				return new ErrorResult(ShoppingResultMessage.CartNotFound);
			}
			boolean updatedResult = cartRepository.delete(cartId);
			return updatedResult ? new SuccessResult(ShoppingResultMessage.CartDeleted) : new ErrorResult(ShoppingResultMessage.CartCouldNotDeleted);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorResult(ShoppingResultMessage.ErrorMessage);
		}
	}
	
	

}
