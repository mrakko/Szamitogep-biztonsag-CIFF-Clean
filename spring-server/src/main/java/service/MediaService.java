package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MediaService {

    private static final String TMP_CAFF_FOLDER_PATH = "tmp/caff";
    private static final String TMP_GIF_FOLDER_PATH = "tmp/gif";
    
    public void addCaff(byte[] content, String name, Long userId){
        String uuid = UUID.randomUUID().toString();
        Path caff_path = Paths.get(TMP_CAFF_FOLDER_PATH + "/" + uuid + ".caff");
        Path gif_path = Paths.get(TMP_GIF_FOLDER_PATH + "/" + uuid + ".gif");
        
        try {
            Files.createDirectories(caff_path.getParent());
            Files.createFile(caff_path);
            Files.createDirectories(gif_path.getParent());
            Files.createFile(gif_path);
        } catch (IOException e) {
            System.out.println("An error occurred while creating tmp folders.");
            e.printStackTrace();
        }

        try {
            Files.write(caff_path, content);
        } catch (IOException e) {
            System.out.println("An error occurred while creating tmp caff file.");
            e.printStackTrace();
            return;
        }

        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(new String[]{"parser.exe", caff_path.toString(), gif_path.toString()});
        } catch (IOException e) {
            System.out.println("An error occurred while converting caff file.");
            e.printStackTrace();
            return;
        }

        byte[] gifContent = null;
        try {
            gifContent = Files.readAllBytes(gif_path);
        } catch (IOException e) {
            System.out.println("An error occurred while reading tmp gif file.");
            e.printStackTrace();
            return;
        }
        
        try {
            Files.delete(caff_path);
            Files.delete(gif_path);
        } catch (IOException e) {
            System.out.println("An error occurred while deleting tmp files.");
            e.printStackTrace();
        }

        //TODO save to database
    }

    
}
