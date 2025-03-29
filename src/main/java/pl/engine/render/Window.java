package pl.engine.render;

import pl.engine.EventsHandler;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame {

    private Screen screen;

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
