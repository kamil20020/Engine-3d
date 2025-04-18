package pl.engine.render.screen;

import pl.engine.render.Renderer;

import java.awt.*;

public interface Screen {

    void init();
    void repaint();
    void clearContent();
    void drawPixel(double x, double y, double z, Color color);
}
