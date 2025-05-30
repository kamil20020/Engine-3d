package pl.engine.render.engine.swing;

import pl.engine.render.Camera;
import pl.engine.render.engine.EventsHandler;
import pl.engine.render.engine.RenderEngineFactory;
import pl.engine.render.engine.Window;

public class SwingRenderEngineFactory implements RenderEngineFactory {

    @Override
    public Window createWindow(EventsHandler eventsHandler) {

        return new SwingWindow(eventsHandler);
    }

    @Override
    public EventsHandler createEventsHandler(Camera camera) {

        return new SwingEventsHandler(camera);
    }
}
