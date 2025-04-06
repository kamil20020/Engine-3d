package pl.engine.render;

import pl.engine.Triangleable;
import pl.engine.math.Vector3;
import pl.engine.shapes.Drawable;
import pl.engine.shapes.Mesh;
import pl.engine.shapes.flat.*;
import pl.engine.shapes.spatial.Cube;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Renderer {

    private final List<Triangleable> triangeables = new ArrayList<>();
    private final List<Drawable> drawables = new ArrayList<>();
    private final Camera camera;

    public Renderer(Camera camera){

        this.camera = camera;

        init();
    }

    private void init(){

        Rect rect = new Rect(Vector3.of(500, 500, 10), Vector3.of(600, 600, 10), Color.orange, true);
        Triangle triangle = new Triangle(
            Vector3.of(600, 500, 10),
            Vector3.of(700, 500, 10),
            Vector3.of(700, 700, 10),
            Color.pink,
            true
        );

        Triangle triangle1 = new Triangle(
            Vector3.of(600, 600, 10),
            Vector3.of(700, 600, 10),
            Vector3.of(600, 600, 100),
            Color.pink,
            true
        );

        Disk disk = new Disk(
            Vector3.of(600, 600, 10),
            100,
            Color.green
        );

        Circle circle = new Circle(
            Vector3.of(700, 700, 10),
            100,
            Color.pink
        );

        Line line = new Line(
            Vector3.of(100, 100, 10),
            Vector3.of(500, 500, 20),
            Color.green
        );

        Line line1 = new Line(
            Vector3.of(100, 100, 10),
            Vector3.of(100, 100, 100),
            Color.green
        );

        Cube cube = new Cube(Vector3.of(0, 600, 10), 100, Color.green, true);
        Cube cube1 = new Cube(Vector3.of(600, 0, 10), 100, Color.orange, true);
        Cube cube2 = new Cube(Vector3.of(800, 800, 10), 100, Color.magenta, true);
        Cube cube3 = new Cube(Vector3.of(0, 0, 10), 100, Color.pink, true);
        Cube cube4 = new Cube(Vector3.of(0, 0, 10), 1000, Color.gray, true);

        Mesh ship = Mesh.loadFromObjFile("./meshes/space-ship.obj", Color.orange, false, 0);
        Mesh teapot = Mesh.loadFromObjFile("./meshes/teapot.obj", Color.orange, false, 0);
        Mesh axis = Mesh.loadFromObjFile("./meshes/axis.obj", Color.orange, false, 0);
        Mesh mountains = Mesh.loadFromObjFile("./meshes/mountains.obj", Color.orange, true, 200);

        triangeables.addAll(List.of(mountains));

//        drawables.addAll(List.of(rect));
    }

    private Vector3 getVertexAndTransform(Triangleable toDraw, int triangleIndex){

       Vector3 vertex = toDraw.getVertexByTriangleIndex(triangleIndex);

        return camera.transform(vertex);
    }

    public void draw(){

        Vector3[] toDrawVertices = new Vector3[]{
            Vector3.of(),
            Vector3.of(),
            Vector3.of()
        };

        triangeables.forEach(toDraw -> {

            BiConsumer<Vector3[], Color> drawFunction = toDraw.getDrawFunction();

            for(int i=0; i <= toDraw.getTriangles().length - 3; i += 3){

                for(int j=0; j < 3; j++){
                    toDrawVertices[j] = getVertexAndTransform(toDraw, i + j);
                }

                drawFunction.accept(
                    toDrawVertices,
                    toDraw.getColor()
                );
            }
        });

        drawables.forEach(toDraw -> {
            toDraw.draw();
        });
    }
}
