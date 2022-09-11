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
			String sql = "select cp.*,p.\"name\" as product_name,p.sales_price as product_sales_price,p.image_path as product_image_path, c.id  as category_id,c.\"name\" as category_name\r\n"
					+ "from cart_products cp join products p on cp.product_id = p.id join categories c on c.id  = p.category_id";
			
			List<CartProduct> cartProductList = cartProductRepository.listAll(sql);
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
			String sql = "select cp.*,p.\"name\" as product_name,p.sales_price as product_sales_price,p.image_path as product_image_path, c.id  as category_id,c.\"name\" as category_name\r\n"
					+ "from cart_products cp join products p on cp.product_id = p.id join categories c on c.id  = p.category_id where cp.id = ?";
			
			CartProduct cartProduct = cartProductRepository.find(sql, cartProductId);
			return cartProduct != null ? new SuccessDataResult<CartProduct>(cartProduct) : new ErrorDataResult<CartProduct>(ShoppingResultMessage.CartProductNotFound);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<CartProduct>(ShoppingResultMessage.ErrorMessage);
		}
	}
	
	@Override
	public DataResult<Long> add(CartProduct cartProduct) {
		try {
			System.out.println(cartProduct.getCartId() +" " +cartProduct.getSalesPrice() + " "  + cartProduct.getSalesQuantity() +" " + cartProduct.getTaxRate() + " " + cartProduct.getLineAmount() + " " + cartProduct.getProduct().getProductId() );
			long cartProductId = cartProductRepository.add(cartProduct);
			return  cartProductId > 0 ? new SuccessDataResult<Long>(cartProductId,ShoppingResultMessage.CartProductAdded) : new ErrorDataResult<Long>(cartProductId,ShoppingResultMessage.CartProductCouldNotAdded);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<Long>(ShoppingResultMessage.ErrorMessage);
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
				boolean deleted = cartProductRepository.delete(cartProductId);
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
