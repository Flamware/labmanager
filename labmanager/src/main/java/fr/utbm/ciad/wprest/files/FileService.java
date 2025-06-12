package fr.utbm.ciad.wprest.files;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final Path basePath;

    public FileService() {
        // Récupère le chemin absolu du dossier racine du projet
        this.basePath = Paths.get(System.getProperty("user.dir"), "tmp", "labmanager-tmp").normalize();
    }

    public Resource loadAsResource(String relativePath) {
        try {
            Path fullPath = basePath.resolve(relativePath).normalize();
            Resource resource = new UrlResource(fullPath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                System.err.println("Resource not found or not readable: " + fullPath);
                return null;
            }
        } catch (MalformedURLException e) {
            System.err.println("Malformed path: " + relativePath);
            return null;
        }
    }
}
