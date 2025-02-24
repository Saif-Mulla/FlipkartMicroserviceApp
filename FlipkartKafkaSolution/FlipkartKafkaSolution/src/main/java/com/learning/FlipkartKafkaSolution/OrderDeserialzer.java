package com.learning.FlipkartKafkaSolution;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderDeserialzer implements Deserializer<OrderModel> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public OrderModel deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, OrderModel.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing OrderModel", e);
        }
    }

    @Override
    public void close() {
        // Nothing to close
    }
}
