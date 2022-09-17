package com.ibtech.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibtech.entities.OrderProduct;

public class OrderProductRepository extends BaseRepository<OrderProduct>{

	public List<OrderProduct> getAll() throws SQLException{
		String sql = "Select * from order_products";
		return super.listAll(sql);
	}
	
	public List<OrderProduct> getByOrderId(long orderId) throws SQLException{
		List<OrderProduct> orderProductList = new ArrayList<>();
		connect();
		String sql = "Select * from order_products where order_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, orderId);
		ResultSet resultSet = statement.executeQuery();
		orderProductList = parseList(resultSet);
		disconnect();
		return orderProductList;
	}
	
	public OrderProduct getById(long orderProductId) throws SQLException {
		String sql = "Select * from order_products where id = ?";
		return super.find(sql, orderProductId);
	}
	
	@Override
	protected OrderProduct parse(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("id");
		long orderId = resultSet.getLong("order_id");
		String productName = resultSet.getString("product_name");
		String imagePath = resultSet.getString("image_path");
		double salesPrice = resultSet.getDouble("sales_price");
		int salesQuantity = resultSet.getInt("sales_quantity");
		double taxRate = resultSet.getDouble("tax_rate");
		double lineAmount = resultSet.getDouble("line_amount");
		OrderProduct orderProduct = new OrderProduct(id,orderId,productName,imagePath,salesPrice,salesQuantity,taxRate,lineAmount);
		return orderProduct;
	}
}
