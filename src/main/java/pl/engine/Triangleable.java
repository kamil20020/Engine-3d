package pl.engine;

import pl.engine.general.PentaConsumer;
import pl.engine.math.Vector3;
import pl.engine.general.QuadConsumer;
import pl.engine.general.TriConsumer;
import pl.engine.render.Vertex;
import pl.engine.shapes.Drawable;
import pl.engine.shapes.flat.Triangle;
import pl.engine.texture.Texturable;
import pl.engine.texture.Texture;

import java.awt.*;
import java.util.Random;

public abstract class Triangleable extends Texturable {

    protected boolean isFilled;
    protected Vertex[] vertices;
    protected Integer[] triangles;
    public Color[] randomColors;

    public Triangleable(Vertex[] vertices, Integer[] triangles, Color color, Texture texture, boolean isFilled){
        super(texture, color);

        this.vertices = vertices;
        this.triangles = triangles;
        this.isFilled = isFilled;

        randomColors = new Color[triangles.length / 3];

        Random random = new Random();

        for(int i=0; i < randomColors.length; i++){

            randomColors[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }
    }

    public Triangleable(Vertex[] vertices, Integer[] triangles, Color color, boolean isFilled){
        this(vertices, triangles, color, null, isFilled);
    }

    public Vector3[] getVertices(){

        return vertices;
    }

    public Integer[] getTriangles(){

        return triangles;
    }

    public Vertex getVertexByTriangleIndex(int triangleIndex){

        int vertexIndex = triangles[triangleIndex];

        return vertices[vertexIndex];
    }

    @Override
    public final void draw(QuadConsumer<Double, Double, Double, Color> pixelLevelDrawFunction){

        if(texture == null){
            drawRaw(pixelLevelDrawFunction);
        }
        else{
            drawTexture(pixelLevelDrawFunction);
        }
    }

    private void drawRaw(QuadConsumer<Double, Double, Double, Color> pixelLevelDrawFunction){

        TriConsumer<Vertex[], Color, QuadConsumer<Double, Double, Double, Color>> triangleDrawFunction = getTriangleDrawFunction();

        for(int i=0; i <= triangles.length - 3; i += 3){

            triangleDrawFunction.accept(
                new Vertex[]{
                    getVertexByTriangleIndex(i),
                    getVertexByTriangleIndex(i + 1),
                    getVertexByTriangleIndex(i + 2)
                },
                color,
                pixelLevelDrawFunction
            );
        }
    }

    private void drawTexture(QuadConsumer<Double, Double, Double, Color> pixelLevelDrawFunction){

        TriConsumer<Vertex[], Color, QuadConsumer<Double, Double, Double, Color>> triangleDrawFunction = getTriangleDrawFunction();
        this.basicDrawFunction = pixelLevelDrawFunction;

        for(int i=0; i <= triangles.length - 3; i += 3){

            triangleDrawFunction.accept(
                new Vertex[]{
                    getVertexByTriangleIndex(i),
                    getVertexByTriangleIndex(i + 1),
                    getVertexByTriangleIndex(i + 2)
                },
                color,
                pixelLevelDrawFunction
            );
        }
    }

    public void drawTriangleTexture(Vertex[] vertices, Color color, QuadConsumer<Double, Double, Double, Color> pixelLevelDrawFunction){

        Vertex minXY = (Vertex) Triangle.getMinXY(vertices);
        Vertex maxXY = (Vertex) Triangle.getMaxXY(vertices);

        this.minXYU = minXY.textureVertex.u;
        this.minXYV = minXY.textureVertex.v;
        this.maxXYU = maxXY.textureVertex.u;
        this.maxXYV = maxXY.textureVertex.v;
        this.basicDrawFunction = pixelLevelDrawFunction;

        Triangle.drawFilled(vertices, color, this::drawWithTexture);
    }

    public TriConsumer<Vertex[], Color, QuadConsumer<Double, Double, Double, Color>> getTriangleDrawFunction(){

        if(isFilled){
            return Triangle::drawFilled;
        }
        else if(texture != null){
            return this::drawTriangleTexture;
        }

        return Triangle::drawEdges;
    }
}
