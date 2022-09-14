package com.ibtech.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.ibtech.business.abstracts.CategoryService;
import com.ibtech.business.contants.message.InventoryResultMessage;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.ErrorDataResult;
import com.ibtech.core.utilities.result.ErrorResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.core.utilities.result.SuccessDataResult;
import com.ibtech.core.utilities.result.SuccessResult;
import com.ibtech.entities.Category;
import com.ibtech.repository.CategoryRepository;

public class CategoryManager implements CategoryService{

	private CategoryRepository categoryRepository;

	public CategoryManager(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public DataResult<List<Category>> getAll() {
		try {
			return new SuccessDataResult<List<Category>>(categoryRepository.getAll());
		} catch (SQLException e) {
			e.printStackTrace();
			return new ErrorDataResult<List<Category>>(InventoryResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<Category> getById(int categoryId) {
		try {
			Category category = categoryRepository.getById(categoryId);
			if(category != null) {
				return new SuccessDataResult<Category>(category);
			}else {
				return new ErrorDataResult<Category>(InventoryResultMessage.CategoryNotFound);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return new ErrorDataResult<Category>(InventoryResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result add(Category category) {
		try {
			Category dbCategory = categoryRepository.findByName(category.getCategoryName());
			if(dbCategory != null) {
				return new ErrorResult(InventoryResultMessage.CategoryAlreadyExist);
			}
			boolean inserted = categoryRepository.add(dbCategory);
			return inserted ? new SuccessResult(InventoryResultMessage.CategoryAdded) : new ErrorResult(InventoryResultMessage.CategoryCouldNotAdded);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ErrorDataResult<Category>(InventoryResultMessage.ErrorMessage);
		}	
	}

	@Override
	public Result update(Category category)  {
		try {
			DataResult<Category> result = getById(category.getCategoryId());
			if(!result.isSuccess()) {
				return result;
			}
			Category dbCategory = categoryRepository.findByName(category.getCategoryName());
			if(dbCategory != null) {
				return new ErrorResult(InventoryResultMessage.CategoryAlreadyExist);
			}
			boolean updated = categoryRepository.update(category);
			return updated ? new SuccessResult(InventoryResultMessage.CategoryUpdated) : new ErrorResult(InventoryResultMessage.CategoryCouldNotUpdated);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorResult(InventoryResultMessage.ErrorMessage);
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
			e.printStackTrace();
			return new ErrorResult(InventoryResultMessage.ErrorMessage);
		}
	}
	
}
