package pl.engine.shapes.flat;

import pl.engine.Triangleable;
import pl.engine.math.Vector3;
import pl.engine.render.Vertex;

import java.awt.*;

public class Rect extends Triangleable {

    public Rect(Vector3 topLeft, Vector3 bottomRight, Color color, boolean isFilled){
        super(
            getVertices(topLeft, bottomRight),
            new Integer[]{0, 2, 3, 1, 0, 3},
            color,
            isFilled
        );
    }

    public static Vertex[] getVertices(Vector3 topLeft, Vector3 bottomRight){

        return new Vertex[]{
            Vertex.of(topLeft),
            Vertex.of(bottomRight.x, topLeft.y, bottomRight.z),
            Vertex.of(topLeft.x, bottomRight.y, topLeft.z),
            Vertex.of(bottomRight.x, bottomRight.y, bottomRight.z),
        };
    }

    @Override
    public String toString() {

        return "Square {\n" + triangles[0].toString() + "\n" + triangles[1] + "\n}";
    }
}
