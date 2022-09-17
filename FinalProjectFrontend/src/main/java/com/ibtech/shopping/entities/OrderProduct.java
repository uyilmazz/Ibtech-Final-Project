package com.ibtech.shopping.entities;

public class OrderProduct {
	private long id;
	private long orderId;
	private String productName;
	private String imagePath;
	private double salesPrice;
	private int salesQuantity;
	private double taxRate;
	private double lineAmount;
	
	public OrderProduct() {
		
	}

	public OrderProduct(long id, long orderId,String productName,
			String imagePath, double salesPrice, int salesQuantity,  double taxRate, double lineAmount) {
		this.id = id;
		this.orderId = orderId;
		this.salesPrice = salesPrice;
		this.salesQuantity = salesQuantity;
		this.productName = productName;
		this.imagePath = imagePath;
		this.taxRate = taxRate;
		this.lineAmount = lineAmount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
}
