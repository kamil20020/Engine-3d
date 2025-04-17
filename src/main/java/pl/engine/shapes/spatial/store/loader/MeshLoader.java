package pl.engine.shapes.spatial.store.loader;

import pl.engine.shapes.spatial.Mesh;

import java.awt.*;

public interface MeshLoader {

    Mesh load(String path, Color color, boolean isFilled, int offset);
}
