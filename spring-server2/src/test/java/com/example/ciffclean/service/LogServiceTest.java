package com.example.ciffclean.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.example.ciffclean.repositories.LogRepository;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class LogServiceTest {
    @Autowired
	private LogService logService;

    @Autowired
	private LogRepository logRepository;
    
	@Test
	public void logErrorTest() throws Exception {
		var id = logService.logError(null, "TEST ERROR HAPPENED", "TEST");
        var log = logRepository.findById(id).get();
		assertEquals(log.getActivity(), "TEST");
		assertEquals(log.getDetails(), "TEST ERROR HAPPENED");
		assertNull(log.getUserId());
        assertFalse(log.isSucceeded());
        assertNull(log.getRelatedFileId());
	}

    @Test
	public void logActivityTest() throws Exception {
		var id = logService.logActivity(null, "TEST ACTIVITY", null);
        var log = logRepository.findById(id).get();
		assertEquals(log.getActivity(), "TEST ACTIVITY");
		assertEquals(log.getDetails(), "");
		assertNull(log.getUserId());
        assertTrue(log.isSucceeded());
        assertNull(log.getRelatedFileId());
	}
    
}
