package pl.engine.shapes;

import pl.engine.general.QuadConsumer;

import java.awt.*;

public abstract class Drawable {

    protected Color color;

    public Drawable(Color color){

        this.color = color;
    }

    public Color getColor(){

        return color;
    }

    public abstract void draw(QuadConsumer<Double, Double, Double, Color> drawFunction);
}
