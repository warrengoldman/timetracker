package com.goldman.timetracker.repository;

import org.springframework.data.repository.CrudRepository;

import com.goldman.timetracker.entity.DummyData;

public interface DummyDataRepository extends CrudRepository<DummyData, Integer>  {
	DummyData findByDescription(String description);
}
