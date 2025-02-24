package com.learning.FlipkartKafkaSolution;

import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class FlipkartProducer {
    
    private KafkaProducer<String, OrderModel> producer;

    public FlipkartProducer() {
        this.producer = new KafkaProducer<>(FlipkartProperties.getProducerProperties());
    }

    public void sendOrders() {
        String topic = "FlipkartOrdersTopic";
        List<OrderModel> orders = List.of(
            new OrderModel("1", "Book", "Java Programming", 1500, "Saif"),
            new OrderModel("2", "Mobile", "Samsung S21", 75000, "Saif2"),
            new OrderModel("3", "Car", "Tesla Model 3", 5800000, "Saif3")
        );

        for (OrderModel order : orders) {
            ProducerRecord<String, OrderModel> record = new ProducerRecord<>(topic, order.getType(), order);
            producer.send(record);
        }
        
        producer.flush();
        producer.close();
    }

    public static void main(String[] args) {
        FlipkartProducer flipkartProducer = new FlipkartProducer();
        flipkartProducer.sendOrders();
    }
}
