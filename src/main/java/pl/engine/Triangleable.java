package pl.engine;

import pl.engine.math.Vector3;
import pl.engine.general.QuadConsumer;
import pl.engine.general.TriConsumer;
import pl.engine.render.Vertex;
import pl.engine.shapes.flat.Triangle;
import pl.engine.texture.Texturable;
import pl.engine.texture.Texture;
import pl.engine.texture.TextureVertex;

import java.awt.*;
import java.util.Random;

public abstract class Triangleable extends Texturable {

    protected boolean isFilled;
    protected Vector3[] verticesPositions;
    protected TextureVertex[] textureVertices;
    protected Vertex[] vertices;
    public Color[] randomColors;

    public Triangleable(Vector3[] verticesPositions, TextureVertex[] textureVertices, Vertex[] vertices, Color color, Texture texture, boolean isFilled){
        super(texture, color);

        this.verticesPositions = verticesPositions;
        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.isFilled = isFilled;

        randomColors = new Color[vertices.length / 3];

        Random random = new Random();

        for(int i=0; i < randomColors.length; i++){

            randomColors[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }
    }

    public Triangleable(Vector3[] verticesPositions, TextureVertex[] textureVertices, Vertex[] vertices, Color color, boolean isFilled){
        this(verticesPositions, textureVertices, vertices, color, null, isFilled);
    }

    public Triangleable(Vector3[] verticesPositions, Vertex[] vertices, Color color, boolean isFilled){
        this(verticesPositions, new TextureVertex[0], vertices, color, null, isFilled);
    }

    public Vertex[] getVertices(){

        return vertices;
    }

    public Vector3[] getVerticesPositions(){

        return verticesPositions;
    }

    public Vector3 getVertexPositionByVertexIndex(int vertexIndex){

        int vertexPositionIndex = getVertexByIndex(vertexIndex).positionIndex;

        return verticesPositions[vertexPositionIndex];
    }

    public Vertex getVertexByIndex(int vertexIndex){

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

        for(int i=0; i <= vertices.length - 3; i += 3){

            drawTriangle(
                new Vector3[]{
                    getVertexPositionByVertexIndex(i),
                    getVertexPositionByVertexIndex(i + 1),
                    getVertexPositionByVertexIndex(i + 2)
                },
                new Vertex[]{
                    vertices[i],
                    vertices[i + 1],
                    vertices[i + 2],
                },
                color,
                pixelLevelDrawFunction
            );
        }
    }

    private void drawTexture(QuadConsumer<Double, Double, Double, Color> pixelLevelDrawFunction){

        this.basicDrawFunction = pixelLevelDrawFunction;

        for(int i=0; i <= vertices.length - 3; i += 3){

            drawTriangle(
                new Vector3[]{
                    getVertexPositionByVertexIndex(i),
                    getVertexPositionByVertexIndex(i + 1),
                    getVertexPositionByVertexIndex(i + 2)
                },
                new Vertex[]{
                    vertices[i],
                    vertices[i + 1],
                    vertices[i + 2],
                },
                color,
                pixelLevelDrawFunction
            );
        }
    }

    public void drawTriangle(Vector3[] verticesPositions, Vertex[] vertices, Color color, QuadConsumer<Double, Double, Double, Color> pixelLevelDrawFunction){

        if(isFilled){
            Triangle.drawFilled(verticesPositions, color, pixelLevelDrawFunction);
        }
        else if(texture != null){
            drawTriangleTexture(verticesPositions, vertices, color, pixelLevelDrawFunction);
        }
        else{
            Triangle.drawEdges(verticesPositions, color, pixelLevelDrawFunction);
        }
    }

    public void drawTriangleTexture(Vector3[] verticesPositions, Vertex[] vertices, Color color, QuadConsumer<Double, Double, Double, Color> pixelLevelDrawFunction){

        int minXYIndex = Triangle.getMinXYIndex(verticesPositions);
        int maxXYIndex = Triangle.getMaxXYIndex(verticesPositions);

        Vertex minXYVertex = vertices[minXYIndex];
        Vertex maxXYVertex = vertices[maxXYIndex];

        TextureVertex minXYTextureVertex = textureVertices[minXYVertex.textureVertexIndex];
        TextureVertex maxXYTextureVertex = textureVertices[maxXYVertex.textureVertexIndex];

        this.minXYU = minXYTextureVertex.u;
        this.minXYV = minXYTextureVertex.v;
        this.maxXYU = maxXYTextureVertex.u;
        this.maxXYV = maxXYTextureVertex.v;
        this.basicDrawFunction = pixelLevelDrawFunction;

        Triangle.drawFilled(verticesPositions, color, this::drawWithTexture);
    }
}
