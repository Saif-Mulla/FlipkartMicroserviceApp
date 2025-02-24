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
        int partitions = cluster.partitionCountForTopic(topic);
        switch (key.toString()) {
            case "Book": return 0 % partitions;
            case "Mobile": return 1 % partitions;
            case "Car": return 2 % partitions;
            default: return 0;
        }
    }

}