package pl.engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Triangle implements Drawable{

    private Vector3[] v;
    private Color color;
    private boolean isFilled = false;

    public Triangle(Vector3 a, Vector3 b, Vector3 c, Color color, boolean isFilled){
        this(a, b, c, color);

        this.isFilled = isFilled;
    }

    public Triangle(Vector3 a, Vector3 b, Vector3 c, Color color){

        this.v = new Vector3[3];
        this.v[0] = a;
        this.v[1] = b;
        this.v[2] = c;
        this.color = color;
    }

    private void drawEdges(Screen screen){

        Line l1 = new Line(v[0], v[1], color);
        Line l2 = new Line(v[1], v[2], color);
        Line l3 = new Line(v[0], v[2], color);

        l1.draw(screen);
        l2.draw(screen);
        l3.draw(screen);
    }

    // ----
    // | /
    // |/
    private void drawFilledOrthodonalTop(Screen screen, Vector3 minYVec, Vector3 middleVec, Vector3 maxYVec){

        float topLine2Slope = Line.getSlope(minYVec, maxYVec);

        int topLine2B = Line.getBCoef(topLine2Slope, maxYVec);

        int x2 = middleVec.x;

        for(int y = maxYVec.y; y >= middleVec.y; y--){

            drawInvalidRow(screen, topLine2Slope, topLine2B, y, x2);
        }
    }

    // |\
    // | \
    // ----
    private void drawFilledOrthodonalBottom(Screen screen, Vector3 minYVec, Vector3 middleVec, Vector3 maxYVec){

        float topLine2Slope = Line.getSlope(minYVec, maxYVec);

        int topLine2B = Line.getBCoef(topLine2Slope, maxYVec);

        int x1 = middleVec.x;

        for(int y = minYVec.y; y <= middleVec.y; y++){

            drawInvalidRow(screen, topLine2Slope, topLine2B, y, x1);
        }
    }

    private void drawInvalidRow(Screen screen, float topLine2Slope, int topLine2B, int y, int x2){

        int x1 = Line.getX(topLine2Slope, topLine2B, y);

        int minX = x1;
        int maxX = x2;

        if(x1 > x2){
            minX = x2;
            maxX = x1;
        }

        for(int x = minX; x <= maxX; x++){
            screen.draw(x, y, color);
        }
    }

    private void drawValidRow(Screen screen, float topLine1Slope, int topLine1B, float topLine2Slope, int topLine2B, int y){

        int x1 = Line.getX(topLine1Slope, topLine1B, y);

        drawInvalidRow(screen, topLine2Slope, topLine2B, y, x1);
    }

    private void drawFilledValid(Screen screen, Vector3 minYVec, Vector3 middleVec, Vector3 maxYVec){

        float topLine1Slope = Line.getSlope(minYVec, middleVec);
        float topLine2Slope = Line.getSlope(minYVec, maxYVec);
        float bottomLineSlope = Line.getSlope(middleVec, maxYVec);

        int topLine1B = Line.getBCoef(topLine1Slope, minYVec);
        int topLine2B = Line.getBCoef(topLine2Slope, maxYVec);
        int bottomLineB = Line.getBCoef(bottomLineSlope, maxYVec);

        for(int y = minYVec.y; y < middleVec.y; y++){

            drawValidRow(screen, topLine1Slope, topLine1B, topLine2Slope, topLine2B, y);
        }

        for(int y = maxYVec.y; y >= middleVec.y; y--){

            drawValidRow(screen, topLine2Slope, topLine2B, bottomLineSlope, bottomLineB, y);
        }
    }

    @Override
    public void draw(Screen screen) {

        if(!isFilled){

            drawEdges(screen);
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
            drawFilledOrthodonalBottom(screen, minYVec, middleVec, maxYVec);
        }
        else if(middleVec.x == maxYVec.x){
            drawFilledOrthodonalTop(screen, minYVec, middleVec, maxYVec);
        }
        else{
            drawFilledValid(screen, minYVec, middleVec, maxYVec);
        }
    }

    @Override
    public String toString(){

        return Arrays.toString(v);
    }
}
