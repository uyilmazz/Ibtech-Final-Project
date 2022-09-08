package com.ibtech.inventory.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ibtech.inventory.entities.Category;
import com.ibtech.inventory.entities.Product;

public class ProductRepository extends BaseRepository<Product>{
	
	public List<Product> getByCategory(int category_id) throws SQLException{
		connect();
		String sql = "Select * from products where category_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, category_id);
		ResultSet resultSet = statement.executeQuery();
		List<Product> productList = parseList(resultSet);
		return productList;
	}

	public boolean add(Product product) throws SQLException {
		connect();		
		String sql = "insert into products(name,sales_price,image_path,category_id) values(?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, product.getProductName());
		statement.setDouble(2, product.getSalesPrice());
		statement.setString(3, product.getImagePath());
		statement.setInt(4, product.getCategory().getCategoryId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Product product) throws SQLException {
		connect();
		String sql = "Update into products(name,sales_price,image_path,category_id) values(?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, product.getProductName());
		statement.setDouble(2, product.getSalesPrice());
		statement.setString(3, product.getImagePath());
		statement.setInt(4, product.getCategory().getCategoryId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean delete(long productId) throws SQLException {
		connect();
		String sql = "Delete from products where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, productId);
		int affected = statement.executeUpdate();
		return affected > 0 ? true : false;
	}
	
	@Override
	protected Product parse(ResultSet resultSet) throws SQLException {
		Long productId = resultSet.getLong("id");
		String productName = resultSet.getString("name");
		String imagePath = resultSet.getString("image_path");
		double salesPrice = resultSet.getDouble("sales_price");
		int categoryId = resultSet.getInt("category_id");
		CategoryRepository categoryRepository = new CategoryRepository();
		String sql = "Select * from categories where id = ?";
		Category category = categoryRepository.find(sql, categoryId);
		Product product = new Product(productId,productName,imagePath,salesPrice);
		product.setCategory(category);
		return product;
	}
	
	
}
