package edu.team3.onlineshop.exceptions;

/**
 * @author team 3
 *
 */

public class FileStorageException extends RuntimeException{

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
