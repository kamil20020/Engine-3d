package pl.engine.exceptions;

public class FileWriteException extends RuntimeException{

    public FileWriteException(String filePath){
        super("Could not write to file from path: " + filePath);
    }

    public FileWriteException(String filePath, String cause){
        super("Could not write to file from path: " + filePath + " because of " + cause);
    }
}
