package com.ibtech.entities;

import com.ibtech.inventory.entities.Product;

public class CartProduct {
	private long id;
	private long cartId;
	private double salesPrice;
	private int salesQuantity;
	private double taxRate;
	private double lineAmount;
	private Product product;
	
	public CartProduct() {
		
	}

	public CartProduct(long id, long cartId,  double salesPrice, int salesQuantity, double taxRate,
			double lineAmount) {
		this.id = id;
		this.cartId = cartId;
		this.salesPrice = salesPrice;
		this.salesQuantity = salesQuantity;
		this.taxRate = taxRate;
		this.lineAmount = lineAmount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}


	public double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public int getSalesQuantity() {
		return salesQuantity;
	}

	public void setSalesQuantity(int salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(double lineAmount) {
		this.lineAmount = lineAmount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
