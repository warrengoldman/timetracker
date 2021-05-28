package com.goldman.timetracker.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldman.timetracker.entity.TimeEntry;
import com.goldman.timetracker.service.DateService;
import com.goldman.timetracker.service.TimeEntryService;

@Controller
public class TimeEntryReportController {

	@Autowired
	private TimeEntryService timeEntryService;

	@Autowired
	private DateService dateService;

	@PostMapping("timeReport/forWeek")
	public @ResponseBody List<TimeEntry> getTimeReport(@RequestBody String fromDateStr) {
		Date fromDate = dateService.getDate(fromDateStr);
		List<TimeEntry> timeEntries = timeEntryService.findByTimeEntryDateGreaterThanEqual(fromDate);
		sort(timeEntries);
		return timeEntries;
	}

	void sort(List<TimeEntry> timeEntries) {
		Collections.sort(timeEntries, getComparator());
	}

	private Comparator<TimeEntry> getComparator() {
		Comparator<TimeEntry> comparator = new Comparator<TimeEntry>() {
			@Override
			public int compare(TimeEntry te1, TimeEntry te2) {
				String te1Desc = te1.getActivityDescription();
				String te2Desc = te2.getActivityDescription();
				if (te1Desc.contains("-") && !te2Desc.contains("-")) {
					return 1;
				} else if (te2Desc.contains("-") && !te1Desc.contains("-")) {
					return -1;
				} else if (te2Desc.contains("-") && te1Desc.contains("-")) {
					return compareByDescriptionThenDate(te1, te2);
				} else {
					if (te1.getBillable() && !te2.getBillable()) {
						return 1;
					} else if (!te1.getBillable() && te2.getBillable()) {
						return -1;
					} else if (te1.getBillable() && te2.getBillable()) {
						return compareByDateThenDescription(te1, te2);						
					}
					if (isDayOfWeek(te1.getActivityDescription()) && isDayOfWeek(te2.getActivityDescription())) {
						return sortDayOfWeek(te1.getActivityDescription(), te2.getActivityDescription());
					} else if (!isDayOfWeek(te1.getActivityDescription()) && !isDayOfWeek(te2.getActivityDescription())) {
						return compareByDateThenDescription(te1, te2);
					} else if (isDayOfWeek(te1.getActivityDescription())) {
						return -1;
					}
					return 1;
				}
			}

			private int sortDayOfWeek(String dayOfWeek1, String dayOfWeek2) {
				Map<String, Integer> dayOfWeekSortMap = new HashMap<>();
				dayOfWeekSortMap.put("monday", Integer.valueOf(1));
				dayOfWeekSortMap.put("tuesday", Integer.valueOf(2));
				dayOfWeekSortMap.put("wednesday", Integer.valueOf(3));
				dayOfWeekSortMap.put("thursday", Integer.valueOf(4));
				dayOfWeekSortMap.put("friday", Integer.valueOf(5));
				dayOfWeekSortMap.put("saturday", Integer.valueOf(6));
				dayOfWeekSortMap.put("sunday", Integer.valueOf(7));
				Integer sortOrderDayOfWeek1 = dayOfWeekSortMap.get(dayOfWeek1.toLowerCase());
				Integer sortOrderDayOfWeek2 = dayOfWeekSortMap.get(dayOfWeek2.toLowerCase());
				return sortOrderDayOfWeek1.compareTo(sortOrderDayOfWeek2);
			}

			private boolean isDayOfWeek(String activityDescription) {
				return Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday").contains(activityDescription.toLowerCase());
			}

			private int compareByDescriptionThenDate(TimeEntry te1, TimeEntry te2) {
				int compareTo = te1.getActivityDescription().compareTo(te2.getActivityDescription());
				if (compareTo == 0) {
					compareTo = te1.getTimeEntryDate().compareTo(te2.getTimeEntryDate());
				}
				return compareTo;
			}

			private int compareByDateThenDescription(TimeEntry te1, TimeEntry te2) {
				int compareTo = te1.getTimeEntryDate().compareTo(te2.getTimeEntryDate());
				if (compareTo == 0) {
					compareTo = te1.getActivityDescription().compareTo(te2.getActivityDescription());
				}
				return compareTo;
			}
			
		};
		return comparator;
	}
}
