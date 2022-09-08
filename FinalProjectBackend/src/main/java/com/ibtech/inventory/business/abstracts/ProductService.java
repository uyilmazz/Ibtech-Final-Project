package com.ibtech.inventory.business.abstracts;

import java.util.List;

import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.inventory.entities.Product;

public interface ProductService {
	public DataResult<List<Product>> getAll();
	public DataResult<List<Product>> getByCategory(int category_id);
	public DataResult<Product> getById(long productId);
	
	public Result add(Product product);
	public Result update(Product product);
	public Result delete(long productId);
}
