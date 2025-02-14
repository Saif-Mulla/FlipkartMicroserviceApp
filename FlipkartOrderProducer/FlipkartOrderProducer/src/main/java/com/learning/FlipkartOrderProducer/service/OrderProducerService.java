package com.learning.FlipkartOrderProducer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.learning.FlipkartOrderProducer.model.Order;

@Service
public class OrderProducerService {

	private static final String TOPIC_NAME = "FlipkartOrdersTopic";
	
	@Autowired
	private KafkaTemplate<String, Order> kafkaTemplate;
	
	public void sendOrderDetails(Order order) {
		kafkaTemplate.send(TOPIC_NAME, order.getOrderTitle() ,order);
		System.out.println("Order details sent: "+order.getOrderId());
	}

}
