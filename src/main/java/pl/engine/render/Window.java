package pl.engine.render;

import pl.engine.EventsHandler;

import javax.swing.*;

public class Window extends JFrame {

    private final Screen screen;

    public Window(EventsHandler eventsHandler) {
        super();

        setTitle("3d Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(MAXIMIZED_BOTH);
        addKeyListener(eventsHandler);

        screen = Screen.getInstance();
        screen.init();
        add(screen);
    }

    public Screen getScreen(){

        return screen;
    }
}
