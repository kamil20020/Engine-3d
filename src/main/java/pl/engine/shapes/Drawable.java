package pl.engine.shapes;

import pl.engine.Screen;

import java.awt.*;

public abstract class Drawable {

    protected Color color;

    private static final Screen screen = Screen.getInstance();

    public void drawPixel(int x, int y, Color color){

        screen.draw(x, y, color);
    }

    public abstract void draw();
}
