package com.learning.FlipkartKafkaSolution;

import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class FlipkartProducer {
    
    private KafkaProducer<String, String> producer;

    public FlipkartProducer() {
        this.producer = new KafkaProducer<>(getProducerProperties());
    }

    private Properties getProducerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class", OrderPartitioner.class.getName());
        return props;
    }

    public void sendOrders() {
        String topic = "FlipkartOrdersTopic";
        List<OrderModel> orders = List.of(
            new OrderModel("1", "Book", "Java Programming", 500, "Alice"),
            new OrderModel("2", "Mobile", "Samsung S21", 70000, "Bob"),
            new OrderModel("3", "Car", "Tesla Model 3", 5000000, "Charlie")
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
