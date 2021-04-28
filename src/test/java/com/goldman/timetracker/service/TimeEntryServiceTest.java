package com.goldman.timetracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class TimeEntryServiceTest {

	private TimeEntryService timeEntryService = new TimeEntryService();

	@Test
	void checkAutowire() {
		assertNotNull(timeEntryService);
	}

	@Test
	void getActivityDescription() {
		String line = "	password-reset 8:55-9:15";
		assertEquals("password-reset", timeEntryService.getActivityDescription(line));
	}

	@Test
	void getHours() {
		String line = "	password-reset 8:55-9:10";
		String activityDescription = timeEntryService.getActivityDescription(line);
		assertEquals(new BigDecimal(".25"), timeEntryService.getHours(line.trim().substring(activityDescription.length())));
		
		line = "	password-reset 8:55-9:55";
		activityDescription= timeEntryService.getActivityDescription(line);
		assertEquals(new BigDecimal("1.0"), timeEntryService.getHours(line.trim().substring(activityDescription.length())));

		
		line = "	password-reset 8:55-6:55";
		activityDescription= timeEntryService.getActivityDescription(line);
		assertEquals("10.0", timeEntryService.getHours(line.trim().substring(activityDescription.length())).toString().substring(0,4));
	}
}
