package pl.engine.render.engine.swing;

import pl.engine.render.Camera;
import pl.engine.render.engine.EventsHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingEventsHandler extends EventsHandler implements KeyListener{

    public SwingEventsHandler(Camera camera){

        super(camera);
    }

    @Override
    public void keyTyped(KeyEvent e) {

        super.keyTyped(e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {

        super.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

        super.keyReleased(e.getKeyCode());
    }
}
