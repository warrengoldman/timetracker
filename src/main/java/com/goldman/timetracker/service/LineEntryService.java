package com.goldman.timetracker.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldman.timetracker.entity.BatchEntry;
import com.goldman.timetracker.entity.LineEntry;
import com.goldman.timetracker.repository.LineEntryRepository;

@Service
public class LineEntryService {

	@Autowired
	private LineEntryRepository lineEntryRepository;

	@Autowired
	private BatchService batchService;

	@Autowired
	private TimeEntryService timeEntryService;

	@Autowired
	private DateService dateService;

	public LineEntry processLine(String line) {
		LineEntry lineEntry = new LineEntry();
		lineEntry.setLine(line);
		lineEntryRepository.save(lineEntry);
		processLineEntry(lineEntry);
		return lineEntry;
	}

	private void processLineEntry(LineEntry lineEntry) {
		Date batchDate = dateService.getDate(lineEntry.getLine());
		if (batchDate != null) {
			BatchEntry batchEntry = new BatchEntry();
			batchEntry.setBatchDate(batchDate);
			batchEntry.setLineSk(lineEntry.getLineSk());
			batchService.save(batchEntry);
		} else {
			timeEntryService.processTimeEntry(lineEntry);
		}
	}
}
