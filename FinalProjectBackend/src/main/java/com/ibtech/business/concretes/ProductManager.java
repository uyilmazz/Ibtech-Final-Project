package com.ibtech.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.ibtech.business.abstracts.ProductService;
import com.ibtech.business.contants.message.InventoryResultMessage;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.ErrorDataResult;
import com.ibtech.core.utilities.result.ErrorResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.core.utilities.result.SuccessDataResult;
import com.ibtech.core.utilities.result.SuccessResult;
import com.ibtech.entities.Product;
import com.ibtech.repository.ProductRepository;

public class ProductManager implements ProductService{

	private ProductRepository productRepository;
	
	public ProductManager(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public DataResult<List<Product>> getAll() {
		try {
			return new SuccessDataResult<List<Product>>(productRepository.getAll());
		} catch (SQLException e) {
			return new ErrorDataResult<List<Product>>(InventoryResultMessage.ErrorMessage);
		}
	}

	public DataResult<List<Product>> getByCategory(int category_id){
		try {
			return new SuccessDataResult<List<Product>>(productRepository.getByCategory(category_id));
		}catch(Exception e) {
			return new ErrorDataResult<List<Product>>(InventoryResultMessage.ErrorMessage);
		}
	}
	
	@Override
	public DataResult<List<Product>> getByLimit(int limit) {
		try {
			return new SuccessDataResult<List<Product>>(productRepository.getByLimit(limit));
		}catch(Exception e) {
			return new ErrorDataResult<List<Product>>(InventoryResultMessage.ErrorMessage);
		}
	}
	
	@Override
	public DataResult<Product> getById(long productId) {
		try {
			Product product = productRepository.getById(productId);
			if(product != null) {
				return new SuccessDataResult<Product>(product);
			}else {
				return new ErrorDataResult<Product>(InventoryResultMessage.ProductNotFound);
			}
		}catch(SQLException e) {
			return new ErrorDataResult<Product>(InventoryResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result add(Product product) {
		try {	
			Product dbProduct = productRepository.findByName(product.getProductName());
			if(dbProduct != null) {
				return new ErrorResult(InventoryResultMessage.ProductAlreadyExist);
			}
			boolean inserted = productRepository.add(product);
			return inserted ? new SuccessResult(InventoryResultMessage.ProductAdded) : new ErrorResult(InventoryResultMessage.ProductCouldNotAdded);
		} catch (SQLException e) {
			return new ErrorResult(InventoryResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result update(Product product) {
		try {
			DataResult<Product> result = getById(product.getProductId());
			if(!result.isSuccess()) {
				return result;
			}
			Product dbProduct = productRepository.findByName(product.getProductName());
			if(dbProduct != null) {
				return new ErrorResult(InventoryResultMessage.ProductAlreadyExist);
			}
			boolean updated = productRepository.update(product);
			return updated ? new SuccessResult(InventoryResultMessage.CategoryUpdated) : new ErrorResult(InventoryResultMessage.CategoryCouldNotUpdated);
			
		}catch(Exception e) {
			return new ErrorResult(InventoryResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result delete(long productId) {
		try {
			DataResult<Product> result = getById(productId);
			if(!result.isSuccess()) {
				return new ErrorResult();
			}
			boolean deleted = productRepository.delete(productId);
			return deleted ? new SuccessResult(InventoryResultMessage.ProductDeleted) : new ErrorResult(InventoryResultMessage.CategoryCouldNotDeleted);
		}catch(Exception e) {
			return new ErrorResult(InventoryResultMessage.ErrorMessage);
		}
	}

	

}
