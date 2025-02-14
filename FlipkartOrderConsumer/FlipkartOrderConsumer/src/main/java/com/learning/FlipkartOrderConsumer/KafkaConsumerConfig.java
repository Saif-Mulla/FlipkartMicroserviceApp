package com.learning.FlipkartOrderConsumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.learning.FlipkartOrderConsumer.model.Order;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, Order> consumerFactory(){
	    Map<String, Object> props = new HashMap<>();
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	    props.put(ConsumerConfig.GROUP_ID_CONFIG, "flipkart-orders-group");
	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
	    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Allow all packages
	    return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
	            new JsonDeserializer<>(Order.class, false));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Order> getKafkaListener() {
		ConcurrentKafkaListenerContainerFactory< String, Order> listener = new ConcurrentKafkaListenerContainerFactory<String, Order>();
		listener.setConsumerFactory(consumerFactory());
		return listener;
	}
	
//	@Bean
//	public ConsumerFactory<String, Order> consumerFactory(){
//	    Map<String, Object> props = new HashMap<>();
//
//	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//	    props.put(ConsumerConfig.GROUP_ID_CONFIG, "flipkart-orders-group");
//	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//	    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//	    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//	    
//	    // Create JsonDeserializer
//	    JsonDeserializer<Order> deserializer = new JsonDeserializer<>(Order.class, false);
//	    deserializer.addTrustedPackages("com.learning.FlipkartOrderConsumer.model"); // Specify exact package
//	    deserializer.setRemoveTypeHeaders(false);
//	    deserializer.setUseTypeMapperForKey(false);
//	    
//	    return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
//	}
//	
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, Order> getKafkaListener() {
//		ConcurrentKafkaListenerContainerFactory< String, Order> listener = new ConcurrentKafkaListenerContainerFactory<String, Order>();
//		listener.setConsumerFactory(consumerFactory());
//		return listener;
//	}
	
}
