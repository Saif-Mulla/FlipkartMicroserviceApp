package com.learning.FlipkartKafkaSolution;

import java.time.Duration;
import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ReportConsumer {
    
    private KafkaConsumer<String, String> consumer;

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(FlipkartProperties.getConsumerProperties());
        consumer.subscribe(Collections.singletonList("ReportTopic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Aggregated Order Data: " + record.value());
            }
        }
    }
}
