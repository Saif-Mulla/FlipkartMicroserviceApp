package com.learning.FlipkartOrderProducer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.learning.FlipkartOrderProducer.model.Order;
import com.learning.FlipkartOrderProducer.partition.OrderPartitioner;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic topic() {
		return TopicBuilder
				.name("FlipkartOrdersTopic")
				.partitions(3)
				.replicas(1)
				.build();
	}
	
	@Bean
	public ProducerFactory< String, Order> producerFactory(){
		
		Map props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, OrderPartitioner.class);
		
		return new DefaultKafkaProducerFactory<String, Order>(props);
	}
	
	@Bean
	public KafkaTemplate<String, Order> getKafkaTemplate(){
		return new KafkaTemplate<String, Order>(producerFactory());
	}
	
}
