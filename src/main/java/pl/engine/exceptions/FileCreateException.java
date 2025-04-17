package pl.engine.exceptions;

public class FileCreateException extends RuntimeException{

    public FileCreateException(String filePath){
        super("Could not create file from from path: " + filePath);
    }
}
