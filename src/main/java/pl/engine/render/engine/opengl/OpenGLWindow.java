package pl.engine.render.engine.opengl;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.engine.render.engine.EventsHandler;
import pl.engine.render.engine.Window;
import pl.engine.render.engine.Screen;

import java.awt.event.KeyEvent;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class OpenGLWindow implements Window {

    private final OpenGLScreen screen;

    private long window;

    private static final Logger log = LoggerFactory.getLogger(OpenGLWindow.class);

    public OpenGLWindow(EventsHandler eventsHandler) {
        super();

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Could not init GLFW");
        }

        log.debug("Initialized OpenGL");

        // window settings

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        window = glfwCreateWindow(videoMode.width(), videoMode.height(), "BufferedImage to OpenGL", glfwGetPrimaryMonitor(), 0);

        if (window == NULL) {
            throw new RuntimeException("Nie udało się stworzyć okna GLFW");
        }

        log.debug("Created window");

        // init OpenGL
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {

            if(key == GLFW_KEY_UP){
                key = 38;
            }
            else if(key == GLFW_KEY_DOWN){
                key = 40;
            }
            else if(key == GLFW_KEY_LEFT){
                key = 37;
            }
            else if(key == GLFW_KEY_RIGHT){
                key = 39;
            }

            if(action == GLFW_PRESS){
                eventsHandler.keyPressed(key);
            }
            else if(action == GLFW_REPEAT){
                eventsHandler.keyTyped(key);
            }
            else if(action == GLFW_RELEASE){
                eventsHandler.keyTyped(key);
            }
        });

        try(MemoryStack stack = stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(window, pWidth, pHeight);

//            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

//            GLFW.glfwSetWindowPos(window,(vidmode.width() - pWidth.get(0)) / 2,(vidmode.height() - pHeight.get(0)) / 2);

            GLFW.glfwMakeContextCurrent(window);
            GLFW.glfwSwapInterval(1); // V-Sync
            GLFW.glfwShowWindow(window);
        }

        GL.createCapabilities();

        log.debug("Initialized OpenGL");

        screen = new OpenGLScreen();
        screen.init();
        screen.setWindow(window);

        log.debug("Initialized window");
    }

    @Override
    public void finalize(){

        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    @Override
    public boolean shouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    @Override
    public void setVisible() {

    }

    @Override
    public Screen getScreen(){

        return screen;
    }
}
