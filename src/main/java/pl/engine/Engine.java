package pl.engine;

import pl.engine.math.Vector3;
import pl.engine.render.*;
import pl.engine.render.screen.Screen;
import pl.engine.render.window.OpenGLWindow;
import pl.engine.render.window.SwingWindow;
import pl.engine.render.window.Window;

import static java.lang.Thread.sleep;

public class Engine {

    private static final int FPS = 60;

    private final Window window;
    private final Screen screen;
    private final Camera camera;
    private final Renderer renderer;
    private final EventsHandler eventsHandler;

    public Engine(){

        camera = new Camera(
            Vector3.of(0, 0, -10), //Vector3.of(-500, -500, 10),
            Vector3.of(0, 0 , 0)
        );

        eventsHandler = new EventsHandler(camera);

        window = new OpenGLWindow(eventsHandler);
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
