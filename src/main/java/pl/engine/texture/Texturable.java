package pl.engine.texture;

import pl.engine.general.QuadConsumer;
import pl.engine.shapes.Drawable;
import pl.engine.math.Vector3;

import java.awt.*;
import java.util.function.BiConsumer;

public abstract class Texturable extends Drawable {

    protected Texture texture;
    protected double minXYU, minXYV;
    protected double maxXYU, maxXYV;

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

    public QuadConsumer<Double, Double, Double, Color> getDrawFunctionWithTexture(
        QuadConsumer<Double, Double, Double, Color> basicDrawFunction
    ){

        return (Double x, Double y, Double z, Color color) -> {

            Color textureColor;

            try{
                textureColor = texture.getColorFromLimitedTexture(x, y, minXYU, minXYV, maxXYU, maxXYV);
            }
            catch(IllegalArgumentException e){
                LOGGER.log(System.Logger.Level.DEBUG, e.getMessage());

                return;
            }

            basicDrawFunction.accept(x, y, z, textureColor);
        };
    }
}
