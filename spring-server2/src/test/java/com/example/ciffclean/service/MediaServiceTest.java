package com.example.ciffclean.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MediaServiceTest {

	@Autowired
	private MediaService mediaService;

	@Test
	public void addCaff() throws Exception {
		Path path = Paths.get("src/test/resources/caff_files/1.caff");
		byte[] data = Files.readAllBytes(path);
		Long id = mediaService.addCaff(data, "test1", null);
		assertNotEquals(id, -1L);
	}
}