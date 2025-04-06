package pl.engine.shapes.flat;

import pl.engine.Triangleable;
import pl.engine.math.Vector3;
import pl.engine.render.Perspective;
import pl.engine.shapes.Drawable;
import pl.engine.texture.Texturable;
import pl.engine.texture.Texture;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;
import java.util.function.BiConsumer;

public class Triangle extends Drawable {

    private Vector3[] v;
    private boolean isFilled = false;

    public Triangle(Vector3 a, Vector3 b, Vector3 c, Color color, boolean isFilled){
        super(color);

        this.v = new Vector3[]{a, b, c};
        this.isFilled = isFilled;
    }

    @Override
    public void draw() {

        if(!isFilled){

            drawEdges(v, color);
            return;
        }

        drawFilled(v, color);
    }

    public static void drawEdges(Vector3[] v, Color color){

        Line.draw(v[0], v[1], color);
        Line.draw(v[1], v[2], color);
        Line.draw(v[0], v[2], color);
    }

    public static void drawFilled(Vector3[] v, Color color){

        if((v[0].x == v[1].x && v[1].x == v[2].x) || (v[0].y == v[1].y && v[1].y == v[2].y)){
            return;
        }

        Vector3 minYVec = v[0];
        Vector3 maxYVec = v[0];

        for(int i = 1; i <= 2; i++){

            if(v[i].y < minYVec.y){
                minYVec = v[i];
            }
            else if(v[i].y > maxYVec.y){
                maxYVec = v[i];
            }
        }

        Vector3 middleVec = null;

        for(int i = 0; i <= 2; i++){

            if(v[i] != minYVec && v[i] != maxYVec){
                middleVec = v[i];
                break;
            }
        }

        if(minYVec.x == maxYVec.x){

            Vector3 buffer;

            if(minYVec.y == middleVec.y){

                buffer = minYVec;
                minYVec = middleVec;
            }
            else{

                buffer = maxYVec;
                maxYVec = middleVec;
            }

            middleVec = buffer;
        }

        if(middleVec.x == minYVec.x){
            drawFilledOrthodonalBottom(minYVec, middleVec, maxYVec, color);
        }
        else if(middleVec.x == maxYVec.x){
            drawFilledOrthodonalTop(minYVec, middleVec, maxYVec, color);
        }
        else{
            drawFilledValid(minYVec, middleVec, maxYVec, color);
        }
    }

    // ----
    // | /
    // |/
    private static void drawFilledOrthodonalTop(Vector3 minYVec, Vector3 middleVec, Vector3 maxYVec, Color color){

        double topLine2Slope = Line.getSlope(minYVec, maxYVec);

        double topLine2B = Line.getBCoef(topLine2Slope, maxYVec);

        double x2 = middleVec.x;

        for(double y = maxYVec.y; y >= middleVec.y; y--){

            drawInvalidRow(topLine2Slope, topLine2B, y, x2, color);
        }
    }

    // |\
    // | \
    // ----
    private static void drawFilledOrthodonalBottom(Vector3 minYVec, Vector3 middleVec, Vector3 maxYVec, Color color){

        double topLine2Slope = Line.getSlope(minYVec, maxYVec);

        double topLine2B = Line.getBCoef(topLine2Slope, maxYVec);

        double x1 = middleVec.x;

        for(double y = minYVec.y; y <= middleVec.y; y++){

            drawInvalidRow(topLine2Slope, topLine2B, y, x1, color);
        }
    }

    private static void drawInvalidRow(double topLine2Slope, double topLine2B, double y, double x2, Color color){

        double x1 = Line.getX(topLine2Slope, topLine2B, y);

        double minX = x1;
        double maxX = x2;

        if(x1 > x2){
            minX = x2;
            maxX = x1;
        }

        for(double x = minX; x <= maxX; x++){

            drawPixel(x, y, color);
        }
    }

    private static void drawFilledValid(Vector3 minYVec, Vector3 middleVec, Vector3 maxYVec, Color color){

        double topLine1Slope = Line.getSlope(minYVec, middleVec);
        double topLine2Slope = Line.getSlope(minYVec, maxYVec);
        double bottomLineSlope = Line.getSlope(middleVec, maxYVec);

        double topLine1B = Line.getBCoef(topLine1Slope, minYVec);
        double topLine2B = Line.getBCoef(topLine2Slope, maxYVec);
        double bottomLineB = Line.getBCoef(bottomLineSlope, maxYVec);

        for(double y = minYVec.y; y < middleVec.y; y++){

            drawValidRow(topLine1Slope, topLine1B, topLine2Slope, topLine2B, y, color);
        }

        for(double y = maxYVec.y; y >= middleVec.y; y--){

            drawValidRow(topLine2Slope, topLine2B, bottomLineSlope, bottomLineB, y, color);
        }
    }

    private static void drawValidRow(double topLine1Slope, double topLine1B, double topLine2Slope, double topLine2B, double y, Color color){

        double x1 = Line.getX(topLine1Slope, topLine1B, y);

        drawInvalidRow(topLine2Slope, topLine2B, y, x1, color);
    }

    @Override
    public String toString(){

        return Arrays.toString(v);
    }
}
