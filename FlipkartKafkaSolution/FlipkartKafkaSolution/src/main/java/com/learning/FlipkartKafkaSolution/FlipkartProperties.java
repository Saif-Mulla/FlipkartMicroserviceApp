package com.learning.FlipkartKafkaSolution;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

public class FlipkartProperties {

    public static Properties getConsumerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "report-consumer-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "com.learning.FlipkartKafkaSolution.OrderDeserialzer");
        props.put("auto.offset.reset", "earliest");
        return props;
    }

    public static Properties getStreamProperties() {
    	Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "flipkart-streams");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        return props;
    }

    
    public static Properties getProducerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "com.learning.FlipkartKafkaSolution.OrderSerializer");
        props.put("partitioner.class", OrderPartitioner.class.getName());
        return props;
    }
}
