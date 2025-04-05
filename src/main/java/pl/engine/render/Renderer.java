package pl.engine.render;

import pl.engine.Triangleable;
import pl.engine.math.Vector3;
import pl.engine.shapes.Drawable;
import pl.engine.shapes.flat.*;
import pl.engine.shapes.spatial.Cube;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private List<Triangleable> toDraw = new ArrayList<>();
    private List<Drawable> outsideToDraw = new ArrayList<>();
    private Camera camera;

    public Renderer(Camera camera){

        this.camera = camera;

        init();
    }

    private void init(){

        Rect square = new Rect(Vector3.of(500, 500, 10), Vector3.of(600, 600, 10), Color.orange, false);
        Triangle triangle = new Triangle(
            Vector3.of(600, 500, 10),
            Vector3.of(700, 500, 10),
            Vector3.of(700, 700, 10),
            Color.pink,
            false
        );

        Cube cube = new Cube(Vector3.of(0, 600, 10), 100, Color.green, false);
        Cube cube1 = new Cube(Vector3.of(600, 0, 10), 100, Color.orange, false);
        Cube cube2 = new Cube(Vector3.of(600, 600, 10), 100, Color.magenta, false);
        Cube cube3 = new Cube(Vector3.of(0, 0, 10), 100, Color.pink, false);
        Cube cube4 = new Cube(Vector3.of(0, 0, 10), 1000, Color.gray, false);

        toDraw.addAll(List.of(cube, cube1, cube2, cube3, cube4));

        outsideToDraw.addAll(List.of(square, triangle));
    }

    private Vector3 getVertexAndTransform(Triangleable toDraw, int triangleIndex){

       Vector3 vertex = toDraw.getVertexByTriangleIndex(triangleIndex);

        return camera.transform(vertex);
    }

    public void draw(){

        toDraw.forEach(toDraw -> {

            for(int i=0; i < toDraw.getTriangles().length - 3; i += 3){

                Triangle.drawEdges(
                    new Vector3[]{
                        getVertexAndTransform(toDraw, i),
                        getVertexAndTransform(toDraw, i + 1),
                        getVertexAndTransform(toDraw, i + 2),
                    },
                    toDraw.getColor()
                );
            }
        });

        outsideToDraw.forEach(toDraw -> {
            toDraw.draw();
        });
    }
}
