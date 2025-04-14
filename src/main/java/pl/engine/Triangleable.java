package pl.engine;

import pl.engine.math.Vector3;
import pl.engine.render.QuadConsumer;
import pl.engine.render.TriConsumer;
import pl.engine.shapes.Drawable;
import pl.engine.shapes.flat.Triangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public abstract class Triangleable extends Drawable {

    protected boolean isFilled;
    protected Vector3[] vertices;
    protected Integer[] triangles;
    public Color[] randomColors;

    public Triangleable(Vector3[] vertices, Integer[] triangles, Color color, boolean isFilled){
        super(color);

        this.vertices = vertices;
        this.triangles = triangles;
        this.isFilled = isFilled;

        randomColors = new Color[triangles.length / 3];

        Random random = new Random();

        for(int i=0; i < randomColors.length; i++){

            randomColors[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }
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
    public final void draw(QuadConsumer<Double, Double, Double, Color> drawFunction){

        TriConsumer<Vector3[], Color, QuadConsumer> groupDrawFunction = getDrawFunction();

        for(int i=0; i <= triangles.length - 3; i += 3){

            groupDrawFunction.accept(
                new Vector3[]{
                    getVertexByTriangleIndex(i),
                    getVertexByTriangleIndex(i + 1),
                    getVertexByTriangleIndex(i + 2)
                },
                color,
                drawFunction
            );
        }
    }

    public TriConsumer<Vector3[], Color, QuadConsumer> getDrawFunction(){

        if(isFilled){
            return Triangle::drawFilled;
        }

        return Triangle::drawEdges;
    }
}
