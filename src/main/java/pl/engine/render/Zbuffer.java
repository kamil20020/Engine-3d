package pl.engine.render;

import pl.engine.math.Vector3;

import java.awt.*;

public class Zbuffer {

    public int[][] minVerticesZ;
    public int[][] frames;
    private static Zbuffer INSTANCE;
    private int frame = 0;

    private Zbuffer(){

        Dimension screenDimensions = Screen.getScreenSize();

        minVerticesZ = new int[screenDimensions.height][screenDimensions.width];
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

    public boolean update(int x, int y, int z){

        int zbufferWidth = minVerticesZ[0].length;
        int zbufferHeight = minVerticesZ.length;

        if(y < 0 || y >= zbufferHeight || x < 0 || x >= zbufferWidth){
            return false;
        }

        int oldZ = minVerticesZ[y][x];
        int pixelFrame = frames[y][x];

        if(pixelFrame < frame || z < oldZ){

            minVerticesZ[y][x] = z;
            frames[y][x] = frame;

            return true;
        }

        return false;
    }
}
