package pl.engine.shapes.spatial.store;

public enum MeshFormatType {

    OBJ("obj");

    private String extension;

    MeshFormatType(String extension){
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
