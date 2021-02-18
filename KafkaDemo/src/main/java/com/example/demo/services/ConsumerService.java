package com.example.demo.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import com.example.demo.model.ProgramDetail;

@Service
public final class ConsumerService {
	private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@KafkaListener(topics = "EPGProgramDetail", groupId = "kafka1")
	public void consume(ProgramDetail programDetail) {
		logger.info(String.format("$$$$ => Consumed message: %s", programDetail));

	}

	private Map<Integer, ProgramDetail> getAllRecords() {

		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "kafkaepg");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

		Consumer<String, ProgramDetail> consumer = new KafkaConsumer<>(properties, new StringDeserializer(),
				new JsonDeserializer<>(ProgramDetail.class));

		// subscribe to topic
		consumer.subscribe(Collections.singleton("EPGProgramDetail"));
		final int giveUp = 100;
		int noRecordsCount = 0;
		Map<Integer, ProgramDetail> programDetails = new HashMap<Integer, ProgramDetail>();

		while (true) {
			final ConsumerRecords<String, ProgramDetail> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, ProgramDetail> record : records) {
				programDetails.put(record.value().getProgramId(), record.value());
			}
			if (records.count() == 0) {
				noRecordsCount++;
				if (noRecordsCount > giveUp)
					break;
				else
					continue;
			}

		}
		consumer.close();
		return programDetails;

	}

	public Map<Integer, ProgramDetail> getProgramsOfChannel(String channelname) {

		Map<Integer, ProgramDetail> result = getAllRecords().entrySet().stream()
				.filter(map -> map.getValue().getChannelName().equalsIgnoreCase(channelname))
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		return result;
	}

	public Map<Integer, ProgramDetail> getProgramDetails(String programName) {
		Map<Integer, ProgramDetail> result = getAllRecords().entrySet().stream()
				.filter(map -> map.getValue().getProgramName().equalsIgnoreCase(programName))
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

		return result;
	}

	public Map<Integer, ProgramDetail> getProgramsByTimpstramp(Date timestramp) {
		System.out.println("ConsumerService.getProgramsOfChannel() timestramp " + timestramp);
		Map<Integer, ProgramDetail> result = getAllRecords().entrySet().stream()
				.filter(map -> map.getValue().getProgramSceduledTime().equals(timestramp)) // filter by value
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

		return result;
	}

	public Map<Integer, ProgramDetail> getProgramsByTimpstrampAndChannelName(String channelName, Date timestramp) {
		System.out.println("ConsumerService.getProgramsByTimpstrampAndChannelName() timestramp " + timestramp
				+ " channelName " + channelName);
		Map<Integer, ProgramDetail> result = getAllRecords().entrySet().stream()
				.filter(map -> map.getValue().getChannelName().equalsIgnoreCase(channelName))
				.filter(map -> map.getValue().getProgramSceduledTime().equals(timestramp))
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

		System.out.println("ConsumerService.getProgramsByTimpstrampAndChannelName() result "+result);
		return result;
	}

	public List<ProgramDetail> getRecord() {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "kafkaepg");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

		Consumer<String, ProgramDetail> consumer = new KafkaConsumer<>(properties, new StringDeserializer(),
				new JsonDeserializer<>(ProgramDetail.class));

		// subscribe to topic
		consumer.subscribe(Collections.singleton("EPGProgramDetail"));
		final int giveUp = 100;
		int noRecordsCount = 0;
		List<ProgramDetail> programDetails = new ArrayList<ProgramDetail>();

		while (true) {
			final ConsumerRecords<String, ProgramDetail> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, ProgramDetail> record : records) {
				programDetails.add(record.value());
			}
			if (records.count() == 0) {
				noRecordsCount++;
				if (noRecordsCount > giveUp)
					break;
				else
					continue;
			}
			// consumer.commitAsync();
		}
		System.out.println("ConsumerService.getRecord() programDetails " + programDetails);
		consumer.close();
		return programDetails;
	}

}
