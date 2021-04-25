package com.goldman.timetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldman.timetracker.entity.DummyData;
import com.goldman.timetracker.repository.DummyDataRepository;

@Controller
public class HealthCheckController {

	@Autowired
	private DummyDataRepository dummyDataRepository;

	@GetMapping("healthcheck/dummydata")
	public @ResponseBody DummyData getDummyData() {
		DummyData dummyData = dummyDataRepository.findByDescription("healthcheck");
		if (dummyData == null) {
			dummyData = new DummyData();
			dummyData.setDescription("healthcheck");
			dummyDataRepository.save(dummyData);
		}
		return dummyData;
	}
	
}
