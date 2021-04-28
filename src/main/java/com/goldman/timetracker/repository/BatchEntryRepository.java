package com.goldman.timetracker.repository;

import org.springframework.data.repository.CrudRepository;

import com.goldman.timetracker.entity.BatchEntry;

public interface BatchEntryRepository extends CrudRepository<BatchEntry, Long> {

	BatchEntry findFirstByOrderByBatchDateDesc();

}
