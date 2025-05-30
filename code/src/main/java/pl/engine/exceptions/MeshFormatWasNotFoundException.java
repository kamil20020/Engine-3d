package pl.engine.exceptions;

public class MeshFormatWasNotFoundException extends RuntimeException{

    public MeshFormatWasNotFoundException(String path, boolean isLoader){
        super("Mesh " + (isLoader ? "loader" : "writer") + " was not found for path: " + path);
    }
}
