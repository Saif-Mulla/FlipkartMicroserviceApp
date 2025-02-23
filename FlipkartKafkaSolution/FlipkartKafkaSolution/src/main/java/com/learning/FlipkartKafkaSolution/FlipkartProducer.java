package com.learning.FlipkartKafkaSolution;

import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class FlipkartProducer {
    
    private KafkaProducer<String, String> producer;

    public FlipkartProducer() {
        this.producer = new KafkaProducer<>(FlipkartProperties.getProducerProperties());
    }

    public void sendOrders() {
        String topic = "FlipkartOrdersTopic";
        List<OrderModel> orders = List.of(
            new OrderModel("1", "Book", "Java Programming", 1500, "Alice"),
            new OrderModel("2", "Mobile", "Samsung S21", 75000, "Bob"),
            new OrderModel("3", "Car", "Tesla Model 3", 5800000, "Charlie")
        );

        for (OrderModel order : orders) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, order.getType(), order.toString());
            producer.send(record);
        }
        
        producer.close();
    }

    public static void main(String[] args) {
        FlipkartProducer flipkartProducer = new FlipkartProducer();
        flipkartProducer.sendOrders();
    }
}
