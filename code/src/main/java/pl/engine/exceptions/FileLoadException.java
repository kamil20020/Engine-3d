package pl.engine.exceptions;

public class FileLoadException extends RuntimeException{

    public FileLoadException(String filePath) {
        super("Could not load from file from path: " + filePath);
    }

    public FileLoadException(String filePath, String cause) {
        super("Could not load from from path: " + filePath + " because of " + cause);
    }
}
