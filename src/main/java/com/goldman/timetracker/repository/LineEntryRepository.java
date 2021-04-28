package com.goldman.timetracker.repository;

import org.springframework.data.repository.CrudRepository;

import com.goldman.timetracker.entity.LineEntry;

public interface LineEntryRepository extends CrudRepository<LineEntry, Long> {

}
