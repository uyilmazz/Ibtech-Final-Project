package com.ibtech.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ibtech.entities.Category;
import com.ibtech.entities.Product;

public class ProductRepository extends BaseRepository<Product>{
	
	public List<Product> getAll() throws SQLException{
		String sql = "select p.id as id,p.\"name\" ,p.sales_price ,p.image_path,p.category_id ,c.\"name\" as category_name from products p join categories c on p.category_id = c.id ";
		return super.listAll(sql);
	}
	
	public Product getById(long productId) throws SQLException {
		String sql = "select p.id as id,p.\"name\" ,p.sales_price ,p.image_path,p.category_id ,c.\"name\" as category_name from products p join categories c on p.category_id = c.id  where p.id = ?";
		return super.find(sql, productId);
	}
	
	public List<Product> getByCategory(int category_id) throws SQLException{
		connect();
		String sql = "select p.id as id,p.\"name\" ,p.sales_price ,p.image_path,p.category_id ,c.\"name\" as category_name from products p join categories c on p.category_id = c.id  where p.category_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, category_id);
		ResultSet resultSet = statement.executeQuery();
		disconnect();
		List<Product> productList = parseList(resultSet);
		return productList;
	}
	
	public List<Product> getByLimit(int limit) throws SQLException{
		connect();
		String sql = "select p.id as id,p.\"name\" ,p.sales_price ,p.image_path,p.category_id ,c.\"name\" as category_name from products p join categories c on p.category_id = c.id  order by p.id desc limit ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, limit);
		ResultSet resultSet = statement.executeQuery();
		disconnect();
		List<Product> productList = parseList(resultSet);
		return productList;
	}
	
	public Product findByName(String name) throws SQLException {
		String sql = "select p.id as id,p.\"name\" ,p.sales_price ,p.image_path,p.category_id ,c.\"name\" as category_name from products p join categories c on p.category_id = c.id  where p.name = ?";
		return super.findByName(sql, name);
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
		String sql = "Update products set name = ?,sales_price = ? , image_path = ? , category_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, product.getProductName());
		statement.setDouble(2, product.getSalesPrice());
		statement.setString(3, product.getImagePath());
		statement.setInt(4, product.getCategory().getCategoryId());
		statement.setLong(5, product.getProductId());
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
		disconnect();
		return affected > 0 ? true : false;
	}
	
	@Override
	protected Product parse(ResultSet resultSet) throws SQLException {
		Long productId = resultSet.getLong("id");
		String productName = resultSet.getString("name");
		String imagePath = resultSet.getString("image_path");
		double salesPrice = resultSet.getDouble("sales_price");
		int categoryId = resultSet.getInt("category_id");
		String categoryName = resultSet.getString("category_name");
		Category category = new Category(categoryId,categoryName);
		Product product = new Product(productId,productName,imagePath,salesPrice);
		product.setCategory(category);
		return product;
	}
	
	
}
