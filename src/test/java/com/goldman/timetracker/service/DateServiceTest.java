package com.goldman.timetracker.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DateServiceTest {

	private DateService dateService = new DateService();

	@Test
	void checkAutowire() {
		assertNotNull(dateService);
	}

	@Test
	void getDaysOfWeek() {
		String line = "	password-reset 8:55-9:15";
		assertNull(dateService.getLinesDayOfWeek(line));
	}
}
