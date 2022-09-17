package com.ibtech.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.ibtech.entities.CartProduct;
import com.ibtech.entities.Order;

public class OrderRepository extends BaseRepository<Order>{
	
	public List<Order> getAll() throws SQLException{
		String sql = "Select * from orders";
		return super.listAll(sql);
	}
	
	public Integer getCount() throws SQLException {
		int count = 0;
		connect();
		String sql = "Select count(*) from orders";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			count = resultSet.getInt("count");
		}
		disconnect();
		return count;
	}
	
	public List<Order> getByCustomerName(String customerName) throws SQLException{
		connect();
		String sql = "Select * from orders where customer_name = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, customerName);
		ResultSet resulSet = statement.executeQuery();
		List<Order> orderList = parseList(resulSet);
		disconnect();
		return orderList;
	}
	
	public Order getById(long id) throws SQLException {
		String sql = "Select * from orders where id = ?";
		return super.find(sql, id);
	}
	
	public boolean create(Order order)   {
		try {
			CartProductRepository cartProductRepository = new CartProductRepository();
			connect();
			connection.setAutoCommit(false);
			
			// Find Cart
			long cartId = 0;
			double cartTotalAmount = 0;
			String cartSql = "Select * from carts where customer_name = ?";
			PreparedStatement cartFindStatement = connection.prepareStatement(cartSql);
			cartFindStatement.setString(1, order.getCustomerName());
			ResultSet resultSetCart = cartFindStatement.executeQuery();
			if (resultSetCart.next()) {
				cartId = resultSetCart.getLong("id");
				cartTotalAmount = resultSetCart.getDouble("total_amount"); 
			}
			
			// Create Order
			long orderId = 0;
			String createOrderSql = "Insert into orders(address_line_1,address_line_2,customer_name,total_amount) values(?,?,?,?)";
			PreparedStatement createOrderStatement = connection.prepareStatement(createOrderSql, Statement.RETURN_GENERATED_KEYS);
			createOrderStatement.setString(1,order.getAddressLine1());
			createOrderStatement.setString(2,order.getAddressLine2());
			createOrderStatement.setString(3,order.getCustomerName());
			createOrderStatement.setDouble(4,cartTotalAmount);
			createOrderStatement.executeUpdate();
			ResultSet resultSet = createOrderStatement.getGeneratedKeys();
			if (resultSet.next()) {
				orderId = resultSet.getLong(1);
			}
			System.out.println(orderId);
			// Find CartProducts
			String cartProductSql = "select cp.*,p.\"name\" as product_name,p.sales_price as product_sales_price,p.image_path as product_image_path, c.id  as category_id,c.\"name\" as category_name\r\n"
					+ "from cart_products cp join products p on cp.product_id = p.id join categories c on c.id  = p.category_id where cp.cart_id = ?\r\n";
			PreparedStatement cartProductFindStatement = connection.prepareStatement(cartProductSql);
			cartProductFindStatement.setLong(1, cartId);
			ResultSet resultSetCartProduct = cartProductFindStatement.executeQuery();
			List<CartProduct> cartProductList = cartProductRepository.parseList(resultSetCartProduct);
			System.out.println(cartProductList.size());
			
			// Create OrderProducts -> Bulk
			String createOrderProductSql = "insert into order_products(order_id,product_name,image_path,sales_price,sales_quantity,tax_rate,line_amount) values(?,?,?,?,?,?,?)";
			PreparedStatement createOrderProductStatement = connection.prepareStatement(createOrderProductSql);
			for(CartProduct cartProduct : cartProductList) {
				createOrderProductStatement.setLong(1, orderId);
				createOrderProductStatement.setString(2, cartProduct.getProduct().getProductName());
				createOrderProductStatement.setString(3, cartProduct.getProduct().getImagePath());
				createOrderProductStatement.setDouble(4, cartProduct.getSalesPrice());
				createOrderProductStatement.setInt(5, cartProduct.getSalesQuantity());
				createOrderProductStatement.setDouble(6, cartProduct.getTaxRate());
				createOrderProductStatement.setDouble(7, cartProduct.getLineAmount());
				createOrderProductStatement.addBatch();
			}
			
			// Clear Cart
			String clearCartSql = "Update carts set total_amount = 0 where id = ?";
			PreparedStatement clearCartStatement = connection.prepareStatement(clearCartSql);
			clearCartStatement.setLong(1,cartId);
			clearCartStatement.executeUpdate();
			
			// Delete CartProducts
			String deleteCartProductsSql = "Delete from cart_products where cart_id = ?";
			PreparedStatement deleteCartProductStatement = connection.prepareStatement(deleteCartProductsSql);
			deleteCartProductStatement.setLong(1, cartId);
			deleteCartProductStatement.executeUpdate();
			
			createOrderProductStatement.executeBatch();
			connection.commit();
			disconnect();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	protected Order parse(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String addressLine1 = resultSet.getString("address_line_1");
		String addressLine2 = resultSet.getString("address_line_2");
		String customerName = resultSet.getString("customer_name");
		double totalAmount = resultSet.getDouble("total_amount");
		Order order = new Order(id, addressLine1, addressLine2,customerName,totalAmount);
		return order;
	}

}
