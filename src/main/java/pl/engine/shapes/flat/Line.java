package pl.engine.shapes.flat;

import pl.engine.Vector3;
import pl.engine.shapes.Drawable;

import java.awt.*;

public class Line extends Drawable {

    private Vector3 a, b;
    private int weight = 1;

    public Line(Vector3 a, Vector3 b, Color color, int weight){
        this(a, b, color);

        this.weight = weight;
    }

    public Line(Vector3 a, Vector3 b, Color color){

        this.a = a;
        this.b = b;
        this.color = color;

        if(a.x > b.x){

            this.b = a;
            this.a = b;
        }
    }

    private void drawInvalidLine(){

        int minY = 0;
        int maxY = 0;

        if(a.y < b.y){
            minY = a.y;
            maxY = b.y;
        }

        for(int y = minY; y <= maxY; y++){

            int x = a.x;

            drawPixel(x, y, color);

//            for(int w = 0; w < weight; w++, x++){
//                content.setRGB(x, y, color.getRGB());
//            }
        }
    }

    @Override
    public void draw() {

        // y = ax + b
        // b = y - ax

        if(a.x == b.x){

            drawInvalidLine();
            return;
        }

        float slope = getSlope(a, b);

        int bCoef = getBCoef(slope, a);

        float y = ((float)(a.x - 1) * slope + bCoef);

        for(int x = a.x; x <= b.x; x++){

            y += slope;

            drawPixel(x, (int) y, color);

//            for(int w = 0; w < weight; w++, y--){
//                content.setRGB(x, y, color.getRGB());
//            }
        }
    }

    public static float getSlope(Vector3 v1, Vector3 v2){

        return  (float) (v2.y - v1.y) / (float) (v2.x - v1.x);
    }

    public static int getBCoef(float slope, Vector3 v){

        // y = ax + b
        // b = y - ax

        return (int) (v.y - (slope * v.x));
    }

    public static int getX(float slope, int bCoef, int y){

        // y = ax + b
        // ax = y - b
        // x = (y - b) / a

        return (int) ((y - bCoef) / slope);
    }

    @Override
    public String toString(){

        return a.toString() + "\n" + b.toString();
    }
}
