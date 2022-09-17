package com.ibtech.business.abstracts;

import java.util.List;

import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Product;

public interface ProductService {
	public DataResult<List<Product>> getAll();
	public DataResult<List<Product>> getByCategory(int category_id);
	public DataResult<List<Product>> getByLimit(int limit);
	public DataResult<Product> getById(long productId);
	public DataResult<Integer> getCount();
	public Result add(Product product);
	public Result update(Product product);
	public Result delete(long productId);
}
