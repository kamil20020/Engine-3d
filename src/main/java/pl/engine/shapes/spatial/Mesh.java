package pl.engine.shapes.spatial;

import pl.engine.Triangleable;
import pl.engine.math.Vector3;
import pl.engine.render.Vertex;
import pl.engine.texture.Texture;
import pl.engine.texture.TextureVertex;

import java.awt.*;

public class Mesh extends Triangleable{

    public Mesh(Vector3[] verticesPositions, TextureVertex[] textureVertices, Vertex[] vertices, Color color, boolean isFilled) {
        super(verticesPositions, textureVertices, vertices, color, null, isFilled);
    }

    public Mesh(Vector3[] verticesPositions, Vertex[] vertices, Color color, boolean isFilled) {
        super(verticesPositions, vertices, color, isFilled);
    }
}
