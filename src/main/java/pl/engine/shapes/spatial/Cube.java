package pl.engine.shapes.spatial;

import pl.engine.math.Vector3;
import pl.engine.render.Vertex;

import java.awt.*;

public class Cube extends Mesh {

    public Cube(Vector3 leftTopFrontCorner, int a, Color color, boolean isFilled){

        super(
            getVerticesPosition(leftTopFrontCorner, a),
            new Vertex[]{
                Vertex.of(2), Vertex.of(0), Vertex.of(3), Vertex.of(0), Vertex.of(1), Vertex.of(3), //top/
                Vertex.of(6), Vertex.of(5), Vertex.of(4), Vertex.of(6), Vertex.of(7), Vertex.of(5), //bottom/
                Vertex.of(4), Vertex.of(1), Vertex.of(0), Vertex.of(1), Vertex.of(4), Vertex.of(5), //front/
                Vertex.of(3), Vertex.of(6), Vertex.of(2), Vertex.of(7), Vertex.of(6), Vertex.of(3), //back/
                Vertex.of(6), Vertex.of(4), Vertex.of(2), Vertex.of(2), Vertex.of(4), Vertex.of(0), //left/
                Vertex.of(1), Vertex.of(7), Vertex.of(3), Vertex.of(1),Vertex.of( 5), Vertex.of(7) //right/
            },
            color,
            isFilled
        );
    }

    private static Vector3[] getVerticesPosition(Vector3 leftTopFrontCorner, int a){

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
