package com.learning.FlipkartKafkaSolution;

import java.time.Duration;
import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ReportConsumer {
    
    private KafkaConsumer<String, String> consumer;

    public ReportConsumer() {
        this.consumer = new KafkaConsumer<>(FlipkartProperties.getConsumerProperties("report_consumer"));
        consumer.subscribe(Collections.singletonList("ReportTopic"));
    }

    public void consumeReports() {
        try {
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(5));
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    System.out.println("Aggregated Report: " + record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        ReportConsumer reportConsumer = new ReportConsumer();
        reportConsumer.consumeReports();
    }
}
