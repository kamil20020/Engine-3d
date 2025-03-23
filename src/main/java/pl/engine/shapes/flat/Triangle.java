package pl.engine.shapes.flat;

import pl.engine.texture.Texturable;
import pl.engine.texture.Texture;

import java.awt.*;
import java.util.Arrays;
import java.util.function.BiConsumer;

public class Triangle extends Texturable {

    private Vector3[] v;
    private boolean isFilled = false;

    public Triangle(Vector3 a, Vector3 b, Vector3 c, Color color, boolean isFilled){
        this(a, b, c, color);

        this.isFilled = isFilled;
    }

    public Triangle(Vector3 a, Vector3 b, Vector3 c, Color color){
        this(a, b, c);

        this.color = color;
    }

    public Triangle(Vector3 a, Vector3 b, Vector3 c, Texture texture){
        this(a, b, c);

        this.isFilled = true;
        this.texture = texture;
    }

    private Triangle(Vector3 a, Vector3 b, Vector3 c){

        this.v = new Vector3[3];
        this.v[0] = a;
        this.v[1] = b;
        this.v[2] = c;
    }

    private void drawEdges(){

        Line l1 = new Line(v[0], v[1], color);
        Line l2 = new Line(v[1], v[2], color);
        Line l3 = new Line(v[0], v[2], color);

        l1.draw();
        l2.draw();
        l3.draw();
    }

    // ----
    // | /
    // |/
    private void drawFilledOrthodonalTop(Vector3 minYVec, Vector3 middleVec, Vector3 maxYVec){

        float topLine2Slope = Line.getSlope(minYVec, maxYVec);

        int topLine2B = Line.getBCoef(topLine2Slope, maxYVec);

        int x2 = middleVec.x;

        BiConsumer<Integer, Integer> draw = getDrawTextureOrColorPixelFunction();

        for(int y = maxYVec.y; y >= middleVec.y; y--){

            drawInvalidRow(topLine2Slope, topLine2B, y, x2, draw);
        }
    }

    // |\
    // | \
    // ----
    private void drawFilledOrthodonalBottom(Vector3 minYVec, Vector3 middleVec, Vector3 maxYVec){

        float topLine2Slope = Line.getSlope(minYVec, maxYVec);

        int topLine2B = Line.getBCoef(topLine2Slope, maxYVec);

        int x1 = middleVec.x;

        BiConsumer<Integer, Integer> draw = getDrawTextureOrColorPixelFunction();

        for(int y = minYVec.y; y <= middleVec.y; y++){

            drawInvalidRow(topLine2Slope, topLine2B, y, x1, draw);
        }
    }

    private void drawInvalidRow(float topLine2Slope, int topLine2B, int y, int x2, BiConsumer<Integer, Integer> draw){

        int x1 = Line.getX(topLine2Slope, topLine2B, y);

        int minX = x1;
        int maxX = x2;

        if(x1 > x2){
            minX = x2;
            maxX = x1;
        }

        for(int x = minX; x <= maxX; x++){

            draw.accept(x, y);
        }
    }

    private void drawValidRow(float topLine1Slope, int topLine1B, float topLine2Slope, int topLine2B, int y, BiConsumer<Integer, Integer> draw){

        int x1 = Line.getX(topLine1Slope, topLine1B, y);

        drawInvalidRow(topLine2Slope, topLine2B, y, x1, draw);
    }

    private void drawFilledValid(Vector3 minYVec, Vector3 middleVec, Vector3 maxYVec){

        float topLine1Slope = Line.getSlope(minYVec, middleVec);
        float topLine2Slope = Line.getSlope(minYVec, maxYVec);
        float bottomLineSlope = Line.getSlope(middleVec, maxYVec);

        int topLine1B = Line.getBCoef(topLine1Slope, minYVec);
        int topLine2B = Line.getBCoef(topLine2Slope, maxYVec);
        int bottomLineB = Line.getBCoef(bottomLineSlope, maxYVec);

        BiConsumer<Integer, Integer> draw = getDrawTextureOrColorPixelFunction();

        for(int y = minYVec.y; y < middleVec.y; y++){

            drawValidRow(topLine1Slope, topLine1B, topLine2Slope, topLine2B, y, draw);
        }

        for(int y = maxYVec.y; y >= middleVec.y; y--){

            drawValidRow(topLine2Slope, topLine2B, bottomLineSlope, bottomLineB, y, draw);
        }
    }

    @Override
    public void draw() {

        if(!isFilled){

            drawEdges();
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
            drawFilledOrthodonalBottom(minYVec, middleVec, maxYVec);
        }
        else if(middleVec.x == maxYVec.x){
            drawFilledOrthodonalTop(minYVec, middleVec, maxYVec);
        }
        else{
            drawFilledValid(minYVec, middleVec, maxYVec);
        }
    }

    @Override
    protected Vector3 getMinXY() {

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        for(Vector3 vec : v){

            if(vec.x < minX){
                minX = vec.x;
            }

            if(vec.y < minY){
                minY = vec.y;
            }
        }

        return Vector3.of(minX, minY, 0);
    }

    @Override
    public String toString(){

        return Arrays.toString(v);
    }
}
