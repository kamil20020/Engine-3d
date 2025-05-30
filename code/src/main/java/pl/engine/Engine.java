package pl.engine;

import pl.engine.math.Vector3;
import pl.engine.render.*;
import pl.engine.render.engine.RenderEngineFactory;
import pl.engine.render.engine.Screen;
import pl.engine.render.engine.opengl.OpenGLEventsHandler;
import pl.engine.render.engine.opengl.OpenGLRenderEngineFactory;
import pl.engine.render.engine.opengl.OpenGLWindow;
import pl.engine.render.engine.EventsHandler;
import pl.engine.render.engine.Window;

import static java.lang.Thread.sleep;

public class Engine {

    private static final int FPS = 60;

    private final Window window;
    private final Screen screen;
    private final Camera camera;
    private final Renderer renderer;
    private final EventsHandler eventsHandler;
    private final RenderEngineFactory renderEngineFactory;

    public Engine(){

        camera = new Camera(
            Vector3.of(0, 0, -50), //Vector3.of(-500, -500, 10),
            Vector3.of(0, 0 , 0)
        );

        renderEngineFactory = new OpenGLRenderEngineFactory();

        eventsHandler = renderEngineFactory.createEventsHandler(camera);

        window = renderEngineFactory.createWindow(eventsHandler);
        screen = window.getScreen();

        renderer = new Renderer(camera, screen);
    }

    public void run() {

        window.setVisible();

        while(!window.shouldClose()){

            try {
                renderer.draw();

                sleep(1000 / FPS);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
