package com.ibtech.business.abstracts;

import java.util.List;

import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.CartProduct;

public interface CartProductService {
	public DataResult<List<CartProduct>> getAll();
	public DataResult<CartProduct> getById(long cartProductId);
	public DataResult<List<CartProduct>> getByCartId(long cartId);
	public DataResult<CartProduct> add(CartProduct cartProduct);
	public Result updateBulk(List<CartProduct> cartProducts);
	public Result update(CartProduct cartProduct);
	public Result delete(long cartProductId);

}
