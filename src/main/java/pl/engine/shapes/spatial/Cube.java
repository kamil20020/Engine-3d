package pl.engine.shapes.spatial;

import pl.engine.math.Vector3;
import pl.engine.shapes.Mesh;
import pl.engine.shapes.flat.Square;
import pl.engine.shapes.flat.Triangle;
import pl.engine.texture.Texturable;
import pl.engine.texture.Texture;

import java.awt.*;

public class Cube extends Mesh {

    private Vector3 leftDownFrontCorner;
    private Square walls;
    private double a;
    private boolean isFilled = false;

    public Cube(Vector3 leftDownFrontCorner, int a, Color color, boolean isFilled){
        this(leftDownFrontCorner, a, color);

        this.color = color;
        this.isFilled = isFilled;
    }

    public Cube(Vector3 leftDownFrontCorner, int a, Color color){
       this(leftDownFrontCorner, a);

       this.color = color;
    }

    private Cube(Vector3 leftDownFrontCorner, int a, Texture texture){
        this(leftDownFrontCorner, a);

        this.texture = texture;
    }

    private Cube(Vector3 leftDownFrontCorner, int a){
        super(new Triangle[12]);

        this.leftDownFrontCorner = leftDownFrontCorner;
        this.a = a;

        init();
    }

    private void init(){


    }

    @Override
    public void draw() {

    }

    @Override
    protected Vector3 getMinXY() {
        return null;
    }
}
