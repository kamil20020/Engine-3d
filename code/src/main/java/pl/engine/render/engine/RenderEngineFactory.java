package pl.engine.render.engine;

import pl.engine.render.Camera;
import pl.engine.render.engine.swing.SwingEventsHandler;

public interface RenderEngineFactory {

    Window createWindow(EventsHandler eventsHandler);
    EventsHandler createEventsHandler(Camera camera);
}
