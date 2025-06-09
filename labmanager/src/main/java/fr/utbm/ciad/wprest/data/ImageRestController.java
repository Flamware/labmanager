package fr.utbm.ciad.wprest.data;

import fr.utbm.ciad.labmanager.Constants;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin
public class ImageRestController {

    @Autowired
    private ResourceLoader resourceLoader;

    private static final String BASE_DIRECTORY = "./tmp/labmanager-tmp/Downloadables/"; // Hardcoded, use a constant

    @GetMapping(value = "/api/v" + Constants.MANAGER_MAJOR_VERSION + "/images/{folder}/{imageName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String folder, @PathVariable String imageName) {
        try {
            // Construct the file path using the base directory, folder, and imageName
            File file = Paths.get(BASE_DIRECTORY, folder, imageName).toFile();

            // Log the file path for debugging
            System.out.println("File path: " + file.getAbsolutePath());
            Resource resource = new UrlResource(file.toURI());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageData = resource.getInputStream().readAllBytes();

            MediaType contentType = MediaType.IMAGE_JPEG;
            if (imageName.toLowerCase().endsWith(".png")) {
                contentType = MediaType.IMAGE_PNG;
            } else if (imageName.toLowerCase().endsWith(".gif")) {
                contentType = MediaType.IMAGE_GIF;
            }

            return ResponseEntity.ok()
                    .contentType(contentType)
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
