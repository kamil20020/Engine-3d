package pl.engine.shapes.spatial;

import pl.engine.math.Vector3;
import pl.engine.shapes.Mesh;
import pl.engine.shapes.flat.Rect;
import pl.engine.texture.Texture;

import java.awt.*;

public class Cube extends Mesh {

    public Cube(Vector3 leftTopFrontCorner, int a, Color color, boolean isFilled){

        super(
            getVertices(leftTopFrontCorner, a),
            new Integer[]{0, 1, 2, 0, 3, 2},
            color,
            isFilled
        );
    }

    private static Vector3[] getVertices(Vector3 leftTopFrontCorner, int a){

        Vector3[] top = Rect.getVertices(
            Vector3.of(leftTopFrontCorner.x, leftTopFrontCorner.y, leftTopFrontCorner.z - a),
            Vector3.of(leftTopFrontCorner.x + a, leftTopFrontCorner.y, leftTopFrontCorner.z)
        );

        Vector3[] bottom = Rect.getVertices(
            Vector3.of(leftTopFrontCorner.x, leftTopFrontCorner.y + a, leftTopFrontCorner.z - a),
            Vector3.of(leftTopFrontCorner.x + a, leftTopFrontCorner.y + a, leftTopFrontCorner.z)
        );

        Vector3[] result = new Vector3[8];

        System.arraycopy(top, 0, result, 0, 4);
        System.arraycopy(bottom, 0, result, 4, 4);

        return result;
    }
}
