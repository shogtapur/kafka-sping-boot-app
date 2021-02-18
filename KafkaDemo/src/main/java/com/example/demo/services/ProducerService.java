package com.example.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.example.demo.model.ProgramDetail;


@Service
public final class ProducerService {
	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

	private final KafkaTemplate<String, ProgramDetail> kafkaTemplate;
	private final String TOPIC = "EPGProgramDetail";

	public ProducerService(KafkaTemplate<String, ProgramDetail> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(ProgramDetail  progtamDetail) {
		logger.info(String.format("$$$$ => Producing message: %s", progtamDetail));

		ListenableFuture<SendResult<String, ProgramDetail>> future = this.kafkaTemplate.send(TOPIC, 0,"key1",progtamDetail);
		future.addCallback(new ListenableFutureCallback<>() {
			@Override
			public void onFailure(Throwable ex) {
				logger.info("Unable to send message=[ {} ] due to : {}", progtamDetail, ex.getMessage());
			}

			@Override
			public void onSuccess(SendResult<String, ProgramDetail> result) {
				logger.info("Sent message=[ {} ] with offset=[ {} ]", progtamDetail, result.getRecordMetadata().offset());
			}
		});
	}
}