package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class MediaService {

    private static final String TMP_CAFF_FOLDER_PATH = "tmp/caff";
    private static final String TMP_GIF_FOLDER_PATH = "tmp/gif";
    
    public void addCaff(byte[] content, String name, Long userId){
        Path caff_path = Paths.get(TMP_CAFF_FOLDER_PATH + "/" + name + ".caff");
        Path gif_path = Paths.get(TMP_GIF_FOLDER_PATH + "/" + name + ".gif");

        try {
            Files.write(caff_path, content);
        } catch (IOException e) {
            System.out.println("An error occurred while creating tmp caff file.");
            e.printStackTrace();
        }

        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(new String[]{"parser.exe", caff_path.toString(), gif_path.toString()});
        } catch (IOException e) {
            System.out.println("An error occurred while converting caff file.");
            e.printStackTrace();
        }

        byte[] gifContent = null;
        try {
            gifContent = Files.readAllBytes(gif_path);
        } catch (IOException e) {
            System.out.println("An error occurred while reading tmp gif file.");
            e.printStackTrace();
            return;
        }
        
        //TODO save to database
    }

    
}
