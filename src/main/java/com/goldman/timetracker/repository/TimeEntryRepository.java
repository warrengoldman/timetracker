package com.goldman.timetracker.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.goldman.timetracker.entity.TimeEntry;

public interface TimeEntryRepository extends CrudRepository<TimeEntry, Long>  {

	TimeEntry findFirstByOrderByTimeEntryDateDesc();

	TimeEntry findByTimeEntryDateAndActivityDescriptionAndHoursAndBillable(Date timeEntryDate,
			String activityDescription, BigDecimal hours, Boolean billable);

}
