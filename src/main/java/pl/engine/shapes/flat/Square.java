package pl.engine.shapes.flat;

import pl.engine.Vector3;
import pl.engine.texture.Texturable;
import pl.engine.texture.Texture;

import java.awt.*;

public class Square extends Texturable {

    private Vector3 topLeft;
    private int a;
    private boolean isFilled;

    public Square(Vector3 topLeft, int a, Color color, boolean isFilled){
        this(topLeft, a, color);

        this.isFilled = isFilled;
    }

    public Square(Vector3 topLeft, int a, Color color){
        this(topLeft, a);

        this.color = color;
    }

    public Square(Vector3 topLeft, int a, Texture texture){
        this(topLeft, a);

        this.isFilled = true;
        this.texture = texture;
    }

    private Square(Vector3 topLeft, int a){

        this.topLeft = topLeft;
        this.a = a;
    }

    @Override
    public void draw() {

        if(texture != null){

            for(int y = topLeft.y; y < topLeft.y + a; y++){

                for(int x = topLeft.x; x < topLeft.x + a; x++) {

                    drawTexturePixel(x, y);
                }
            }

            return;
        }

        Vector3 v1 = Vector3.of(topLeft);
        Vector3 v2 = Vector3.of(topLeft.x, topLeft.y + a, topLeft.z);
        Vector3 v3 = Vector3.of(topLeft.x + a, topLeft.y + a, topLeft.z);

        Vector3 v4 = Vector3.of(topLeft);
        Vector3 v5 = Vector3.of(topLeft.x + a, topLeft.y, topLeft.z);
        Vector3 v6 = Vector3.of(topLeft.x + a, topLeft.y + a, topLeft.z);

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
