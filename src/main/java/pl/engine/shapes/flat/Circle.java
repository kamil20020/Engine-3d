package pl.engine.shapes.flat;

import pl.engine.shapes.Drawable;

import java.awt.*;

public class Circle extends Drawable {

    private Vector3 mid;
    private int r;

    public Circle(Vector3 mid, int r, Color color){

        this.mid = mid;
        this.r = r;
        this.color = color;
    }

    @Override
    public void draw() {

        int x;
        int y;

        for(double deg = 0; deg <= 360; deg++){

            double degInRadians = Math.toRadians(deg);

            x = (int) ((double) r * Math.cos(degInRadians)) + mid.x;
            y = (int) ((double) r *  Math.sin(degInRadians)) + mid.y;

            drawPixel(x, y, color);
        }
    }
}
