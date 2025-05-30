package pl.engine.render.engine.opengl;

import pl.engine.render.Camera;
import pl.engine.render.engine.EventsHandler;
import pl.engine.render.engine.swing.SwingEventsHandler;
import pl.engine.render.engine.RenderEngineFactory;
import pl.engine.render.engine.Window;

public class OpenGLRenderEngineFactory implements RenderEngineFactory {

    @Override
    public Window createWindow(EventsHandler eventsHandler) {

        return new OpenGLWindow(eventsHandler);
    }

    @Override
    public EventsHandler createEventsHandler(Camera camera) {

        return new OpenGLEventsHandler(camera);
    }
}
