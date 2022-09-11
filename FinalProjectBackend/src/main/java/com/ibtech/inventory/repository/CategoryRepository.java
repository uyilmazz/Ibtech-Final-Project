package com.ibtech.inventory.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibtech.inventory.entities.Category;
import com.ibtech.repository.BaseRepository;

public class CategoryRepository extends BaseRepository<Category>{

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
