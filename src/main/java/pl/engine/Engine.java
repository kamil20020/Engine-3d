package pl.engine;

import pl.engine.math.Vector3;
import pl.engine.render.Camera;
import pl.engine.render.Renderer;
import pl.engine.render.Screen;
import pl.engine.render.Window;

public class Engine extends Thread {

    private static final int FPS = 60;

    private final Window window;
    private final Screen screen;
    private final Camera camera;
    private final Renderer renderer;
    private final EventsHandler eventsHandler;

    public Engine(){

        camera = new Camera(
            Vector3.of(-500, -500, 0), //Vector3.of(-500, -500, 10),
            Vector3.of(0, 0, 0)
        );

        eventsHandler = new EventsHandler(camera);

        window = new Window(eventsHandler);
        screen = window.getScreen();

        renderer = new Renderer(camera);

        screen.setRenderer(renderer);
    }

    @Override
    public void run() {

        window.setVisible(true);

        while(true){

            try {
                screen.repaint();

                sleep(1000 / FPS);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
