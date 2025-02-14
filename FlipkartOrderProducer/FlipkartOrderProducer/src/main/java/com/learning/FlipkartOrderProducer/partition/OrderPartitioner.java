package com.learning.FlipkartOrderProducer.partition;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class OrderPartitioner implements Partitioner {
	
	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		
		int numPartitions = cluster.partitionCountForTopic(topic);
		
		String orderType = (String) key;
		int partition;
		
		switch (orderType) {
		case "MOBILES": {
			partition = 0;
			break;
		}
		case "BOOKS": {
			partition = 1;
			break;
		}
		case "CARS": {
			partition = 2;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + orderType);
		}
		
		return partition % numPartitions;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	
}

