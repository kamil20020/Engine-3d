package pl.engine.render.screen;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import pl.engine.render.Renderer;
import pl.engine.shapes.flat.Rect;

import java.awt.*;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class OpenGLScreen implements Screen{

    private ByteBuffer buffer;
    private long window;
    private int textureID;
    private int width;
    private int height;

    public OpenGLScreen(){

        Dimension screenDimension = getScreenSize();

        width = screenDimension.width;
        height = screenDimension.height;
    }

    public void setWindow(long window){

        this.window = window;
    }

    @Override
    public void init() {

        buffer = ByteBuffer.allocateDirect(3 * width * height);

        buffer.flip();

        // Create texture in OpenGL
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    @Override
    public void repaint() {

        buffer.flip();

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, width, height, GL_RGB, GL_UNSIGNED_BYTE, buffer);

        glEnable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0); glVertex2f(-1, -1);
        glTexCoord2f(1, 0); glVertex2f( 1, -1);
        glTexCoord2f(1, 1); glVertex2f( 1,  1);
        glTexCoord2f(0, 1); glVertex2f(-1,  1);
        glEnd();
        glDisable(GL_TEXTURE_2D);

        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    @Override
    public void clearContent() {

        buffer.clear();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void drawPixel(double x, double y, double z, Color color){

        try{
            byte r = (byte) color.getRed(); // 255
            byte g = (byte) color.getGreen(); // 100
            byte b = (byte) color.getBlue(); // 50

            int index = (int) ((x + y * width) * 3);

            buffer.put(index, r);
            buffer.put(index + 1, g);
            buffer.put(index + 2, b);
        }
        catch (RuntimeException e){

        }
    }

    public static Dimension getScreenSize(){
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
