package pl.engine.render.engine.swing;

import pl.engine.render.engine.EventsHandler;
import pl.engine.render.engine.Screen;
import pl.engine.render.engine.Window;

import javax.swing.*;
import java.awt.event.KeyListener;

public class SwingWindow extends JFrame implements Window {

    private final Screen screen;

    public SwingWindow(EventsHandler eventsHandler) {
        super();

        setTitle("3d Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(MAXIMIZED_BOTH);
        addKeyListener((KeyListener) eventsHandler);

        screen = new SwingScreen();
        screen.init();
        add((SwingScreen) screen);
    }

    @Override
    public void setVisible(){

        setVisible(true);
    }

    @Override
    public Screen getScreen(){

        return screen;
    }

    @Override
    public boolean shouldClose() {
        return !isDisplayable();
    }
}
