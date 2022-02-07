package com.goldman.timetracker.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldman.timetracker.entity.DummyData;
import com.goldman.timetracker.repository.DummyDataRepository;

@Controller
public class HealthCheckController {

	private List<String> messages = new ArrayList<>();

	@Autowired
	private DummyDataRepository dummyDataRepository;

	@GetMapping("healthcheck/dummydata")
	public @ResponseBody String getDummyData() {
		List<String> retVals = new ArrayList<>();
		DummyData dummyData = dummyDataRepository.findByDescription("healthcheck");
		if (dummyData == null) {
			dummyData = new DummyData();
			dummyData.setDescription("healthcheck");
			dummyDataRepository.save(dummyData);
		}
		retVals.add(dummyData.getDummyDataSk() + ":" + dummyData.getDescription());
		retVals.addAll(messages);
		messages = new ArrayList<>();
		return String.join(",", retVals);
	}

//    @KafkaListener(id = "topic2", topics = "topic2", concurrency = "1")
    public void listen(ConsumerRecord<String, String> in) {
        messages.add(in.value());
    }	
}