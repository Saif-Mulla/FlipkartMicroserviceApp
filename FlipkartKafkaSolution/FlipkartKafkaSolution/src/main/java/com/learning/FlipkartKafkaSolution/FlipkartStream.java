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
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

public class FlipkartStream {

	public static void main(String[] args) {
        
	       StreamsBuilder builder = new StreamsBuilder();
	        
	        // Read orders from FlipkartOrdersTopic
	        KStream<String, String> orders = builder.stream("FlipkartOrdersTopic");

	        // Parse and process the orders
	        KTable<String, Long> orderCounts = orders
	            .mapValues(value -> parseOrderCategory(value)) // Extract order type
	            .groupBy((key, orderType) -> orderType) // Group by order type
	            .count(Materialized.as("OrderCounts"));

	        KTable<String, Long> orderAmounts = orders
	            .mapValues(value -> parseOrderAmount(value)) // Extract order amount
	            .groupByKey()
	            .reduce(Long::sum, Materialized.as("OrderAmounts"));

	        // Join both tables (Count + Amount) and send results to ReportTopic
	        KTable<String, String> reportTable = orderCounts.join(orderAmounts,
	            (count, amount) -> orderTypeToJson(count, amount));

	        reportTable.toStream().to("ReportTopic", Produced.with(Serdes.String(), Serdes.String()));

	        KafkaStreams streams = new KafkaStreams(builder.build(), FlipkartProperties.getStreamProperties());
	        streams.start();

	        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	    }

		private static String parseOrderCategory(String value) {
		    String[] fields = value.split(",");
		    return fields.length > 1 ? fields[1] : "Unknown";
		}
		
		private static long parseOrderAmount(String value) {
		    try {
		        String[] fields = value.split(",");
		        return fields.length > 3 ? Long.parseLong(fields[3]) : 0L;
		    } catch (NumberFormatException e) {
		        return 0L;
		    }
		}	    

		private static String orderTypeToJson(Long count, Long amount) {
	        return "{ \"order_count\": " + count + ", \"total_transaction_amount\": " + amount + " }";
	    }
}
