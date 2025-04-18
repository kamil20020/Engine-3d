package pl.engine.shapes.flat;

import pl.engine.general.QuadConsumer;
import pl.engine.math.Vector3;
import pl.engine.shapes.Drawable;

import java.awt.*;
import java.util.Arrays;

public class Triangle extends Drawable {

    private Vector3[] v;
    private boolean isFilled = false;

    public Triangle(Vector3 a, Vector3 b, Vector3 c, Color color, boolean isFilled){
        super(color);

        this.v = new Vector3[]{Vector3.of(a), Vector3.of(b), Vector3.of(c)};
        this.isFilled = isFilled;
    }

    @Override
    public void draw(QuadConsumer<Double, Double, Double, Color> drawFunction) {

        if(!isFilled){

            drawEdges(v, color, drawFunction);
            return;
        }

        drawFilled(v, color, drawFunction);
    }

    public static void drawEdges(Vector3[] v, Color color, QuadConsumer<Double, Double, Double, Color> drawFunction){

        Line.draw(v[0], v[1], color, drawFunction);
        Line.draw(v[1], v[2], color, drawFunction);
        Line.draw(v[0], v[2], color, drawFunction);
    }

    public static void drawFilled(Vector3[] v, Color color, QuadConsumer<Double, Double, Double, Color> drawFunction){

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

            Vector3 buffer = middleVec;

            if(minYVec.y == middleVec.y){

                buffer = minYVec;
                minYVec = middleVec;
            }
            else if(middleVec.y == maxYVec.y){

                buffer = maxYVec;
                maxYVec = middleVec;
            }

            middleVec = buffer;
        }

        drawFilled(minYVec, middleVec, maxYVec, color, drawFunction);
    }

    private static void drawFilled(Vector3 minYVec, Vector3 middleVec, Vector3 maxYVec, Color color, QuadConsumer<Double, Double, Double, Color> drawFunction){

        double topLine1Slope = Line.getSlope(minYVec, middleVec);
        double topLine2Slope = Line.getSlope(minYVec, maxYVec);
        double bottomLineSlope = Line.getSlope(middleVec, maxYVec);

        double topLine1B = Line.getBCoef(topLine1Slope, minYVec);
        double topLine2B = Line.getBCoef(topLine2Slope, maxYVec);
        double bottomLineB = Line.getBCoef(bottomLineSlope, maxYVec);

        double defaultX1 = middleVec.x;
        double defaultX2 = middleVec.x;

        if(middleVec.y != maxYVec.y && middleVec.y != minYVec.y){
            defaultX2 = maxYVec.x;
        }

        for(double y = minYVec.y; y <= middleVec.y; y++){

            double verticalOneSideZ = interpolateZ(minYVec.z, middleVec.z, minYVec.y, middleVec.y, y);
            double verticalOtherSizeZ = interpolateZ(minYVec.z, maxYVec.z, minYVec.y, maxYVec.y, y);

            drawFilledRow(verticalOneSideZ, verticalOtherSizeZ, topLine1Slope, topLine1B, topLine2Slope, topLine2B, y, defaultX1, defaultX2, color, drawFunction);
        }

        for(double y = middleVec.y; y <= maxYVec.y; y++){

            double verticalOneSideZ = interpolateZ(middleVec.z, maxYVec.z, middleVec.y, maxYVec.y, y);
            double verticalOtherSizeZ = interpolateZ(minYVec.z, maxYVec.z, minYVec.y, maxYVec.y, y);

            drawFilledRow(verticalOneSideZ, verticalOtherSizeZ, topLine2Slope, topLine2B, bottomLineSlope, bottomLineB, y, defaultX2, defaultX1, color, drawFunction);
        }
    }

    private static void drawFilledRow(double verticalOneSideZ, double verticalOtherSizeZ, double topLine1Slope, double topLine1B, double topLine2Slope, double topLine2B, double y, double defaultX1, double defaultX2, Color color, QuadConsumer<Double, Double, Double, Color> drawFunction){

        double x1 = Line.getX(topLine1Slope, topLine1B, y, defaultX1);
        double x2 = Line.getX(topLine2Slope, topLine2B, y, defaultX2);

        double minX = x1;
        double maxX = x2;

        if(x1 > x2){
            minX = x2;
            maxX = x1;
        }

        for(double x = minX; x <= maxX; x++){

            double horizontalZ = interpolateZ(verticalOneSideZ, verticalOtherSizeZ, minX, maxX, x);

            drawFunction.accept(x, y, horizontalZ, color);
        }
    }

    private static double interpolateZ(double aPos, double bPos, double minVal, double maxVal, double val){

        if(minVal == maxVal){
            return aPos;
        }

        double completeRatio = (val - minVal) / (maxVal - minVal);

        if(aPos > bPos){

            double buffer = aPos;

            aPos = bPos;
            bPos = buffer;
        }

        return (bPos - aPos) * completeRatio + aPos;
    }

    public static Vector3 getCenter(Vector3 a, Vector3 b, Vector3 c){

        return a
            .add(b)
            .add(c)
            .change(v -> v / 3);
    }

    public static int getMinXYIndex(Vector3[] vertices){

        int minIndex = 0;

        Vector3 minXY = vertices[0];

        for(int i=0; i < vertices.length; i++){

            Vector3 vertex = vertices[minIndex];

            if(vertex.x <= minXY.x && vertex.y <= minXY.y){
                minXY = vertex;
                minIndex = i;
            }
        }

        return minIndex;
    }

    public static int getMaxXYIndex(Vector3[] vertices){

        int maxIndex = 0;

        Vector3 maxXY = vertices[0];

        for(int i=1; i < vertices.length; i++){

            Vector3 vertex = vertices[maxIndex];

            if(vertex.x >= maxXY.x && vertex.y >= maxXY.y){
                maxXY = vertex;
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    @Override
    public String toString(){

        return Arrays.toString(v);
    }
}
