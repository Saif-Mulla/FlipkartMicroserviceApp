package com.learning.FlipkartKafkaSolution;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class FlipkartConsumer {

    private KafkaConsumer<String, String> consumer;
    private Producer<String, String> reportProducer;

    // Persistent aggregated data
    private final Map<String, Integer> orderCount = new HashMap<>();
    private final Map<String, Double> orderTotal = new HashMap<>();

    public FlipkartConsumer() {
        this.consumer = new KafkaConsumer<>(FlipkartProperties.getConsumerProperties("order_aggregator"));
        this.reportProducer = new KafkaProducer<>(FlipkartProperties.getProducerProperties());
        consumer.subscribe(Collections.singletonList("FlipkartOrdersTopic"));
    }

    public void consumeAndAggregateOrders() {
        try {
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(5));
                boolean updated = false;

                for (ConsumerRecord<String, String> record : consumerRecords) {
                    String type = record.key();
                    String[] parts = record.value().split(",");

                    if (parts.length < 4) {
                        System.err.println("Skipping malformed record: " + record.value());
                        continue;
                    }

                    double price = Double.parseDouble(parts[3]);

                    orderCount.put(type, orderCount.getOrDefault(type, 0) + 1);
                    orderTotal.put(type, orderTotal.getOrDefault(type, 0.0) + price);
                    updated = true;
                }

                // Publish only if there is new data
                if (updated) {
                    String reportMessage = String.format(
                        "Books_order_count: %d, Books_total_transaction_amount: %.2f, " +
                        "Mobiles_order_count: %d, Mobiles_total_transaction_amount: %.2f, " +
                        "Cars_order_count: %d, Cars_total_transaction_amount: %.2f",
                        orderCount.getOrDefault("Book", 0), orderTotal.getOrDefault("Book", 0.0),
                        orderCount.getOrDefault("Mobile", 0), orderTotal.getOrDefault("Mobile", 0.0),
                        orderCount.getOrDefault("Car", 0), orderTotal.getOrDefault("Car", 0.0)
                    );

                    reportProducer.send(new ProducerRecord<>("ReportTopic", "AggregatedReport", reportMessage));
                    System.out.println("Published Aggregated Report: " + reportMessage);
                    consumer.commitSync();
                }
            }
        } finally {
            consumer.close();
            reportProducer.close();
        }
    }

    public static void main(String[] args) {
        FlipkartConsumer flipkartConsumer = new FlipkartConsumer();
        flipkartConsumer.consumeAndAggregateOrders();
    }
}
