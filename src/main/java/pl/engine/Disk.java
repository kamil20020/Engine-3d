package pl.engine;

import java.awt.*;

public class Disk implements Drawable{

    private Vector3 mid;
    private int r;
    private Color color;

    public Disk(Vector3 mid, int r, Color color){

        this.mid = mid;
        this.r = r;
        this.color = color;
    }

    @Override
    public void draw(Screen screen) {

        int y;
        int prevY = Integer.MIN_VALUE;

        for(double deg = 90; deg >= -90; deg--){

            double degInRadians = Math.toRadians(deg);

            y = (int) Math.round((double) r * Math.sin(degInRadians)) + mid.y;

            int rightX = (int) ((double) r * Math.cos(degInRadians)) + mid.x;
            int leftX = 2 * mid.x - rightX; //mid.x - (rightX - mid.x)

            int yDiff = prevY - y;

            if(prevY != Integer.MIN_VALUE && yDiff > 1){

                for(int missingY = prevY - 1; missingY >= y; missingY--){

                    for(int x = leftX; x <= rightX; x++){

                        screen.draw(x, missingY, color);
                    }
                }
            }
            else {

                for(int x = leftX; x <= rightX; x++){

                    screen.draw(x, y, color);
                }
            }

            prevY = y;
        }
    }
}
