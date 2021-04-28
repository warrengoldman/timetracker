package com.goldman.timetracker.repository;

import org.springframework.data.repository.CrudRepository;

import com.goldman.timetracker.entity.TimeEntry;

public interface TimeEntryRepository extends CrudRepository<TimeEntry, Long>  {

	TimeEntry findFirstByOrderByTimeEntryDateDesc();

}
