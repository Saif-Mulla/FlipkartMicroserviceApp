package com.learning.FlipkartOrderConsumer.service;

import org.springframework.kafka.annotation.*;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.learning.FlipkartOrderConsumer.model.Order;

@Service
public class BooksConsumerService {
	
	@KafkaListener(
			topics = "FlipkartOrdersTopic", 
			groupId = "flipkart-orders-group", 
			topicPartitions = @TopicPartition(topic = "FlipkartOrdersTopic", partitions = {"1"}),
			containerFactory = "getKafkaListener")
	public void booksConsumerMessages(Order order) {
		System.out.println("Books Order Type Message: "+order);
	}
}
