package com.goldman.timetracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldman.timetracker.entity.BatchEntry;
import com.goldman.timetracker.repository.BatchEntryRepository;

@Service
public class BatchService {

	@Autowired
	private BatchEntryRepository batchEntryRepository;

	public BatchEntry save(BatchEntry batchEntry) {
		return batchEntryRepository.save(batchEntry);
	}

	public BatchEntry findFirstByOrderByBatchDateDesc() {
		return batchEntryRepository.findFirstByOrderByBatchDateDesc();
	}
}
