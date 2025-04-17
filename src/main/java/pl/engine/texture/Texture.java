package pl.engine.texture;

import pl.engine.exceptions.FileLoadException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Texture {

    private int[] pixels;
    private int imgWidth;
    private int imgHeight;
    private int width;
    private int height;
    private int shiftX = 0;
    private int shiftY = 0;

    public Texture(String path) {

        BufferedImage img;

        try{
            img = loadImg(path);
        }
        catch(IOException e){
            throw new FileLoadException(e.getMessage());
        }

        height = img.getHeight();
        width = img.getWidth();

        imgWidth = width;
        imgHeight = height;

        pixels = new int[width * height];

        img.getRGB(0, 0, width, height, pixels, 0, width);
    }

    public Texture(int[] pixels, int imgWidth, int imgHeight, int width, int height, int shiftX, int shiftY){

        this.pixels = pixels;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.width = width;
        this.height = height;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    private BufferedImage loadImg(String path) throws IOException{

        URL resourceURL = Texture.class.getClassLoader().getResource(path);

        return ImageIO.read(resourceURL);
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

    public Color getColorFromLimitedTexture(double x, double y, double minU, double minV, double maxU, double maxV) throws IllegalArgumentException{

        double minLimitedTextureX = getX(minU);
        double minLimitedTextureY = getY(minV);
        double maxLimitedTextureX = getX(maxU);
        double maxLimitedTextureY = getY(maxV);

//        System.out.println(x + " " + y + " " + minU + " " + minV + " " + maxU + " " + maxV);

        double limitedTextureWidth = maxLimitedTextureX - minLimitedTextureX;
        double limitedTextureHeight = maxLimitedTextureY - minLimitedTextureY;

        double normalizedLimitedTextureX = x % limitedTextureWidth;
        double normalizedLimitedTextureY = y % limitedTextureHeight;

        int normalizedTextureX = (int) (minLimitedTextureX + normalizedLimitedTextureX);
        int normalizedTextureY = (int) (minLimitedTextureY + normalizedLimitedTextureY);

        return getColorOnNormalizedPosition(normalizedTextureX, normalizedTextureY);
    }

    public Color getColorOnUVPosition(double u, double v) {

        int textureX = (int) getX(u);
        int textureY = (int) getY(v);

        return getColorOnNormalizedPosition(textureX, textureY);
    }

    public double getX(double u) throws IllegalArgumentException{

        if(u < 0 || u > 1){
            throw new IllegalArgumentException("Texture u should be inside <0, 1>, but was: " + u);
        }

        return u * getWidth();
    }

    public double getY(double v){

        if(v < 0 || v > 1){
            throw new IllegalArgumentException("Texture v should be inside <0, 1>, but was: " + v);
        }

        return v * getHeight();
    }

    private Color getColorOnNormalizedPosition(int x, int y) throws IllegalArgumentException{

        if(x < 0 || y < 0 || x >= width || y >= height){
            throw new IllegalArgumentException("Invalid texture x, y: " + x + ", " + y + " but size is: " + width + ", " + height);
        }

        x += shiftX;
        y += shiftY;

        int pixel = pixels[y * imgWidth + x];

        return new Color(pixel);
    }

    public Color getColorOnRawPosition(double x, double y) throws IllegalArgumentException {

        int textureX = (int) normalizeX(x);
        int textureY = (int) normalizeY(y);

        return getColorOnNormalizedPosition(textureX, textureY);
    }

    public double normalizeX(double x){

        return x % getWidth();
    }

    public double normalizeY(double y){

        return y % getHeight();
    }

    public double getU(double x){

        return normalizeX(x) / getWidth();
    }

    public double getV(double y){

        return normalizeY(y) / getHeight();
    }
}
