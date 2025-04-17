package pl.engine.shapes.spatial;

import pl.engine.math.Vector3;
import pl.engine.render.Vertex;

import java.awt.*;

public class Cube extends Mesh {

    public Cube(Vector3 leftTopFrontCorner, int a, Color color, boolean isFilled){

        super(
            getVertices(leftTopFrontCorner, a),
            new Integer[]{
                2, 0, 3, 0, 1, 3, //top/
                6, 5, 4, 6, 7, 5, //bottom/
                4, 1, 0, 1, 4, 5, //front/
                3, 6, 2, 7, 6, 3, //back/
                6, 4, 2, 2, 4, 0, //left/
                1, 7, 3, 1, 5, 7 //right/
            },
            color,
            isFilled
        );
    }

    private static Vertex[] getVertices(Vector3 leftTopFrontCorner, int a){

        return new Vertex[]{
            Vertex.of(leftTopFrontCorner),
            Vertex.of(leftTopFrontCorner.x + a, leftTopFrontCorner.y, leftTopFrontCorner.z),
            Vertex.of(leftTopFrontCorner.x, leftTopFrontCorner.y, leftTopFrontCorner.z + a),
            Vertex.of(leftTopFrontCorner.x + a, leftTopFrontCorner.y, leftTopFrontCorner.z + a),
            Vertex.of(leftTopFrontCorner.x, leftTopFrontCorner.y + a, leftTopFrontCorner.z),
            Vertex.of(leftTopFrontCorner.x + a, leftTopFrontCorner.y + a, leftTopFrontCorner.z),
            Vertex.of(leftTopFrontCorner.x, leftTopFrontCorner.y + a, leftTopFrontCorner.z + a),
            Vertex.of(leftTopFrontCorner.x + a, leftTopFrontCorner.y + a, leftTopFrontCorner.z + a),
        };
    }
}
