package pl.engine.render.engine;

import pl.engine.render.engine.Screen;

public interface Window {

    Screen getScreen();
    boolean shouldClose();
    void setVisible();
}
