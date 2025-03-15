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

    private void drawEdges(BufferedImage content){

        Line l1 = new Line(v[0], v[1], color);
        Line l2 = new Line(v[1], v[2], color);
        Line l3 = new Line(v[0], v[2], color);

        l1.draw(content);
        l2.draw(content);
        l3.draw(content);
    }

    @Override
    public void draw(BufferedImage content) {

        if(!isFilled){

            drawEdges(content);
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

            if(v[i].y >= minYVec.y && v[i].y <= maxYVec.y){
                middleVec = v[i];
                break;
            }
        }

        float topLine1Slope = Line.getSlope(minYVec, middleVec);
        float topLine2Slope = Line.getSlope(minYVec, maxYVec);
        float bottomLineSlope = Line.getSlope(middleVec, maxYVec);

        int topLine1B = Line.getBCoef(topLine1Slope, minYVec);
        int topLine2B = Line.getBCoef(topLine2Slope, maxYVec);
        int bottomLineB = Line.getBCoef(bottomLineSlope, maxYVec);

        for(int y = minYVec.y; y <= middleVec.y; y++){

            int x1 = Line.getX(topLine1Slope, topLine1B, y);
            int x2 = Line.getX(topLine2Slope, topLine2B, y);

            int minX = x1;
            int maxX = x2;

            if(x1 > x2){
                minX = x2;
                maxX = x1;
            }

            for(int x = minX; x <= maxX; x++){
                content.setRGB(x, y, color.getRGB());
            }
        }

        for(int y = maxYVec.y; y >= middleVec.y; y--){

            int x1 = Line.getX(topLine2Slope, topLine2B, y);
            int x2 = Line.getX(bottomLineSlope, bottomLineB, y);

            int minX = x1;
            int maxX = x2;

            if(x1 > x2){
                minX = x2;
                maxX = x1;
            }

            for(int x = minX; x <= maxX; x++){
                content.setRGB(x, y, color.getRGB());
            }
        }
    }

    @Override
    public String toString(){

        return Arrays.toString(v);
    }
}
