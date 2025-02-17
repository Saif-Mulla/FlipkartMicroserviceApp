package com.learning.FlipkartKafkaSolution;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class FlipkartConsumer {
    
    private KafkaConsumer<String, String> consumer;

    public FlipkartConsumer() {
        this.consumer = new KafkaConsumer<>(getProperties());
        consumer.subscribe(Collections.singletonList("FlipkartOrdersTopic"));
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "order_consumers");
        props.put("enable.auto.commit", "false");  // Fix: Manually commit offsets
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");
        return props;
    }

    public void consumeOrders() {
        Map<String, Integer> orderCount = new HashMap<>();
        Map<String, Double> orderTotal = new HashMap<>();
        
        try {
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(5));
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    System.out.println("Received: Partition=" + record.partition() + 
                                       ", Key=" + record.key() + 
                                       ", Value=" + record.value());

                    String[] parts = record.value().split(",");
                    if (parts.length < 4) {
                        System.err.println("Skipping malformed record: " + record.value());
                        continue;
                    }

                    String type = (record.key() != null) ? record.key() : "UNKNOWN";
                    double price = Double.parseDouble(parts[3]);

                    orderCount.put(type, orderCount.getOrDefault(type, 0) + 1);
                    orderTotal.put(type, orderTotal.getOrDefault(type, 0.0) + price);

                    System.out.println("Aggregated Orders: " + orderCount + " " + orderTotal);
                    consumer.commitSync();
                }
    		}
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        FlipkartConsumer flipkartConsumer = new FlipkartConsumer();
        flipkartConsumer.consumeOrders();
    }
}
