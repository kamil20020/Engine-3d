package pl.engine.shapes.flat;

import pl.engine.math.Vector3;
import pl.engine.general.QuadConsumer;
import pl.engine.shapes.Drawable;

import java.awt.*;

public class Disk extends Drawable {

    private Vector3 mid;
    private double r;

    public Disk(Vector3 mid, int r, Color color){
        super(color);

        this.mid = mid;
        this.r = r;
    }

    @Override
    public void draw(QuadConsumer<Double, Double, Double, Color> drawFunction) {

        double y;
        double prevY = Integer.MIN_VALUE;

        for(double deg = 90; deg >= -90; deg--){

            double degInRadians = Math.toRadians(deg);

            y = Math.round(r * Math.sin(degInRadians)) + mid.y;

            double rightX = r * Math.cos(degInRadians) + mid.x;
            double leftX = 2 * mid.x - rightX; // got from mid.x - (rightX - mid.x)

            double yDiff = prevY - y;

            if(prevY != Integer.MIN_VALUE && yDiff > 1){

                for(double missingY = prevY - 1; missingY >= y; missingY--){

                    for(double x = leftX; x <= rightX; x++){

                        drawFunction.accept(x, y, mid.z, color);
                    }
                }
            }
            else {

                for(double x = leftX; x <= rightX; x++){

                    drawFunction.accept(x, y, mid.z, color);
                }
            }

            prevY = y;
        }
    }
}
