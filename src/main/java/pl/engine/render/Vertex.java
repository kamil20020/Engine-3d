package pl.engine.render;

import pl.engine.math.Vector3;
import pl.engine.texture.TextureVertex;

public class Vertex extends Vector3 {

    public TextureVertex textureVertex;

    public Vertex(double x, double y, double z, double u, double v) {
        super(x, y, z);

        textureVertex = new TextureVertex(u, v);
    }

    public Vertex(Vector3 vertexPosition, TextureVertex textureVertex) {
        super(vertexPosition.x, vertexPosition.y, vertexPosition.z);

        this.textureVertex = TextureVertex.of(textureVertex);
    }

    public Vertex(double x, double y, double z) {
        super(x, y, z);
    }

    public static Vertex of(double x, double y, double z){

        return new Vertex(x, y, z);
    }

    public static Vertex of(Vector3 v){

        return new Vertex(v.x, v.y, v.z);
    }

    public static Vertex empty(){

        return new Vertex(0, 0, 0);
    }

    public void setPosition(Vector3 position){

        x = position.x;
        y = position.y;
        z = position.z;
    }
}
