package com.ibtech.inventory.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.ErrorDataResult;
import com.ibtech.core.utilities.result.ErrorResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.core.utilities.result.SuccessDataResult;
import com.ibtech.core.utilities.result.SuccessResult;
import com.ibtech.inventory.business.abstracts.CategoryService;
import com.ibtech.inventory.business.constants.message.InventoryResultMessage;
import com.ibtech.inventory.entities.Category;
import com.ibtech.inventory.repository.CategoryRepository;

public class CategoryManager implements CategoryService{

	private CategoryRepository categoryRepository;

	public CategoryManager(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public DataResult<List<Category>> getAll() {
		String sql = "Select * from categories";
		try {
			return new SuccessDataResult<List<Category>>(categoryRepository.listAll(sql));
		} catch (SQLException e) {
			return new ErrorDataResult<List<Category>>(e.getMessage());
		}
	}

	@Override
	public DataResult<Category> getById(int categoryId) {
		String sql = "Select * from categories where id = ?";
		try {
			Category category = categoryRepository.find(sql, categoryId);
			if(category != null) {
				return new SuccessDataResult<Category>(category);
			}else {
				return new ErrorDataResult<Category>(InventoryResultMessage.CategoryNotFound);
			}
		}catch(SQLException e) {
			return new ErrorDataResult<Category>(e.getMessage());
		}
	}

	@Override
	public Result add(Category category) {
		try {
			String sql = "Select * from categories where name = ?";
			Category dbCategory = categoryRepository.findByName(sql, category.getCategoryName());
			if(dbCategory != null) {
				return new ErrorResult(InventoryResultMessage.CategoryAlreadyExist);
			}
			boolean inserted = categoryRepository.add(dbCategory);
			return inserted ? new SuccessResult(InventoryResultMessage.CategoryAdded) : new ErrorResult(InventoryResultMessage.CategoryCouldNotAdded);
		} catch (SQLException e) {
			return new ErrorDataResult<Category>(e.getMessage());
		}	
	}

	@Override
	public Result update(Category category)  {
		try {
			DataResult<Category> result = getById(category.getCategoryId());
			if(!result.isSuccess()) {
				return result;
			}
			String sql = "Select * from categories where name = ?";
			Category dbCategory = categoryRepository.findByName(sql, category.getCategoryName());
			if(dbCategory != null) {
				return new ErrorResult(InventoryResultMessage.CategoryAlreadyExist);
			}
			boolean updated = categoryRepository.update(category);
			return updated ? new SuccessResult(InventoryResultMessage.CategoryUpdated) : new ErrorResult(InventoryResultMessage.CategoryCouldNotUpdated);
			
		}catch(Exception e) {
			return new ErrorResult(e.getMessage());
		}
	}

	@Override
	public Result delete(int categoryId) {
		try {
			DataResult<Category> result = getById(categoryId);
			if(!result.isSuccess()) {
				return new ErrorResult();
			}
			boolean deleted = categoryRepository.delete(categoryId);
			return deleted ? new SuccessResult(InventoryResultMessage.CategoryDeleted) : new ErrorResult(InventoryResultMessage.CategoryCouldNotDeleted);
		}catch(Exception e) {
			return new ErrorResult(e.getMessage());
		}
	}
	
}
