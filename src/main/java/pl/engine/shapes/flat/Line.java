package pl.engine.shapes.flat;

import pl.engine.math.Vector3;
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
        super(color);

        this.a = a;
        this.b = b;

        if(a.x > b.x){

            this.b = a;
            this.a = b;
        }
    }

    private void drawInvalidLine(){

        double minY = 0;
        double maxY = 0;

        if(a.y < b.y){
            minY = a.y;
            maxY = b.y;
        }

        for(double y = minY; y <= maxY; y++){

            double x = a.x;

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

        double slope = getSlope(a, b);

        double bCoef = getBCoef(slope, a);

        double y = (a.x - 1) * slope + bCoef;

        for(double x = a.x; x <= b.x; x++){

            y += slope;

            drawPixel(x, y, color);

//            for(int w = 0; w < weight; w++, y--){
//                content.setRGB(x, y, color.getRGB());
//            }
        }
    }

    public static double getSlope(Vector3 v1, Vector3 v2){

        return  (float) (v2.y - v1.y) / (float) (v2.x - v1.x);
    }

    public static double getBCoef(double slope, Vector3 v){

        // y = ax + b
        // b = y - ax

        return (int) (v.y - (slope * v.x));
    }

    public static int getX(double slope, double bCoef, double y){

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
