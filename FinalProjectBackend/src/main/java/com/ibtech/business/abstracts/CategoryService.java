package com.ibtech.business.abstracts;

import java.util.List;

import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.entities.Category;

public interface CategoryService {
	public DataResult<List<Category>> getAll() throws Exception;
	public DataResult<Category> getById(int categoryId);
	
	public DataResult<Integer> getCount();
	
	public Result add(Category category);
	public Result update(Category category);
	public Result delete(int categoryId);
}
