package pl.engine;

import pl.engine.math.Vector3;
import pl.engine.shapes.Drawable;
import pl.engine.shapes.flat.Triangle;

import java.awt.*;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Triangleable extends Drawable {

    protected boolean isFilled;
    protected Vector3[] vertices;
    protected Integer[] triangles;

    public Triangleable(Vector3[] vertices, Integer[] triangles, Color color, boolean isFilled){
        super(color);

        this.vertices = vertices;
        this.triangles = triangles;
        this.isFilled = isFilled;
    }

    public Vector3[] getVertices(){

        return vertices;
    }

    public Integer[] getTriangles(){

        return triangles;
    }

    public Vector3 getVertexByTriangleIndex(int triangleIndex){

        int vertexIndex = triangles[triangleIndex];

        return vertices[vertexIndex];
    }

    @Override
    public final void draw(){

        BiConsumer<Vector3[], Color> drawFunction;

        if(isFilled){
            drawFunction = Triangle::drawEdges;
        }
        else {
            drawFunction = Triangle::drawFilled;
        }

        for(int i=0; i < triangles.length - 3; i += 3){

            drawFunction.accept(
                new Vector3[]{
                    getVertexByTriangleIndex(i),
                    getVertexByTriangleIndex(i + 1),
                    getVertexByTriangleIndex(i + 2)
                },
                color
            );
        }
    }
}
