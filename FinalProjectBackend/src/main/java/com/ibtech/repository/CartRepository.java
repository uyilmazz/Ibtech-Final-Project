package com.ibtech.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.ibtech.entities.Cart;

public class CartRepository extends BaseRepository<Cart> {

	public List<Cart> getAll() throws SQLException {
		String sql = "Select * from carts";
		return super.listAll(sql);
	}

	public Cart getById(Long cartId) throws SQLException {
		String sql = "Select * from carts where id = ?";
		return super.find(sql, cartId);
	}

	public Cart findByUserName(String userName) throws SQLException {
		String sql = "Select * from carts where  customer_name = ?";
		return super.findByName(sql, userName);
	}

	public long add(Cart cart) throws SQLException {
		long cartId = 0;
		connect();
		String sql = "insert into carts(customer_name,total_amount) values(?,?)";
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, cart.getCustomerName());
		statement.setDouble(2, cart.getTotalAmount());
		statement.executeUpdate();
		ResultSet resultSet = statement.getGeneratedKeys();
		if (resultSet.next()) {
			cartId = resultSet.getLong(1);
		}
		disconnect();
		return cartId;
	}

	public boolean update(Cart cart) throws SQLException {
		connect();
		String sql = "Update carts set customer_name = ? , total_amount = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, cart.getCustomerName());
		statement.setDouble(2, cart.getTotalAmount());
		statement.setLong(3, cart.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}

	public boolean delete(long cartId) throws SQLException {
		connect();
		String sql = "Delete from carts where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, cartId);
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}

	@Override
	protected Cart parse(ResultSet resultSet) throws SQLException {
		Long cartId = resultSet.getLong("id");
		String customerName = resultSet.getString("customer_name");
		double totalAmount = resultSet.getDouble("total_amount");
		Cart cart = new Cart(cartId, customerName, totalAmount);
		return cart;
	}

}
