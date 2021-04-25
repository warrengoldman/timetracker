package com.goldman.timetracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goldman.timetracker.entity.DummyData;
import com.goldman.timetracker.repository.DummyDataRepository;

@SpringBootTest
class DummyDataRepositoryTest {
	private static final String TEST_DESCRIPTION = "hello";
	@Autowired
    private DummyDataRepository testRepository;
	@Test
	void checkAutowire() {
		assertNotNull(this.testRepository);
	}

	@Test
	void findByDescription() throws Exception {
		DummyData aTest = this.testRepository.findByDescription(TEST_DESCRIPTION);
		if (aTest == null) {
			DummyData newDummyData = new DummyData();
			newDummyData.setDescription(TEST_DESCRIPTION);
			this.testRepository.save(newDummyData);
			aTest = this.testRepository.findByDescription(TEST_DESCRIPTION);
		}
    	assertNotNull(aTest);
    	assertNotNull(aTest.getDummyDataSk());
    	assertEquals(TEST_DESCRIPTION, aTest.getDescription());
	}
}