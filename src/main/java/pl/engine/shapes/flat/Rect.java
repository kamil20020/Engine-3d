package pl.engine.shapes.flat;

import pl.engine.Triangleable;
import pl.engine.math.Vector3;

import java.awt.*;

public class Rect extends Triangleable {

    public Rect(Vector3 topLeft, Vector3 bottomRight, Color color, boolean isFilled){
        super(
            getVertices(topLeft, bottomRight),
            new Integer[]{2, 0, 1, 2, 1, 3},
            color,
            isFilled
        );
    }

    public static Vector3[] getVertices(Vector3 topLeft, Vector3 bottomRight){

        return new Vector3[]{
            Vector3.of(topLeft),
            Vector3.of(bottomRight.x, topLeft.y, bottomRight.z),
            Vector3.of(topLeft.x, bottomRight.y, topLeft.z),
            Vector3.of(bottomRight.x, bottomRight.y, bottomRight.z),
        };
    }

    @Override
    public String toString() {

        return "Square {\n" + triangles[0].toString() + "\n" + triangles[1] + "\n}";
    }
}
