package com.example.ciffclean.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.example.ciffclean.repositories.GifFileRepository;
import com.example.ciffclean.domain.GifFile;


@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class MediaServiceTest {

	@Autowired
	private MediaService mediaService;

	@Autowired
	private GifFileRepository gifFileRepository;

	@Test
	public void addCaff() throws Exception {
		Path path = Paths.get("src/test/resources/caff_files/1.caff");
		byte[] data = Files.readAllBytes(path);
		Long id = mediaService.addCaff(data, "test1", null);
		assertNotEquals(id, -1L);
		
		List<GifFile> result = gifFileRepository.findByNameContains("test");
		assertEquals(1, result.size());
	}

	@Test
	public void modifyCaff() throws Exception {
		Path path = Paths.get("src/test/resources/caff_files/2.caff");
		byte[] data = Files.readAllBytes(path);
		Long id = mediaService.addCaff(data, "test2", null);
		assertNotEquals(id, -1L);
		
		List<GifFile> result = gifFileRepository.findByNameContains("test");
		assertEquals(1, result.size());

		mediaService.editFileName(id, "new_name");

		result = gifFileRepository.findByNameContains("test");
		assertEquals(0, result.size());
		result = gifFileRepository.findByNameContains("new");
		assertEquals(1, result.size());
	}

	@Test
	public void findFiles() throws Exception {
		Path path = Paths.get("src/test/resources/caff_files/1.caff");
		byte[] data = Files.readAllBytes(path);
		Long id = mediaService.addCaff(data, "test1", null);
		assertNotEquals(id, -1L);

		List<GifFile> result = mediaService.findFiles("test");
		assertEquals(1, result.size());

		path = Paths.get("src/test/resources/caff_files/2.caff");
		data = Files.readAllBytes(path);
		id = mediaService.addCaff(data, "test2", null);
		assertNotEquals(id, -1L);

		result = mediaService.findFiles("test");
		assertEquals(2, result.size());
		result = mediaService.findFiles("asd");
		assertEquals(0, result.size());
		
	}
}