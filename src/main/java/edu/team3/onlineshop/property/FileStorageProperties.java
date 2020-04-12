package edu.team3.onlineshop.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author team 3
 *
 */

@Component
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

    private String uploadDirectory;// = "/api/v1/products/productimages/";

    public String getUploadDirectory() {
        return uploadDirectory;
    }

    public void setUploadDirectory(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

}
