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
            new Integer[]{
                0, 1, 2, 2, 3, 1, //top
                4, 5, 6, 6, 7, 5, //bottom
                0, 1, 4, 1, 4, 5, //front
                2, 3, 6, 6, 7, 3, //back
                6, 2, 4, 4, 0, 2, //left
                3, 1, 5, 3, 5, 7 //right
            },
            color,
            isFilled
        );
    }

    private static Vector3[] getVertices(Vector3 leftTopFrontCorner, int a){

        return new Vector3[]{
            Vector3.of(leftTopFrontCorner),
            Vector3.of(leftTopFrontCorner.x + a, leftTopFrontCorner.y, leftTopFrontCorner.z),
            Vector3.of(leftTopFrontCorner.x, leftTopFrontCorner.y, leftTopFrontCorner.z + a),
            Vector3.of(leftTopFrontCorner.x + a, leftTopFrontCorner.y, leftTopFrontCorner.z + a),
            Vector3.of(leftTopFrontCorner.x, leftTopFrontCorner.y + a, leftTopFrontCorner.z),
            Vector3.of(leftTopFrontCorner.x + a, leftTopFrontCorner.y + a, leftTopFrontCorner.z),
            Vector3.of(leftTopFrontCorner.x, leftTopFrontCorner.y + a, leftTopFrontCorner.z + a),
            Vector3.of(leftTopFrontCorner.x + a, leftTopFrontCorner.y + a, leftTopFrontCorner.z + a),
        };
    }
}
