package pl.engine;

import java.awt.*;

public class Rect implements Drawable {

    private Vector3 topLeft;
    private int height;
    private int width;
    private Color color;
    private boolean isFilled;

    public Rect(Vector3 topLeft, int width, int height, Color color, boolean isFilled){

        this(topLeft, width, height, color);
        this.isFilled = isFilled;
    }

    public Rect(Vector3 topLeft, int width, int height, Color color){

        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw(Screen screen) {

        Triangle bottom = new Triangle(
            Vector3.of(topLeft),
            Vector3.of(topLeft.x, topLeft.y + height, topLeft.z),
            Vector3.of(topLeft.x + width, topLeft.y + height, topLeft.z),
            color,
            isFilled
        );

        Triangle top = new Triangle(
            Vector3.of(topLeft),
            Vector3.of(topLeft.x + width, topLeft.y, topLeft.z),
            Vector3.of(topLeft.x + width, topLeft.y + height, topLeft.z),
            color,
            isFilled
        );

        bottom.draw(screen);
        top.draw(screen);
    }
}
