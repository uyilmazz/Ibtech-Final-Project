package com.ibtech.entities;


public class Cart {
	private long id;
	private String customerName;
	private double totalAmount;
	
	public Cart() {
		
	}

	public Cart(long id, String customerName, double totalAmount) {
		this.id = id;
		this.customerName = customerName;
		this.totalAmount = totalAmount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
