package com.ibtech.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.ibtech.entities.CartProduct;
import com.ibtech.entities.Category;
import com.ibtech.entities.Product;

public class CartProductRepository extends BaseRepository<CartProduct>{

	private final static String SELECTSTRING = "select cp.*,p.\"name\" as product_name,p.sales_price as product_sales_price,p.image_path as product_image_path, c.id  as category_id,c.\"name\" as category_name\r\n"
			+ "from cart_products cp join products p on cp.product_id = p.id join categories c on c.id  = p.category_id";
	
	public List<CartProduct> getAll() throws SQLException{
		return super.listAll(SELECTSTRING);
	}
	
	public List<CartProduct> getByCartId(long cartId) throws SQLException{
		connect();
		String sql ="select cp.*,p.\"name\" as product_name,p.sales_price as product_sales_price,p.image_path as product_image_path, c.id  as category_id,c.\"name\" as category_name\r\n"
				+ "from cart_products cp join products p on cp.product_id = p.id join categories c on c.id  = p.category_id where cp.cart_id = ?\r\n";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, cartId);
		ResultSet resulSet = statement.executeQuery();
		disconnect();
		return parseList(resulSet);
	}
	
	public CartProduct getById(long cartProductId) throws SQLException {
		String sql ="select cp.*,p.\"name\" as product_name,p.sales_price as product_sales_price,p.image_path as product_image_path, c.id  as category_id,c.\"name\" as category_name\r\n"
				+ "from cart_products cp join products p on cp.product_id = p.id join categories c on c.id  = p.category_id where cp.id = ?\r\n";
		return super.find(sql, cartProductId);
	}
	
	public long add(CartProduct cartProduct) throws SQLException {
		connect();
		connection.setAutoCommit(false);
		double totalAmount = 0;
		// Find Cart
		String cartSql = "Select * from carts where id = ?";
		PreparedStatement cartFindStatement = connection.prepareStatement(cartSql);
		cartFindStatement.setLong(1, cartProduct.getCartId());
		ResultSet resultSetCart = cartFindStatement.executeQuery();
		if(resultSetCart.next()) {
			totalAmount = resultSetCart.getDouble("total_amount");
		} 
		
		// Create CartProduct
		long createdId = 0;
		String sql = "insert into cart_products(cart_id,sales_price,sales_quantity,tax_rate,line_amount,product_id) values(?,?,?,?,?,?)";
		PreparedStatement cartProductCreateStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		cartProductCreateStatement.setLong(1, cartProduct.getCartId());
		cartProductCreateStatement.setDouble(2, cartProduct.getSalesPrice());
		cartProductCreateStatement.setInt(3, cartProduct.getSalesQuantity());
		cartProductCreateStatement.setDouble(4, cartProduct.getTaxRate());
		cartProductCreateStatement.setDouble(5, cartProduct.getLineAmount());
		cartProductCreateStatement.setLong(6, cartProduct.getProduct().getProductId());
		int affectedCreatedCartProduct = cartProductCreateStatement.executeUpdate();
		ResultSet resultSet = cartProductCreateStatement.getGeneratedKeys();
		if(resultSet.next()) {
			createdId = resultSet.getLong(1);
		}
		
		// Update Cart
		totalAmount += cartProduct.getLineAmount();
		String updateCartSql = "Update carts set total_amount = ? where id = ?";
		PreparedStatement cartUpdateStatement = connection.prepareStatement(updateCartSql);
		cartUpdateStatement.setDouble(1,totalAmount);
		cartUpdateStatement.setLong(2, cartProduct.getCartId());
		int affectedUpdatedCart = cartUpdateStatement.executeUpdate();
		if(!(affectedCreatedCartProduct > 0 && affectedUpdatedCart > 0)) {
			createdId = 0;
		}
		connection.commit();
		disconnect();	
		return createdId;
	}
	
	public boolean updateBulk(List<CartProduct> cartProducts) {
		try {
			connect();
			connection.setAutoCommit(false);
			
			// Find Cart
			double totalAmount = 0;
			String cartSql = "Select * from carts where id = ?";
			PreparedStatement cartFindStatement = connection.prepareStatement(cartSql);
			cartFindStatement.setLong(1, cartProducts.get(0).getCartId());
			ResultSet resultSetCart = cartFindStatement.executeQuery();
			if(resultSetCart.next()) {
				totalAmount = resultSetCart.getDouble("total_amount");
			} 
			
			String sql = "Update cart_products set cart_id = ? ,sales_price = ?, sales_quantity = ?, tax_rate = ?,line_amount = ?,product_id = ? where id = ?";
			PreparedStatement cartProductUpdateStatement = connection.prepareStatement(sql);
			
			String updateCartSql = "Update carts set total_amount = ? where id = ?";
			PreparedStatement cartUpdateStatement = connection.prepareStatement(updateCartSql);
			
			for(CartProduct cartProduct : cartProducts) {
				// Update CartProduct
				cartProductUpdateStatement.setLong(1, cartProduct.getCartId());
				cartProductUpdateStatement.setDouble(2, cartProduct.getSalesPrice());
				cartProductUpdateStatement.setInt(3, cartProduct.getSalesQuantity());
				cartProductUpdateStatement.setDouble(4, cartProduct.getTaxRate());
				cartProductUpdateStatement.setDouble(5, cartProduct.getLineAmount());
				cartProductUpdateStatement.setLong(6, cartProduct.getProduct().getProductId());
				cartProductUpdateStatement.setLong(7, cartProduct.getId());
				cartProductUpdateStatement.addBatch();
				
				// Update Cart
				totalAmount += cartProduct.getLineAmount();
				cartUpdateStatement.setDouble(1,totalAmount);
				cartUpdateStatement.setLong(2, cartProduct.getCartId());
				cartUpdateStatement.addBatch();
			}
			int[] updatedCartProduct = cartProductUpdateStatement.executeBatch();
			int[] updatedCart = cartUpdateStatement.executeBatch();
			
			connection.commit();
			disconnect();
			if(updatedCartProduct.length > 0 && updatedCart.length > 0) {
				return true;
			}
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean update(CartProduct cartProduct) throws SQLException {
		connect();
		String sql = "Update cart_products set cart_id = ?,sales_price = ?,sales_quantity = ?,tax_rate = ?,line_amount = ?,product_id = ? where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, cartProduct.getCartId());
		statement.setDouble(2, cartProduct.getSalesPrice());
		statement.setInt(3, cartProduct.getSalesQuantity());
		statement.setDouble(4, cartProduct.getTaxRate());
		statement.setDouble(5, cartProduct.getLineAmount());
		statement.setLong(6, cartProduct.getProduct().getProductId());
		statement.setLong(7, cartProduct.getId());
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
	}
	
	public boolean delete(CartProduct cartProduct) throws SQLException {
		
		connect();
		connection.setAutoCommit(false);
		double totalAmount = 0;
		// Find Cart
		String cartSql = "Select * from carts where id = ?";
		PreparedStatement cartFindStatement = connection.prepareStatement(cartSql);
		cartFindStatement.setLong(1, cartProduct.getCartId());
		ResultSet resultSetCart = cartFindStatement.executeQuery();
		if(resultSetCart.next()) {
			totalAmount = resultSetCart.getDouble("total_amount");
		} 
		
		// Remove CartProduct
		String sql = "Delete from cart_products where id = ?";
		PreparedStatement cartProductDeleteStatement = connection.prepareStatement(sql);
		cartProductDeleteStatement.setLong(1, cartProduct.getId());
		int affectedDeleteCartProduct = cartProductDeleteStatement.executeUpdate();
	
		// Update Cart
		totalAmount -= cartProduct.getLineAmount();
		String updateCartSql = "Update carts set total_amount = ? where id = ?";
		PreparedStatement cartUpdateStatement = connection.prepareStatement(updateCartSql);
		cartUpdateStatement.setDouble(1,totalAmount);
		cartUpdateStatement.setLong(2, cartProduct.getCartId());
		int affectedUpdatedCart = cartUpdateStatement.executeUpdate();
		
		connection.commit();
		disconnect();	
		
		if(affectedDeleteCartProduct > 0 && affectedUpdatedCart > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	protected CartProduct parse(ResultSet resultSet) throws SQLException {
		
		Long id = resultSet.getLong("id");
		long cartId = resultSet.getLong("cart_id");
		double salesPrice = resultSet.getDouble("sales_price");
		int salesQuantity = resultSet.getInt("sales_quantity");
		double taxRate = resultSet.getDouble("tax_rate");
		double lineAmount = resultSet.getDouble("line_amount");
		CartProduct cartProduct = new CartProduct(id,cartId,salesPrice,salesQuantity,taxRate,lineAmount);
		
		long productId = resultSet.getLong("product_id");
		String productName = resultSet.getString("product_name");
		double productSalesPrice = resultSet.getDouble("product_sales_price");
		String imagePath = resultSet.getString("product_image_path");
		Product product = new Product(productId,productName,imagePath,productSalesPrice);
		
		int categoryId = resultSet.getInt("category_id");
		String categoryName = resultSet.getString("category_name");
		Category category = new Category(categoryId,categoryName);
		
		product.setCategory(category);
		cartProduct.setProduct(product);
		return cartProduct;
	}

}
