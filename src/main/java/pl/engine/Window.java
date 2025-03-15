package pl.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Window extends JFrame {

    public Window() {
        super();

        setTitle("3d Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(MAXIMIZED_BOTH);

        Screen screen = new Screen();
        add(screen);

        setVisible(true);
    }
}
