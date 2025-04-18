package pl.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glTexParameteri;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {

        new Engine().run();

//        GLFWErrorCallback.createPrint(System.err).set();
//
//        if(!GLFW.glfwInit()) {
//            throw new IllegalStateException("Unable to initialize GLFW");
//        }
//
//        GLFW.glfwDefaultWindowHints();
//        org.lwjgl.glfw.GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
//        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
//
//        long window = GLFW.glfwCreateWindow(640, 480, "LWJGL Bootcamp", NULL, NULL);
//        if(window == NULL) {
//            throw new IllegalStateException("Unable to create GLFW Window");
//        }
//
////        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {});
//
//        try(MemoryStack stack = stackPush()){
//            IntBuffer pWidth = stack.mallocInt(1);
//            IntBuffer pHeight = stack.mallocInt(1);
//
//            GLFW.glfwGetWindowSize(window, pWidth, pHeight);
//
////            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
//
////            GLFW.glfwSetWindowPos(window,(vidmode.width() - pWidth.get(0)) / 2,(vidmode.height() - pHeight.get(0)) / 2);
//
//            GLFW.glfwMakeContextCurrent(window);
//            GLFW.glfwSwapInterval(1);
//            GLFW.glfwShowWindow(window);
//        }
//
//        GL.createCapabilities();
//
//        while(!GLFW.glfwWindowShouldClose(window)) {
//            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
//
//            GLFW.glfwSwapBuffers(window);
//
//            GLFW.glfwPollEvents();
//        }
//
////        Callbacks.glfwFreeCallbacks(window);
//        GLFW.glfwDestroyWindow(window);
//
//        GLFW.glfwTerminate();
//        GLFW.glfwSetErrorCallback(null).free();
    }
}
