package com.goldman.timetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldman.timetracker.entity.LineEntry;
import com.goldman.timetracker.service.LineEntryService;

@Controller
public class LineEntryController {

	@Autowired
	private LineEntryService lineEntryService;

	@PostMapping("line")
	public @ResponseBody Integer processLine(@RequestBody String line) {
		LineEntry lineEntry = lineEntryService.processLine(line);
		return lineEntry.getLineSk();
	}
}
