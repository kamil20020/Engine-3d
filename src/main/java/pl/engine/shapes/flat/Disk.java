package pl.engine.shapes.flat;

import pl.engine.Vector3;
import pl.engine.texture.Texturable;
import pl.engine.texture.Texture;

import java.awt.*;
import java.util.function.BiConsumer;

public class Disk extends Texturable {

    private Vector3 mid;
    private int r;

    public Disk(Vector3 mid, int r, Color color){
        this(mid, r);

        this.color = color;
    }

    public Disk(Vector3 mid, int r, Texture texture){
        this(mid, r);

        this.texture = texture;
    }

    private Disk(Vector3 mid, int r){

        this.mid = mid;
        this.r = r;
    }

    @Override
    public void draw() {

        int y;
        int prevY = Integer.MIN_VALUE;

        BiConsumer<Integer, Integer> draw = getDrawTextureOrColorPixelFunction();

        for(double deg = 90; deg >= -90; deg--){

            double degInRadians = Math.toRadians(deg);

            y = (int) Math.round((double) r * Math.sin(degInRadians)) + mid.y;

            int rightX = (int) ((double) r * Math.cos(degInRadians)) + mid.x;
            int leftX = 2 * mid.x - rightX; // got from mid.x - (rightX - mid.x)

            int yDiff = prevY - y;

            if(prevY != Integer.MIN_VALUE && yDiff > 1){

                for(int missingY = prevY - 1; missingY >= y; missingY--){

                    for(int x = leftX; x <= rightX; x++){

                        draw.accept(x, missingY);
                    }
                }
            }
            else {

                for(int x = leftX; x <= rightX; x++){

                    draw.accept(x, y);
                }
            }

            prevY = y;
        }
    }

    @Override
    protected Vector3 getMinXY() {

        return Vector3.of(mid.x - r, mid.y - r, 0);
    }
}
