package edu.team3.onlineshop.service;

import edu.team3.onlineshop.exceptions.FileNotFoundException;
import edu.team3.onlineshop.exceptions.FileStorageException;
import edu.team3.onlineshop.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author team 3
 *
 */

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        Path path = Paths.get(fileStorageProperties.getUploadDirectory());
        Path absPath = path.toAbsolutePath();
        Path normalizedAbsPath = absPath.normalize();
        this.fileStorageLocation = normalizedAbsPath;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, long productId, boolean isProduct) throws FileStorageException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            String newName;
            if(isProduct)
            	newName = "PRD_IMG_"+productId+"_"+ System.currentTimeMillis()+"."+extension;
            else {
            	newName = "MER_IMG_IDPF_"+productId+"."+extension;
            }
            Path targetLocation = this.fileStorageLocation.resolve(newName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return newName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) throws FileNotFoundException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

}
