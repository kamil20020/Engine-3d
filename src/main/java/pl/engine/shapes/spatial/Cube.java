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
                0, 2, 3, 3, 1, 0, //top
                7, 6, 4, 7, 5, 4, //bottom
                4, 0, 1, 4, 1, 5, //front
                3, 2, 6, 3, 7, 6, //back
                6, 2, 0, 0, 4, 6, //left
                7, 5, 1, 1, 3, 7 //right
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
