package pl.engine.texture;

import pl.engine.shapes.Drawable;
import pl.engine.math.Vector3;

import java.awt.*;
import java.util.function.BiConsumer;

public abstract class Texturable extends Drawable {

    protected Texture texture;

    public Texturable(Color color){
        super(color);
    }

    protected abstract Vector3 getMinXY();

    private int normalizeX(double x){

        double diff = x - getMinXY().x;

        return (int) diff % texture.getWidth();
    }

    private  int normalizeY(double y){

        double diff = y - getMinXY().y;

        return (int) diff % texture.getHeight();
    }

//    public BiConsumer<Double, Double> getDrawTextureOrColorPixelFunction(){
//
//        if(texture == null){
//
//            return (x, y) -> {
//
//                drawPixel(x, y, color);
//            };
//        }
//
//        return (Double x, Double y) -> drawPixel(x, y, color);
//    }
//
//    public void drawTexturePixel(Texture texture, double x, double y){
//
//        int textureX = normalizeX(x);
//        int textureY = normalizeY(y);
//
//        Color textureColor = texture.getColorOnPosition(textureX, textureY);
//
//        drawPixel(x, y, textureColor);
//    }
}
