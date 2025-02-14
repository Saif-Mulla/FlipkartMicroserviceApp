package com.learning.FlipkartOrderProducer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.FlipkartOrderProducer.model.Order;
import com.learning.FlipkartOrderProducer.service.OrderProducerService;

@RestController
@RequestMapping("/order")
public class OrderProducerController {

	@Autowired
	private OrderProducerService service;
	
	@PostMapping("/publish")
	public void sendOrderDetails(@RequestBody Order order){
		service.sendOrderDetails(order);
		
	}
	
	
}
