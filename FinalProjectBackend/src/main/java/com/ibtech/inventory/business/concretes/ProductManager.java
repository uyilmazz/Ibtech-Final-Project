package com.ibtech.inventory.business.concretes;

import java.sql.SQLException;
import java.util.List;

import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.ErrorDataResult;
import com.ibtech.core.utilities.result.ErrorResult;
import com.ibtech.core.utilities.result.Result;
import com.ibtech.core.utilities.result.SuccessDataResult;
import com.ibtech.core.utilities.result.SuccessResult;
import com.ibtech.inventory.business.abstracts.ProductService;
import com.ibtech.inventory.business.constants.message.ResultMessage;
import com.ibtech.inventory.entities.Product;
import com.ibtech.inventory.repository.ProductRepository;

public class ProductManager implements ProductService{

	private ProductRepository productRepository;
	
	public ProductManager(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public DataResult<List<Product>> getAll() {
		String sql = "Select * from products";
		try {
			return new SuccessDataResult<List<Product>>(productRepository.listAll(sql));
		} catch (SQLException e) {
			return new ErrorDataResult<List<Product>>(ResultMessage.ErrorMessage);
		}
	}

	public DataResult<List<Product>> getByCategory(int category_id){
		try {
			return new SuccessDataResult<List<Product>>(productRepository.getByCategory(category_id));
		}catch(Exception e) {
			return new ErrorDataResult<List<Product>>(ResultMessage.ErrorMessage);
		}
	}
	
	@Override
	public DataResult<Product> getById(long productId) {
		String sql = "Select * from products where id = ?";
		try {
			Product product = productRepository.find(sql, productId);
			if(product != null) {
				return new SuccessDataResult<Product>(product);
			}else {
				return new ErrorDataResult<Product>(ResultMessage.ProductNotFound);
			}
		}catch(SQLException e) {
			return new ErrorDataResult<Product>(ResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result add(Product product) {
		try {
			String sql = "Select * from products where name = ?";
			Product dbProduct = productRepository.findByName(sql, product.getProductName());
			if(dbProduct != null) {
				return new ErrorResult(ResultMessage.ProductAlreadyExist);
			}
			boolean inserted = productRepository.add(product);
			return inserted ? new SuccessResult(ResultMessage.ProductAdded) : new ErrorResult(ResultMessage.ProductCouldNotAdded);
		} catch (SQLException e) {
			return new ErrorResult(ResultMessage.ErrorMessage);
		}
	}

	@Override
	public Result update(Product product) {
		try {
			DataResult<Product> result = getById(product.getProductId());
			if(!result.isSuccess()) {
				return result;
			}
			String sql = "Select * from products where name = ?";
			Product dbProduct = productRepository.findByName(sql, product.getProductName());
			if(dbProduct != null) {
				return new ErrorResult(ResultMessage.ProductAlreadyExist);
			}
			boolean updated = productRepository.update(product);
			return updated ? new SuccessResult(ResultMessage.CategoryUpdated) : new ErrorResult(ResultMessage.CategoryCouldNotUpdated);
			
		}catch(Exception e) {
			return new ErrorResult(ResultMessage.ErrorMessage);
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
			return deleted ? new SuccessResult(ResultMessage.ProductDeleted) : new ErrorResult(ResultMessage.CategoryCouldNotDeleted);
		}catch(Exception e) {
			return new ErrorResult(ResultMessage.ErrorMessage);
		}
	}

}
