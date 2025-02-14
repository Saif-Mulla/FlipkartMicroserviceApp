package com.learning.FlipkartOrderConsumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import com.learning.FlipkartOrderConsumer.model.Order;

@Service
public class CarConsumerService {

	@KafkaListener(
			topics = "FlipkartOrdersTopic", 
			groupId = "flipkart-orders-group", 
			topicPartitions = @TopicPartition(topic = "FlipkartOrdersTopic", partitions = {"2"}),
			containerFactory = "getKafkaListener")
	public void carConsumerMessages(Order order) {
		System.out.println("Car Order Type Message: "+order);
	}
}
