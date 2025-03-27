package pl.engine.texture;

import pl.engine.shapes.Drawable;
import pl.engine.Vector3;

import java.awt.*;
import java.util.function.BiConsumer;

public abstract class Texturable extends Drawable {

    protected Texture texture;

    protected abstract Vector3 getMinXY();

    private int normalizeX(int x){

        int diff = x - getMinXY().x;

        return diff % texture.getWidth();
    }

    private int normalizeY(int y){

        int diff = y - getMinXY().y;

        return diff % texture.getHeight();
    }

    public BiConsumer<Integer, Integer> getDrawTextureOrColorPixelFunction(){

        if(texture == null){

            return (x, y) -> {

                drawPixel(x, y, this.color);
            };
        }

        return this::drawTexturePixel;
    }

    public void drawTexturePixel(int x, int y){

        int textureX = normalizeX(x);
        int textureY = normalizeY(y);

        Color textureColor = texture.getColorOnPosition(textureX, textureY);

        drawPixel(x, y, textureColor);
    }
}
