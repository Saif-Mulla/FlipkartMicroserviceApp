package com.learning.FlipkartKafkaSolution;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class OrderPartitioner implements Partitioner {
    @Override
    public void configure(Map<String, ?> configs) {}

    @Override
    public void close() {}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (key.toString().contains("Book")) return 0;
        else if (key.toString().contains("Mobile")) return 1;
        else return 2;
	}
}