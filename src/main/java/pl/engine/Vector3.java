package pl.engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Vector3 implements Drawable{

    public int x, y, z;

    public Vector3(Integer x, Integer y, Integer z){

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3 of(Integer x, Integer y, Integer z){

        return new Vector3(x, y, z);
    }

    @Override
    public void draw(Screen screen) {

        screen.draw(x, y, Color.red);
    }

    @Override
    public String toString(){

        return "(" + x + ", " + y + ", " + z + ")";
    }
}
