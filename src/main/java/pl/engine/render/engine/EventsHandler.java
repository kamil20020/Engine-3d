package pl.engine.render.engine;

import pl.engine.render.Camera;

import java.awt.event.KeyEvent;

public abstract class EventsHandler {

    protected final Camera camera;

    public EventsHandler(Camera camera){

        this.camera = camera;
    }

    public final void keyPressed(int keyCode) {

    }

    public final void keyTyped(int keyCode) {

        camera.handleCamera(keyCode);
    }

    public final void keyReleased(int keyCode) {

    }
}
