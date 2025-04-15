package pl.engine.shapes.flat;

import pl.engine.math.Vector3;
import pl.engine.general.QuadConsumer;
import pl.engine.shapes.Drawable;

import java.awt.*;

public class Line extends Drawable {

    public Vector3 a, b;
    private int weight = 1;

    public Line(Vector3 a, Vector3 b, Color color, int weight){
        this(a, b, color);

        this.weight = weight;
    }

    public Line(Vector3 a, Vector3 b, Color color){
        super(color);

        a = Vector3.of(a);
        b = Vector3.of(b);

        if(a.x > b.x){

            this.b = a;
            this.a = b;
        }
        else{
            this.a = a;
            this.b = b;
        }
    }

    @Override
    public void draw(QuadConsumer<Double, Double, Double, Color> drawFunction) {

        draw(a, b, color, drawFunction);
    }

    public static void draw(Vector3 a, Vector3 b, Color color, QuadConsumer<Double, Double, Double, Color> drawFunction){

        if(a.x > b.x){

            Vector3 buffer = a;
            a = b;
            b = buffer;
        }

        // y = ax + b
        // b = y - ax

        if(a.x == b.x){

            if(a.y != b.y){
                drawVerticalLine(a, b, color, drawFunction);
            }
            else{
                drawHorizontalZLine(a, b, color, drawFunction);
            }

            return;
        }

        double slope = getSlope(a, b);

        double bCoef = getBCoef(slope, a);

        double y = (a.x - 1) * slope + bCoef;

        for(double x = a.x; x <= b.x; x++){

            y += slope;

            double completeStepsRatio = (x - a.x) / (b.x - a.x);
            double z = completeStepsRatio * (b.z - a.z) + a.z;

            drawFunction.accept(x, y, z, color);

//            for(int w = 0; w < weight; w++, y--){
//                content.setRGB(x, y, color.getRGB());
//            }
        }
    }

    private static void drawVerticalLine(Vector3 a, Vector3 b, Color color, QuadConsumer<Double, Double, Double, Color> drawFunction){

        if(a.y > b.y){

            Vector3 buffer = a;

            a = b;
            b = buffer;
        }

        for(double y = a.y; y <= b.y; y++){

            double completeStepsRatio = (y - a.y) / (b.y - a.y);
            double z = completeStepsRatio * (b.z - a.z) + a.z;

            drawFunction.accept(a.x, y, z, color);

//            for(int w = 0; w < weight; w++, x++){
//                content.setRGB(x, y, color.getRGB());
//            }
        }
    }

    private static void drawHorizontalZLine(Vector3 a, Vector3 b, Color color, QuadConsumer<Double, Double, Double, Color> drawFunction){

        if(a.z > b.z){

            Vector3 buffer = a;

            a = b;
            b = buffer;
        }

        for(double z = a.z; z <= b.z; z++){

            drawFunction.accept(a.x, a.y, z, color);
        }
    }

    public static double getSlope(Vector3 v1, Vector3 v2){

        return (v2.y - v1.y) / (v2.x - v1.x);
    }

    public static double getBCoef(double slope, Vector3 v){

        // y = ax + b
        // b = y - ax

        return v.y - (slope * v.x);
    }

    public static double getX(double slope, double bCoef, double y){

        return getX(slope, bCoef, y, 0);
    }

    public static double getX(double slope, double bCoef, double y, double defaultX){

        // y = ax + b
        // ax = y - b
        // x = (y - b) / a

        if(slope == Double.POSITIVE_INFINITY){
            return defaultX;
        }

        return (y - bCoef) / slope;
    }

    @Override
    public String toString(){

        return a.toString() + "\n" + b.toString();
    }
}
