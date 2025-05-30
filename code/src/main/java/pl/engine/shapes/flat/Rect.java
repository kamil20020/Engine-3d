package pl.engine.shapes.flat;

import pl.engine.Triangleable;
import pl.engine.math.Vector3;
import pl.engine.render.Vertex;

import java.awt.*;

public class Rect extends Triangleable {

    public Rect(Vector3 topLeft, Vector3 bottomRight, Color color, boolean isFilled){
        super(
            getVerticesPositions(topLeft, bottomRight),
            new Vertex[]{
                Vertex.of(0),
                Vertex.of(2),
                Vertex.of(3),
                Vertex.of(1),
                Vertex.of(0),
                Vertex.of(3),
            },
            color,
            isFilled
        );
    }

    public static Vector3[] getVerticesPositions(Vector3 topLeft, Vector3 bottomRight){

        return new Vector3[]{
            Vector3.of(topLeft),
            Vector3.of(bottomRight.x, topLeft.y, bottomRight.z),
            Vector3.of(topLeft.x, bottomRight.y, topLeft.z),
            Vector3.of(bottomRight.x, bottomRight.y, bottomRight.z),
        };
    }

    @Override
    public String toString() {

        return "Rect {\n" + verticesPositions[0].toString() + "\n" + verticesPositions[1] + "\n}";
    }
}
