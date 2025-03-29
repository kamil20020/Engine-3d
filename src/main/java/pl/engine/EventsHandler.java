package pl.engine;

import pl.engine.render.Camera;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EventsHandler implements KeyListener {

    private Camera camera;

    public EventsHandler(Camera camera){

        this.camera = camera;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        camera.handleCamera(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
