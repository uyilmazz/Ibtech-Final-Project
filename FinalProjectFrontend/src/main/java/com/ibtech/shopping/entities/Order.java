package com.ibtech.shopping.entities;

public class Order {
	private long id;
	private String addressLine1;
	private String addressLine2;
	private String customerName;
	private double totalAmount;
	
	public Order() {
		
	}

	public Order(long id, String addressLine1, String addressLine2, String userName, double totalAmount) {
		this.id = id;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.customerName = userName;
		this.totalAmount = totalAmount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String userName) {
		this.customerName = userName;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
