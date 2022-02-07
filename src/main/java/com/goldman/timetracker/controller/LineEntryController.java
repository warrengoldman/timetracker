package com.goldman.timetracker.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.goldman.timetracker.entity.LineEntry;
import com.goldman.timetracker.service.LineEntryService;

@RestController
public class LineEntryController {

	@Autowired
	private LineEntryService lineEntryService;

	@PostMapping("line")
	public Integer processLine(@RequestBody String line) {
		LineEntry lineEntry = lineEntryService.processLine(line);
		return lineEntry.getLineSk();
	}

    @KafkaListener(id = "topic2", topics = "topic2", concurrency = "1")
    public Integer listen(ConsumerRecord<String, String> in) {
    	String messageValue = in.value();
		return processLine(messageValue);
    }
}
