package com.ibtech.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.ibtech.entities.CartProduct;
import com.ibtech.inventory.entities.Category;
import com.ibtech.inventory.entities.Product;

public class CartProductRepository extends BaseRepository<CartProduct>{

	public CartProduct geyByCartProductIdAndCartId(long cartProductId,long cartId) throws SQLException {
		connect();
		String sql = "select cp.*,p.\"name\" as product_name,p.sales_price as product_sales_price,p.image_path as product_image_path, c.id  as category_id,c.\"name\" as category_name\r\n"
				+ "from cart_products cp join products p on cp.product_id = p.id join categories c on c.id  = p.category_id where cp.product_id  = ? and cp.cart_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, cartProductId);
		statement.setLong(2, cartId);
		ResultSet resulSet = statement.executeQuery();
		disconnect();
		return parse(resulSet);
	}
	
	public List<CartProduct> getByCartId(long cartId) throws SQLException{
		connect();
		String sql = "select cp.*,p.\"name\" as product_name,p.sales_price as product_sales_price,p.image_path as product_image_path, c.id  as category_id,c.\"name\" as category_name\r\n"
				+ "from cart_products cp join products p on cp.product_id = p.id join categories c on c.id  = p.category_id where cp.cart_id  = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, cartId);
		ResultSet resulSet = statement.executeQuery();
		disconnect();
		return parseList(resulSet);
	}
	
	public long add(CartProduct cartProduct) throws SQLException {
		long createdId = 0;
		connect();	
		String sql = "insert into cart_products(cart_id,sales_price,sales_quantity,tax_rate,line_amount,product_id) values(?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		statement.setLong(1, cartProduct.getCartId());
		statement.setDouble(2, cartProduct.getSalesPrice());
		statement.setInt(3, cartProduct.getSalesQuantity());
		statement.setDouble(4, cartProduct.getTaxRate());
		statement.setDouble(5, cartProduct.getLineAmount());
		statement.setLong(6, cartProduct.getProduct().getProductId());
		statement.executeUpdate();
		ResultSet resultSet = statement.getGeneratedKeys();
		if(resultSet.next()) {
			createdId = resultSet.getLong(1);
		}
		disconnect();	
		return createdId;
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
	
	public boolean delete(long cartId) throws SQLException {
		connect();
		String sql = "Delete from cart_products where id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, cartId);
		int affected = statement.executeUpdate();
		disconnect();
		return affected > 0 ? true : false;
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
