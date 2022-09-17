package com.ibtech.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.ibtech.entities.User;

public class UserRepository extends BaseRepository<User> {

	public long add(User user) throws SQLException {
		long userId = 0;
		connect();
		String sql = "insert into users(name,password) values(?,?)";
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, user.getName());
		statement.setString(2, user.getPassword());
		statement.executeUpdate();
		ResultSet resultSet = statement.getGeneratedKeys();
		if (resultSet.next()) {
			userId = resultSet.getLong(1);
		}
		disconnect();
		return userId;
	}
	
	public Integer getCount() throws SQLException {
		int count = 0;
		connect();
		String sql = "Select count(*) from users";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			count = resultSet.getInt("count");
		}
		disconnect();
		return count;
	}

	public User check(User user) throws SQLException {
		User responseUser = null;
		connect();
		String sql = "Select * from users where name = ? and password = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, user.getName());
		statement.setString(2, user.getPassword());
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			responseUser = parse(resultSet);
		}
		disconnect();
		return responseUser;
	}

	@Override
	protected User parse(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		String name = resultSet.getString("name");
		String password = resultSet.getString("password");
		User user = new User(id, name, password);
		return user;
	}

}
