package pl.engine.render;

import pl.engine.math.Vector3;
import pl.engine.render.Camera;
import pl.engine.render.Perspective;
import pl.engine.shapes.Drawable;
import pl.engine.shapes.flat.*;
import pl.engine.shapes.spatial.Cube;
import pl.engine.texture.GridTexture;
import pl.engine.texture.Texture;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Renderer {

    private List<Drawable> drawables = new ArrayList<>();
    private Camera camera;

    public Renderer(Camera camera){

        this.camera = camera;

        init();
    }

    private void init(){

        Square square = new Square(Vector3.of(500, 500, 10), 100, Color.orange, true);
        Triangle triangle = new Triangle(
            Vector3.of(600, 500, 10),
            Vector3.of(700, 500, 10),
            Vector3.of(700, 700, 10),
            Color.pink,
            true
        );

        Cube cube = new Cube(Vector3.of(800, 800, 100), 100, Color.green, false);

        drawables.addAll(List.of(square, triangle, cube));
    }

    public void draw(){

        drawables.forEach(Drawable::draw);
    }
}
