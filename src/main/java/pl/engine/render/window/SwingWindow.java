package pl.engine.render.window;

import pl.engine.EventsHandler;
import pl.engine.render.screen.Screen;
import pl.engine.render.screen.SwingScreen;

import javax.swing.*;

public class SwingWindow extends JFrame implements Window{

    private final Screen screen;

    public SwingWindow(EventsHandler eventsHandler) {
        super();

        setTitle("3d Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(MAXIMIZED_BOTH);
        addKeyListener(eventsHandler);

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
