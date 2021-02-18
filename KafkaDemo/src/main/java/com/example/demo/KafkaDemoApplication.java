package com.example.demo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ProgramDetail;
import com.example.demo.services.ConsumerService;
import com.example.demo.services.ProducerService;

@RestController
@SpringBootApplication
@RequestMapping(value = "/kafka")
public class KafkaDemoApplication {

	private final ProducerService producerService;
	private final ConsumerService consumerService;
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoApplication.class, args);
	}

	public KafkaDemoApplication(ProducerService producerService, ConsumerService consumerService) {
		this.producerService = producerService;
		this.consumerService = consumerService;

	}
	
	@PostMapping("/StoreProgramAndSchedule")
	public String  storeProgramAndSchedule(
	        @RequestParam("programId") int programId, 
	        @RequestParam("programName") String programName, 
			@RequestParam("channelName") String channelName,
			@RequestParam("programSceduledTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date programSceduledTime) {
	     
	    ProgramDetail programDetail = new ProgramDetail();
	    programDetail.setProgramId(programId);
	    programDetail.setProgramName(programName);
	    programDetail.setChannelName(channelName);
	    programDetail.setProgramSceduledTime(programSceduledTime);
	   
	    producerService.sendMessage(programDetail);
	    return programDetail.getProgramName() +" added successfully";
	}
	
	@RequestMapping(value = "/getEpgDetails")
	public List<ProgramDetail> getMessageToKafkaTopic() {
		 return consumerService.getRecord();
	}

	@RequestMapping(value = "/getProgramDetails")
	public Map<Integer, ProgramDetail> getProgramDetails(@RequestParam("programName") String programName) {
		return consumerService.getProgramDetails(programName);
	}
	@RequestMapping(value = "/getProgramsOfChannel")
	public Map<Integer, ProgramDetail> getProgramsOfChannel(@RequestParam("channelName") String channelName) {
		return consumerService.getProgramsOfChannel(channelName);
	}

	@RequestMapping(value = "/getProgramsByTimpstramp")
	public Map<Integer, ProgramDetail> getProgramsByTimpstramp(
			@RequestParam("programSceduledTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date programSceduledTime) {
		return consumerService.getProgramsByTimpstramp(programSceduledTime);
	}
	
	@RequestMapping(value = "/getProgramsByTimpstrampAndChannelName")
	public Map<Integer, ProgramDetail> getProgramsByTimpstrampAndChannelName(
			@RequestParam("channelName") String channelName,
			@RequestParam("programSceduledTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date programSceduledTime) {
		return consumerService.getProgramsByTimpstrampAndChannelName(channelName,programSceduledTime);
	}
	
	
	


}
