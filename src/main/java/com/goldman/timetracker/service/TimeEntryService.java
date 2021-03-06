package com.goldman.timetracker.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldman.timetracker.entity.BatchEntry;
import com.goldman.timetracker.entity.LineEntry;
import com.goldman.timetracker.entity.TimeEntry;
import com.goldman.timetracker.repository.TimeEntryRepository;

@Service
public class TimeEntryService {

	@Autowired
	private DateService dateService;

	@Autowired
	private BatchService batchService;

	@Autowired
	private TimeEntryRepository timeEntryRepository;

	public TimeEntry processTimeEntry(LineEntry lineEntry) {
		String line = lineEntry.getLine();
		if (line == null) {
			return null;
		}
		String linesDayOfWeek = dateService.getLinesDayOfWeek(line);
		Date timeEntryDate;
		String activityDescription;
		BigDecimal hours;
		Boolean billable;
		Integer lineSk = lineEntry.getLineSk();
		BatchEntry batchEntry = null;
		if (linesDayOfWeek != null) {
			batchEntry = batchService.findFirstByOrderByBatchDateDesc();
			timeEntryDate = getDayFollowing(batchEntry.getBatchDate(), linesDayOfWeek);
			billable = false;
			activityDescription = linesDayOfWeek;
			hours = getHours(line.substring(linesDayOfWeek.length()).trim());
		} else {
			batchEntry = batchService.findFirstByOrderByBatchDateDesc();
			TimeEntry mostRecentTimeEntry = timeEntryRepository.findFirstByOrderByTimeEntryDateDesc();
			if (mostRecentTimeEntry != null) {
				timeEntryDate = mostRecentTimeEntry.getTimeEntryDate();
			} else {
				timeEntryDate = new Date();
			}
			billable = isBillable(line);
			activityDescription = getActivityDescription(line);
			hours = getHours(line.trim().substring(activityDescription.length()));
		}
		TimeEntry timeEntry = timeEntryRepository.findByTimeEntryDateAndActivityDescriptionAndHoursAndBillable(timeEntryDate, activityDescription, hours, billable);
		if (timeEntry == null) {
			timeEntry = createTimeEntry(timeEntryDate, activityDescription, hours, billable, lineSk, batchEntry.getBatchSk());
			timeEntry = timeEntryRepository.save(timeEntry);
		}
		return timeEntry;
	}

	String getActivityDescription(String line) {
		String trimmedLine = line.trim();
		try {
			return trimmedLine.substring(0, trimmedLine.indexOf(' '));
		} catch (Exception e) {
			throw new RuntimeException("exception processing " + line, e);
		}
	}

	private Boolean isBillable(String line) {
		return !line.toLowerCase().trim().startsWith("nb");
	}

	private Date getDayFollowing(Date batchDate, String linesDayOfWeek) {
		ZoneId systemDefault = ZoneId.systemDefault();
		LocalDateTime ldt = LocalDateTime.ofInstant(batchDate.toInstant(), systemDefault);
		String dayOfWeek = ldt.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
		while (!dayOfWeek.equalsIgnoreCase(linesDayOfWeek)) {
			ldt = ldt.plusDays(1);
			dayOfWeek = ldt.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()).toLowerCase();
		}
		return Date.from(ldt.atZone(systemDefault).toInstant());
	}

	private TimeEntry createTimeEntry(Date timeEntryDate, String activityDescription, BigDecimal hours,
			Boolean billable, Integer lineSk, Integer batchEntrySk) {
		TimeEntry timeEntry = new TimeEntry();
		timeEntry.setTimeEntryDate(timeEntryDate);
		timeEntry.setActivityDescription(activityDescription);
		timeEntry.setHours(hours);
		timeEntry.setBillable(billable);
		timeEntry.setLineSk(lineSk);
		timeEntry.setBatchEntrySk(batchEntrySk);
		return timeEntry;
	}

	BigDecimal getHours(String timeStr) {
		return BigDecimal.valueOf(getTicketTime(timeStr));
	}

	private static Double getTicketTime(String ticketTimeStr) {
		int dashIndex = ticketTimeStr.indexOf('-');
		String startTimeStr = ticketTimeStr.substring(0, dashIndex);
		String endTimeStr = ticketTimeStr.substring(dashIndex + 1);
		boolean isPm = isPm(startTimeStr, endTimeStr);
		return getTime(endTimeStr, isPm) - getTime(startTimeStr, null);
	}

	private static boolean isPm(String startTimeStr, String endTimeStr) {
		String startHour = startTimeStr;
		String endHour = endTimeStr;
		if (startTimeStr.indexOf(':') != -1) { 
			startHour = startTimeStr.substring(0, startTimeStr.indexOf(':'));
		}
		if (endTimeStr.indexOf(':') != -1) {
			endHour = endTimeStr.substring(0, endTimeStr.indexOf(':'));
		}
		return Double.valueOf(startHour.trim()) > Double.valueOf(endHour.trim());
	}

	private static Double getTime(String timeStr, Boolean isPm) {
		Double retVal = null;
		int colonIndex = timeStr.indexOf(':');
		if (colonIndex == -1) {
			retVal = Double.valueOf(timeStr.trim()) ;
		} else {
			String hour = timeStr.substring(0, colonIndex);
			String minute = timeStr.substring(colonIndex + 1);
			if (minute.substring(0,1).equals("0")) {
				minute = minute.substring(1);
			}
			retVal = Double.valueOf(hour.trim()) + (Double.valueOf(minute)/60);
		}
		if (isPm != null && isPm) {
			retVal = retVal + 12;
		}
		return retVal;
	}

	public List<TimeEntry> findByTimeEntryDateGreaterThanEqual(Date fromDate) {
		return timeEntryRepository.findByTimeEntryDateGreaterThanEqual(fromDate);
	}
}
