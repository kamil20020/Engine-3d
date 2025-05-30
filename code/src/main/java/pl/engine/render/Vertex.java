package pl.engine.render;

import pl.engine.math.Vector3;
import pl.engine.texture.TextureVertex;

public class Vertex {

    public int positionIndex;
    public int textureVertexIndex;

    public Vertex(int positionIndex, int textureVertexIndex) {

        this(positionIndex);

        this.textureVertexIndex = textureVertexIndex;
    }

    public Vertex(int positionIndex) {

        this.positionIndex = positionIndex;
    }

    public Vertex(){


    }

    public static Vertex of(int positionIndex, int textureVertexIndex) {

        return new Vertex(positionIndex, textureVertexIndex);
    }

    public static Vertex of(int positionIndex) {

        return new Vertex(positionIndex);
    }

    public static Vertex empty() {

        return new Vertex();
    }
}