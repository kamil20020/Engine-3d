package pl.engine;

import java.awt.*;

public class Square implements Drawable{

    private Vector3 topLeft;
    private int a;
    private Color color;
    private boolean isFilled;

    public Square(Vector3 topLeft, int a, Color color, boolean isFilled){

        this(topLeft, a, color);
        this.isFilled = isFilled;
    }

    public Square(Vector3 topLeft, int a, Color color){

        this.topLeft = topLeft;
        this.a = a;
        this.color = color;
    }

    @Override
    public void draw(Screen screen) {

        Triangle bottom = new Triangle(
            Vector3.of(topLeft),
            Vector3.of(topLeft.x, topLeft.y + a, topLeft.z),
            Vector3.of(topLeft.x + a, topLeft.y + a, topLeft.z),
            color,
            isFilled
        );

        Triangle top = new Triangle(
            Vector3.of(topLeft),
            Vector3.of(topLeft.x + a, topLeft.y, topLeft.z),
            Vector3.of(topLeft.x + a, topLeft.y + a, topLeft.z),
            color,
            isFilled
        );

        bottom.draw(screen);
        top.draw(screen);
    }
}
