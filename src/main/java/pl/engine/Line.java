package pl.engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Line implements Drawable{

    private Vector3 a, b;
    private Color color;
    private int weight = 1;

    public Line(Vector3 a, Vector3 b, Color color, int weight) throws IllegalArgumentException{
        this(a, b, color);

        this.weight = weight;
    }

    public Line(Vector3 a, Vector3 b, Color color) throws IllegalArgumentException{

        this.a = a;
        this.b = b;
        this.color = color;

        if(a.x > b.x){

            this.b = a;
            this.a = b;
        }
    }

    private void drawInvalidLine(BufferedImage content){

        int minY = 0;
        int maxY = 0;

        if(a.y < b.y){
            minY = a.y;
            maxY = b.y;
        }

        for(int y = minY; y <= maxY; y++){

            int x = a.x;

            content.setRGB(x, y, color.getRGB());

//            for(int w = 0; w < weight; w++, x++){
//                content.setRGB(x, y, color.getRGB());
//            }
        }
    }

    @Override
    public void draw(BufferedImage content) {

        // y = ax + b
        // b = y - ax

        if(a.x == b.x){

            drawInvalidLine(content);
            return;
        }

        float slope = getSlope(a, b);

        int bCoef = getBCoef(slope, a);

        a.draw(content);
        b.draw(content);

        float y = ((float)(a.x - 1) * slope + bCoef);

        for(int x = a.x; x <= b.x; x++){

            y += slope;

            content.setRGB(x, (int) y, color.getRGB());

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
