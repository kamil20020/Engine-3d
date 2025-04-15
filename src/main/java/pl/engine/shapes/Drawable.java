package pl.engine.shapes;

import pl.engine.general.QuadConsumer;
import pl.engine.render.Screen;

import java.awt.*;

public abstract class Drawable {

    protected Color color;

    private static final Screen screen = Screen.getInstance();

    public Drawable(Color color){

        this.color = color;
    }

    protected static void drawPixel(double x, double y, Color color){

        screen.draw(x, y, color);
    }

    public Color getColor(){

        return color;
    }

    public final void draw(){

        draw((x, y, z, color) -> {
            Drawable.drawPixel(x, y, color);
        });
    }

    public abstract void draw(QuadConsumer<Double, Double, Double, Color> drawFunction);
}
