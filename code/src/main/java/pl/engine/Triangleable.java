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

public abstract class Triangleable extends Texturable {

    protected boolean isFilled;
    protected Vector3[] verticesPositions;
    protected TextureVertex[] textureVertices;
    protected Vertex[] vertices;
    private double modelWidth;
    private double modelHeight;
    private double modelDepth;

    public Triangleable(Vector3[] verticesPositions, TextureVertex[] textureVertices, Vertex[] vertices, Color color, Texture texture, boolean isFilled){
        super(texture, color);

        this.verticesPositions = verticesPositions;
        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.isFilled = isFilled;

        double minX = Double.MAX_VALUE;
        double maxX = 0;
        double minY = Double.MAX_VALUE;
        double maxY = 0;
        double minZ = Double.MAX_VALUE;
        double maxZ = 0;

        for(Vector3 vertexPosition : verticesPositions){

            if(vertexPosition.x < minX){
                minX = vertexPosition.x;
            }

            if(vertexPosition.x > maxX){
                maxX = vertexPosition.x;
            }

            if(vertexPosition.y < minY){
                minY = vertexPosition.y;
            }

            if(vertexPosition.y > maxY){
                maxY = vertexPosition.y;
            }

            if(vertexPosition.z < minZ){
                minZ = vertexPosition.z;
            }

            if(vertexPosition.z > maxZ){
                maxZ = vertexPosition.z;
            }
        }

        modelWidth = maxX - minX;
        modelHeight = maxY - minY;
        modelDepth = maxZ - minZ;
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

    public Vector3 getVertexPositionByVertex(Vertex vertex){

        return verticesPositions[vertex.positionIndex];
    }

    public Vector3 getVertexPositionByVertexIndex(int vertexIndex){

        int vertexPositionIndex = vertices[vertexIndex].positionIndex;

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

        minXYU = Double.MAX_VALUE;
        minXYV = Double.MAX_VALUE;
        maxXYU = 0;
        maxXYV = 0;
        double minTriangleX = Double.MAX_VALUE;
        double minTriangleY = Double.MAX_VALUE;
        double maxTriangleX = 0;
        double maxTriangleY = 0;

        for(int i=0; i < 3; i++){

            int textureVertexIndex = vertices[i].textureVertexIndex;

            TextureVertex textureVertex = textureVertices[textureVertexIndex];

            if(textureVertex.u < minXYU){
                minXYU = textureVertex.u;
            }
            else if(textureVertex.u > maxXYU){
                maxXYU = textureVertex.u;
            }

            if(textureVertex.v < minXYV){
                minXYV = textureVertex.v;
            }
            else if(textureVertex.v > maxXYV){
                maxXYV = textureVertex.v;
            }

            Vector3 vertexPosition = verticesPositions[i];

            if(vertexPosition.x < minTriangleX){
                minTriangleX = vertexPosition.x;
            }
            else if(vertexPosition.x > maxTriangleX){
                maxTriangleX = vertexPosition.x;
            }

            if(vertexPosition.y < minTriangleY){
                minTriangleY = vertexPosition.y;
            }
            else if(vertexPosition.y > maxTriangleY){
                maxTriangleY = vertexPosition.y;
            }
        }

        triangleWidth = maxTriangleX - minTriangleX;
        triangleHeight = maxTriangleY - minTriangleY;

        this.basicDrawFunction = pixelLevelDrawFunction;

        Triangle.drawFilled(verticesPositions, color, this::drawWithTexture);
        Triangle.drawEdges(verticesPositions, Color.black, pixelLevelDrawFunction);
    }
}
