package pl.engine.shapes.flat;

import pl.engine.math.Vector3;
import pl.engine.shapes.Drawable;

import java.awt.*;

public class Circle extends Drawable {

    private Vector3 mid;
    private double r;

    public Circle(Vector3 mid, int r, Color color){
        super(color);

        this.mid = mid;
        this.r = r;
    }

    @Override
    public void draw() {

        double x;
        double y;

        for(double deg = 0; deg <= 360; deg++){

            double degInRadians = Math.toRadians(deg);

            x = r * Math.cos(degInRadians) + mid.x;
            y = r * Math.sin(degInRadians) + mid.y;

            drawPixel(x, y, color);
        }
    }
}
