package com.learning.FlipkartKafkaSolution;

public class OrderModel {
    private String id, type, details;
    private double price;
    private String customer;

    public OrderModel(String id, String type, String details, double price, String customer) {
        this.id = id;
        this.type = type;
        this.details = details;
        this.price = price;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + details + "," + price + "," + customer;
    }
    
    public void setCustomer(String customer) {
		this.customer = customer;
	}
    
    public void setDetails(String details) {
		this.details = details;
	}
    
    public void setId(String id) {
		this.id = id;
	}
    
    public void setPrice(double price) {
		this.price = price;
	}
    
    public void setType(String type) {
		this.type = type;
	}
    
    public String getCustomer() {
		return customer;
	}
    
    public String getDetails() {
		return details;
	}
    
    public String getId() {
		return id;
	}
    
    public double getPrice() {
		return price;
	}
    
    public String getType() {
		return type;
	}
    
 
}
