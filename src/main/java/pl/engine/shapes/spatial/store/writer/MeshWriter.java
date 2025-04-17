package pl.engine.shapes.spatial.store.writer;

import pl.engine.shapes.spatial.Mesh;

import java.io.IOException;

public interface MeshWriter {

    void write(String path, Mesh mesh) throws IOException;
}
