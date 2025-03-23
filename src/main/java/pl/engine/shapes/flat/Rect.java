package pl.engine.shapes.flat;

import pl.engine.texture.Texturable;
import pl.engine.texture.Texture;

import java.awt.*;

public class Rect extends Texturable {

    private Vector3 topLeft;
    private int height;
    private int width;
    private boolean isFilled;

    public Rect(Vector3 topLeft, int width, int height, Color color, boolean isFilled){
        this(topLeft, width, height, color);

        this.isFilled = isFilled;
    }

    public Rect(Vector3 topLeft, int width, int height, Color color){
        this(topLeft, width, height);

        this.color = color;
    }

    public Rect(Vector3 topLeft, int width, int height, Texture texture){
        this(topLeft, width, height);

        this.texture = texture;
    }

    private Rect(Vector3 topLeft, int width, int height){

        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {

        Vector3 v1 = Vector3.of(topLeft);
        Vector3 v2 = Vector3.of(topLeft.x, topLeft.y + height, topLeft.z);
        Vector3 v3 = Vector3.of(topLeft.x + width, topLeft.y + height, topLeft.z);

        Vector3 v4 = Vector3.of(topLeft);
        Vector3 v5 = Vector3.of(topLeft.x + width, topLeft.y, topLeft.z);
        Vector3 v6 = Vector3.of(topLeft.x + width, topLeft.y + height, topLeft.z);

        Triangle bottom;
        Triangle top;

        if(texture == null){

            bottom = new Triangle(v1, v2, v3, color, isFilled);
            top = new Triangle(v4, v5, v6, color, isFilled);
        }
        else{

            bottom = new Triangle(v1, v2, v3, texture);
            top = new Triangle(v4, v5, v6, texture);
        }

        bottom.draw();
        top.draw();
    }

    @Override
    protected Vector3 getMinXY() {

        return Vector3.of(topLeft);
    }
}
