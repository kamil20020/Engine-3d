package pl.engine.texture;

import pl.engine.exceptions.FileLoadException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Texture {

    private int[] pixels;
    private int width;
    private int height;
    private int shiftX = 0;
    private int shiftY = 0;

    public Texture(String path) {

        URL resourceURL = getClass().getResource(path);

        BufferedImage img;

        try{
            img = ImageIO.read(resourceURL);
        }
        catch(IOException e){
            throw new FileLoadException(e.getMessage());
        }

        height = img.getHeight();
        width = img.getWidth();

        pixels = new int[width * height];

        img.getRGB(0, 0, width, height, pixels, 0, width);
    }

    public Texture(int[] pixels, int width, int height, int shiftX, int shiftY){

        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    public static Texture of(String path){

        return new Texture(path);
    }

    public int[] getPixels(){

        return pixels;
    }

    public int getHeight(){

        return height;
    }

    public int getWidth(){

        return width;
    }

    public Color getColorOnPosition(int x, int y){

        x += shiftX;
        y += shiftY;

        int pixel = pixels[y * width + x];

        return new Color(pixel);
    }
}
