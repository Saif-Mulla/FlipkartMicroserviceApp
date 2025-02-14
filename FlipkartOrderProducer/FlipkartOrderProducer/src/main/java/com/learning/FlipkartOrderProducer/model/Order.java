package com.learning.FlipkartOrderProducer.model;

import java.util.Date;

public class Order {

	private int orderId;
	private String orderTitle;
	private double orderPrice;
	private String customerName;
	private String customerEmail;
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public double getOrderPrice() {
		return orderPrice;
	}
	
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	public String getOrderTitle() {
		return orderTitle;
	}
	
	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}
	
	
}
