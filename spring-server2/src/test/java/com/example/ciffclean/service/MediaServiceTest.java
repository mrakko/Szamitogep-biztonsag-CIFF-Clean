package com.example.ciffclean.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
import com.example.ciffclean.repositories.UserRepository;

import com.example.ciffclean.domain.AppUser;
import com.example.ciffclean.domain.GifFile;
import com.example.ciffclean.models.CreateCommentDTO;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class MediaServiceTest {

	@Autowired
	private MediaService mediaService;

	@Autowired
	private GifFileRepository gifFileRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void addCaffTest() throws Exception {
		addCaff1();
		
		List<GifFile> result = gifFileRepository.findByNameContains("test");
		assertEquals(1, result.size());
	}

	@Test
	public void modifyCaffTest() throws Exception {
		var id = addCaff2ReturnData().a;
		
		List<GifFile> result = gifFileRepository.findByNameContains("test");
		assertEquals(1, result.size());

		mediaService.editFileName(id, "new_name");

		result = gifFileRepository.findByNameContains("test");
		assertEquals(0, result.size());
		result = gifFileRepository.findByNameContains("new");
		assertEquals(1, result.size());
	}

	@Test
	public void findFilesTest() throws Exception {
		addCaff1();
		List<GifFile> result = mediaService.findFiles("test");
		assertEquals(1, result.size());

		addCaff2ReturnData();

		result = mediaService.findFiles("test");
		assertEquals(2, result.size());
		result = mediaService.findFiles("asd");
		assertEquals(0, result.size());
		
	}

	@Test
	public void downloadFileTest() throws Exception{
		addCaff1();
		var caff2 = addCaff2ReturnData();
		var id = caff2.a;
		var data = caff2.b;
		var res = mediaService.downloadFile(id);
		assertArrayEquals(data, res);
		assertNotEquals(res.length, 0);
	}

	@Test
	public void commentFileTest() throws Exception{
		var id = addCaff2ReturnData().a;
		CreateCommentDTO newComment = new CreateCommentDTO();
		newComment.setFileId(id);
		newComment.setText("This is my new comment");

		var userId = saveUser();
		var commentId = mediaService.commentFile(newComment, userId);

		var res = gifFileRepository.findByIdWithComments(id);
		var actual = res.get().getComments().get(0);
		assertEquals(commentId, actual.getId());
	}

	@Test
	public void deleteFileTest() throws Exception{
		addCaff1();
		var id = addCaff2ReturnData().a;
		mediaService.deleteFile(id);
		var result = mediaService.findFiles("");
		assertEquals(1, result.size());
	}

	@Test
	public void getAllFilesTest() throws Exception{
		addCaff1();
		addCaff2ReturnData();
		var result = mediaService.findFiles("");
		assertEquals(2, result.size());
	}

	private void addCaff1() throws Exception{
		Path path = Paths.get("src/test/resources/caff_files/1.caff");
		byte[] data = Files.readAllBytes(path);
		Long id = mediaService.addCaff(data, "test1", null);
		assertNotEquals(id, -1L);
	}

	private org.antlr.v4.runtime.misc.Pair<Long, byte[]> addCaff2ReturnData() throws Exception{
		Path path = Paths.get("src/test/resources/caff_files/2.caff");
		byte[] data = Files.readAllBytes(path);
		Long id = mediaService.addCaff(data, "test2", null);
		assertNotEquals(id, -1L);
		var res = new org.antlr.v4.runtime.misc.Pair<Long, byte[]>(id, data);
		return res;
	}
	
	private Long saveUser() {
		AppUser newUser = new AppUser();
		newUser.setFullName("Name");
		userRepository.save(newUser);
		return newUser.getId();
	}
}