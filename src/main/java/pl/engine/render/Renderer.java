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
    private final Zbuffer zBuffer;
    private final QuadConsumer<Double, Double, Double, Color> drawFunction;
    private final Screen screen;

    public Renderer(Camera camera){

        this.camera = camera;
        this.screen = Screen.getInstance();
        this.zBuffer = Zbuffer.getInstance();
        this.drawFunction = (Double x, Double y, Double z, Color color) -> {

//            System.out.println(z < 0.1);

//                screen.draw(x, y, color);

            if(zBuffer.update(x.intValue(), y.intValue(), z.intValue())){
                 screen.draw(x, y, color);
            }
        };

        init();
    }

    private void init(){

        Rect rect = new Rect(Vector3.of(200, 200, 5), Vector3.of(300, 300, 5), Color.orange, false);
        Rect rect1 = new Rect(Vector3.of(200, 200, 10), Vector3.of(300, 300, 10), Color.orange, false);
        Rect rect2 = new Rect(Vector3.of(200, 200, 15), Vector3.of(300, 300, 15), Color.orange, false);
        Rect rect3 = new Rect(Vector3.of(200, 200, 20), Vector3.of(300, 300, 20), Color.orange, false);
        Rect rect4 = new Rect(Vector3.of(200, 200, 25), Vector3.of(300, 300, 25), Color.orange, false);
        Rect rect5 = new Rect(Vector3.of(200, 200, 30), Vector3.of(300, 300, 30), Color.orange, false);
        Rect rect6 = new Rect(Vector3.of(200, 200, 50), Vector3.of(300, 300, 50), Color.orange, false);
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

        Rect floor = new Rect(Vector3.of(-1000, -1000, -1000), Vector3.of(1000, 10, 1000), Color.green, true);

        Cube cube = new Cube(Vector3.of(0, 600, 0), 50, Color.green, false);
        Cube cube1 = new Cube(Vector3.of(600, 0, 0), 50, Color.orange, false);
        Cube cube2 = new Cube(Vector3.of(800, 800, 0), 200, Color.magenta, false);
        Cube cube3 = new Cube(Vector3.of(500, 500, 0), 50, Color.pink, false);
        Cube cube4 = new Cube(Vector3.of(0, 0, 0), 50, Color.gray, false);

        Mesh ship = Mesh.loadFromObjFile("./meshes/space-ship.obj", Color.orange, false, -20);
        Mesh teapot = Mesh.loadFromObjFile("./meshes/teapot.obj", Color.orange, false, 0);
        Mesh axis = Mesh.loadFromObjFile("./meshes/axis.obj", Color.orange, false, 0);
        Mesh mountains = Mesh.loadFromObjFile("./meshes/mountains.obj", Color.orange, true, 200);

        triangeables.addAll(List.of(ship));

//        drawables.addAll(List.of(cube, cube1));
    }

    private Vector3 transformVertex(Vector3 vertex){

        return Perspective.transform(camera.transform(vertex));
    }

    private Vector3 getVertexAndTransform(Triangleable toDraw, int triangleIndex){

       Vector3 vertex = toDraw.getVertexByTriangleIndex(triangleIndex);

        return transformVertex(vertex);
    }

    public void draw(){

        zBuffer.clear();

        Vector3[] toDrawVertices = new Vector3[]{
            Vector3.empty(),
            Vector3.empty(),
            Vector3.empty()
        };

        triangeables.forEach(toDraw -> {

            TriConsumer<Vector3[], Color, QuadConsumer> groupDrawFunction = toDraw.getDrawFunction();

            for(int i=0; i <= toDraw.getTriangles().length - 3; i += 3){

                for(int j=0; j < 3; j++){
                    toDrawVertices[j] = getVertexAndTransform(toDraw, i + j);
                }

                if(camera.isTriangleHidden(toDrawVertices[0], toDrawVertices[1])){
//                    continue;
                }

                groupDrawFunction.accept(
                    toDrawVertices,
                    toDraw.randomColors[i / 3],
                    drawFunction
                );

//                Vector3 cross = Vector3.crossProduct(toDrawVertices[0], toDrawVertices[1]);
//
////                if(cross.z > 0){
////
//                    cross = Vector3.crossProduct(toDrawVertices[1], toDrawVertices[0]);
////                }
////
//                Line.draw(toDrawVertices[0], cross, Color.pink, drawFunction);
            }
        });

//        Line.draw(camera.position, camera.position.add(camera.forward.multiply(100)), Color.green);

//        Line.draw(Vector3.of(200, 200, 10), Vector3.of(200, 200, 20), Color.pink, drawFunction);

        drawables.forEach(toDraw -> {
            toDraw.draw(drawFunction);
        });
    }
}
