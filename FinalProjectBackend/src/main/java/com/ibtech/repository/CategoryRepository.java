package com.ibtech.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ibtech.entities.Category;

public class CategoryRepository extends BaseRepository<Category>{

	public List<Category> getAll() throws SQLException{
		String sql = "Select * from categories";
		return super.listAll(sql);
	}
	
	public Category getById(int categoryId) throws SQLException {
		String sql = "Select * from categories where id = ?";
		return super.find(sql, categoryId);
	}
	
	public Category findByName(String name) throws SQLException {
		String sql = "Select * from categories where name = ?";
		return super.findByName(sql, name);
	}
	
	public boolean add(Category category) throws SQLException {
		connect();		
		String sql = "insert into categories(name) values(?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, category.getCategoryName());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean update(Category category) throws SQLException {
		connect();	
		String sql = "Update Categories set name=? where id=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, category.getCategoryName());
		statement.setInt(2, category.getCategoryId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean delete(int id) throws SQLException {
		connect();
		String sql = "Delete from categories where id =?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	@Override
	protected Category parse(ResultSet resultSet) throws SQLException {	
		int categoryId = resultSet.getInt("id");
		String categoryName = resultSet.getString("name");
		Category category = new Category(categoryId,categoryName);
		return category;
	}
}
