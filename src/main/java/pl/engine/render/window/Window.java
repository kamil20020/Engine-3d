package pl.engine.render.window;

import pl.engine.render.screen.Screen;

public interface Window {

    Screen getScreen();
    boolean shouldClose();
    void setVisible();
}
