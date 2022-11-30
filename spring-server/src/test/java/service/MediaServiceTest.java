package service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class MediaServiceTest {

	private MediaService mediaService = new MediaService();

	@Test
	public void addCaff() throws Exception {
		Path path = Paths.get("src/test/resources/caff_files/1.caff");
		byte[] data = Files.readAllBytes(path);
		mediaService.addCaff(data, "test1", null);
	}
}