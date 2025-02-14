package com.learning.FlipkartOrderConsumer;

import org.springframework.messaging.handler.annotation.Payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.FlipkartOrderConsumer.model.Order;

public class DesearilizerConsumerConfiguration {

	public void mobilesConsumerMessages(@Payload String orderJson) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Order order = objectMapper.readValue(orderJson, Order.class);
	        System.out.println("Mobiles Order Type Message: " + order);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void booksConsumerMessages(@Payload String orderJson) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Order order = objectMapper.readValue(orderJson, Order.class);
	        System.out.println("Books Order Type Message: " + order);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void carConsumerMessages(@Payload String orderJson) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Order order = objectMapper.readValue(orderJson, Order.class);
	        System.out.println("Car Order Type Message: " + order);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
