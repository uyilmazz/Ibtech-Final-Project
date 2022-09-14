package com.ibtech.business.concretes;

import java.util.List;

import com.ibtech.business.abstracts.CartProductService;
import com.ibtech.business.contants.message.ShoppingResultMessage;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.ErrorDataResult;
import com.ibtech.core.utilities.result.ErrorResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.core.utilities.result.SuccessDataResult;
import com.ibtech.core.utilities.result.SuccessResult;
import com.ibtech.entities.CartProduct;
import com.ibtech.repository.CartProductRepository;

public class CartProductManager implements CartProductService {

	private CartProductRepository cartProductRepository;
	public CartProductManager(CartProductRepository cartProductRepository) {
		this.cartProductRepository = cartProductRepository;
	}
	
	@Override
	public DataResult<List<CartProduct>> getAll() {
		try {
			List<CartProduct> cartProductList = cartProductRepository.getAll();
			return new SuccessDataResult<List<CartProduct>>(cartProductList);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<CartProduct>>(ShoppingResultMessage.ErrorMessage);
		}
	}
	
	@Override
	public DataResult<List<CartProduct>> getByCartId(long cartId) {
		try {
			List<CartProduct> cartProductList = cartProductRepository.getByCartId(cartId);
			return new SuccessDataResult<List<CartProduct>>(cartProductList);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<CartProduct>>(ShoppingResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<CartProduct> getById(long cartProductId) {
		try {	
			CartProduct cartProduct = cartProductRepository.getById(cartProductId);
			return cartProduct != null ? new SuccessDataResult<CartProduct>(cartProduct) : new ErrorDataResult<CartProduct>(ShoppingResultMessage.CartProductNotFound);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<CartProduct>(ShoppingResultMessage.ErrorMessage);
		}
	}
	
	@Override
	public DataResult<CartProduct> add(CartProduct cartProduct) {
		try {
			long cartProductId = cartProductRepository.add(cartProduct);
			return  cartProductId > 0 ? new SuccessDataResult<CartProduct>(getById(cartProductId).getData(),ShoppingResultMessage.CartProductAdded) : new ErrorDataResult<CartProduct>(ShoppingResultMessage.CartProductCouldNotAdded);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<CartProduct>(ShoppingResultMessage.ErrorMessage);
		}
	}
	
	@Override
	public Result updateBulk(List<CartProduct> cartProducts) {
		try {
			boolean addBulkResult = cartProductRepository.updateBulk(cartProducts);
			return  addBulkResult ? new SuccessResult(ShoppingResultMessage.CartProductAdded) : new ErrorResult(ShoppingResultMessage.CartProductCouldNotAdded);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorResult(ShoppingResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result update(CartProduct cartProduct) {
		try {
			DataResult<CartProduct> result = getById(cartProduct.getId());
			if(!result.isSuccess()) {
				boolean updated = cartProductRepository.update(cartProduct);
				return updated ? new SuccessResult(ShoppingResultMessage.CartProductUpdated) : new ErrorResult(ShoppingResultMessage.CartProductCouldNotUpdated);
			}else {
				return new ErrorResult(ShoppingResultMessage.CartProductNotFound);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorResult(ShoppingResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result delete(long cartProductId) {
		try {
			DataResult<CartProduct> result = getById(cartProductId);
			if(result.isSuccess()) {
				boolean deleted = cartProductRepository.delete(result.getData());
				return deleted ? new SuccessResult(ShoppingResultMessage.CartProductDeleted) : new ErrorResult(ShoppingResultMessage.CartProductCouldNotDeleted);
			}else {
				return new ErrorResult(ShoppingResultMessage.CartProductNotFound);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorResult(ShoppingResultMessage.ErrorMessage);
		}
	}
}
