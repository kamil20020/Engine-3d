package pl.engine.shapes;

import pl.engine.math.Vector3;
import pl.engine.shapes.flat.Triangle;
import pl.engine.texture.Texturable;
import pl.engine.texture.Texture;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mesh extends Texturable {

    private Triangle[] triangles;
    private boolean isFilled = false;

    public Mesh(Triangle[] triangles, Color color, boolean isFilled){
        this(triangles);

        this.color = color;
        this.isFilled = isFilled;
    }

    public Mesh(Triangle[] triangles, Color color){
        this(triangles);

        this.color = color;
    }

    private Mesh(Triangle[] triangles, Texture texture){
        this(triangles);

        this.texture = texture;
    }

    protected Mesh(Triangle[] triangles){

        this.triangles = triangles;
    }

    public List<Vector3> map(UnaryOperator<Vector3> mapFunction){

        return getVerticesStream()
            .map(vertex -> mapFunction.apply(vertex))
            .collect(Collectors.toList());
    }

    private Stream<Vector3> getVerticesStream(){

        return Arrays.stream(triangles)
                .flatMap(triangle -> Arrays.stream(triangle.getVertices()));
    }

    public Triangle[] getTriangles(){

        return triangles;
    }

    @Override
    public void draw() {

        for(Triangle triangle : triangles){

            triangle.draw();
        }
    }

    @Override
    protected Vector3 getMinXY() {
        return null;
    }
}
