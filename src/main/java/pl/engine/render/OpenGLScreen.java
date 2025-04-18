package pl.engine.render;

import org.lwjgl.opengl.GL;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.system.MemoryUtil.NULL;

public class OpenGLScreen {

    private long window;

    public void init() {

        // init GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Nie udało się zainicjować GLFW");
        }

        // window settings
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        window = glfwCreateWindow(800, 600, "BufferedImage to OpenGL", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Nie udało się stworzyć okna GLFW");
        }

        // init OpenGL
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // V-Sync
        glfwShowWindow(window);
        GL.createCapabilities();
    }

    public void render(BufferedImage image) {
        // Load BufferedImage as texture
        int textureID = loadTexture(image);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);

            // Use texture
            glBindTexture(GL_TEXTURE_2D, textureID);
            glBegin(GL_QUADS);
            glTexCoord2f(0, 0); glVertex2f(-1, -1);
            glTexCoord2f(1, 0); glVertex2f( 1, -1);
            glTexCoord2f(1, 1); glVertex2f( 1,  1);
            glTexCoord2f(0, 1); glVertex2f(-1,  1);
            glEnd();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private int loadTexture(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * image.getWidth() * image.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            int color = pixels[i];
            buffer.put((byte) ((color >> 16) & 0xFF)); // Red
            buffer.put((byte) ((color >> 8) & 0xFF));  // Green
            buffer.put((byte) ((color >> 0) & 0xFF));  // Blue
            buffer.put((byte) ((color >> 24) & 0xFF)); // Alpha
        }
        buffer.flip();

        // Create texture in OpenGL
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);

        return textureID;
    }

    public static void main(String[] args) {

        OpenGLScreen screen = new OpenGLScreen();
        screen.init();

        // Load image
        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, 256, 256);
        g2d.dispose();

        screen.render(img);
    }
}
