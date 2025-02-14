package com.learning.FlipkartOrderConsumer.service;

import java.time.Duration;
import java.util.Collections;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.Service;

import com.learning.FlipkartOrderConsumer.model.Order;

@Service
public class MobileConsumerService {
	
	@KafkaListener(
			topics = "FlipkartOrdersTopic", 
			groupId = "flipkart-orders-group", 
			topicPartitions = @TopicPartition(topic = "FlipkartOrdersTopic", partitions = {"0"}),
			containerFactory = "getKafkaListener")
	public void mobilesConsumerMessages(Order order) {
		System.out.println("Mobiles Order Type Message: "+order);
	}
}
