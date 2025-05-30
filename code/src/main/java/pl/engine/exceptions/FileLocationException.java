package pl.engine.exceptions;

public class FileLocationException extends RuntimeException{

    public FileLocationException(String filePath) {
        super("Could not find file from path: " + filePath);
    }

    public FileLocationException(String filePath, String cause) {
        super("Could not find file from path: " + filePath + " because of " + cause);
    }
}
