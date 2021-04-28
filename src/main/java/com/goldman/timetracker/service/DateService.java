package com.goldman.timetracker.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateService {

	public Date getDate(String dateString) {
		Date aDate = null;
		if (dateString != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			try {
				aDate = sdf.parse(dateString.substring(0, 10));
			} catch (Exception notDate) {
				// ignore not a date
			}
		}
		return aDate;
	}

	public boolean lineStartsWithDayOfWeek(String line) {
		String lineLower = line.toLowerCase();
		return lineLower.startsWith("sunday")
				|| lineLower.startsWith("monday")
				|| lineLower.startsWith("tuesday")
				|| lineLower.startsWith("wednesday")
				|| lineLower.startsWith("thursday")
				|| lineLower.startsWith("friday")
				|| lineLower.startsWith("saturday")				
				;
	}

	public String getLinesDayOfWeek(String line) {
		if (lineStartsWithDayOfWeek(line)) {
			int spaceIndex = line.indexOf(" ");
			return line.substring(0, spaceIndex);
		}
		return null;
	}
}
