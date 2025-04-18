package pl.engine.render;

import pl.engine.render.screen.SwingScreen;

import java.awt.*;

public class Zbuffer {

    public double[][] minVerticesZ;
    public int[][] frames;
    private static Zbuffer INSTANCE;
    private int frame = 0;

    private Zbuffer(){

        Dimension screenDimensions = SwingScreen.getScreenSize();

        minVerticesZ = new double[screenDimensions.height][screenDimensions.width];
        frames = new int[screenDimensions.height][screenDimensions.width];
    }

    public static Zbuffer getInstance(){

        if(INSTANCE == null){
            INSTANCE = new Zbuffer();
        }

        return INSTANCE;
    }

    public void clear(){

        frame++;
    }

    public boolean update(int x, int y, double z){

        int zbufferWidth = minVerticesZ[0].length;
        int zbufferHeight = minVerticesZ.length;

        if(y < 0 || y >= zbufferHeight || x < 0 || x >= zbufferWidth){
            return false;
        }

        double oldZ = minVerticesZ[y][x];
        int pixelFrame = frames[y][x];

        if(pixelFrame < frame){

            minVerticesZ[y][x] = z;
            frames[y][x] = frame;

            return true;
        }
        else if(z < oldZ){

            minVerticesZ[y][x] = z;

            return true;
        }

        return false;
    }
}
