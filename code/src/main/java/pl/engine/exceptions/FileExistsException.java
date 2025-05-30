package pl.engine.exceptions;

public class FileExistsException extends RuntimeException{

    public FileExistsException(String filePath){
        super("File with path: " + filePath + " exists");
    }
}
