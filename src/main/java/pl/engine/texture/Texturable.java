package pl.engine.texture;

import pl.engine.general.QuadConsumer;
import pl.engine.shapes.Drawable;

import java.awt.*;

public abstract class Texturable extends Drawable {

    protected Texture texture;
    protected double minXYU, minXYV;
    protected double maxXYU, maxXYV;
    protected QuadConsumer<Double, Double, Double, Color> basicDrawFunction;

    private static final System.Logger LOGGER = System.getLogger(Texturable.class.getName());

    public Texturable(Texture texture, Color color){
        super(color);
    }

    public Texturable(Color color){
        super(color);
    }

    public void setTexture(Texture texture){

        this.texture = texture;
    }

    public void drawWithTexture(Double x, Double y, Double z, Color color){

        Color textureColor;

        try{
            textureColor = texture.getColorFromLimitedTexture(x, y, minXYU, minXYV, maxXYU, maxXYV);

            basicDrawFunction.accept(x, y, z, textureColor);
        }
        catch(IllegalArgumentException e){
            LOGGER.log(System.Logger.Level.DEBUG, e.getMessage());
        }
    }
}
