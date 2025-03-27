package pl.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Window extends JFrame implements KeyListener {

    public Window() {
        super();

        setTitle("3d Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(MAXIMIZED_BOTH);
        addKeyListener(this);

        Screen screen = Screen.getInstance();
        add(screen);

        screen.initDraw();
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {

        int pressedKeyCode = e.getKeyCode();

        switch (pressedKeyCode){

            case KeyEvent.VK_LEFT:
                System.out.println("Left");
                break;

            case KeyEvent.VK_RIGHT:
                System.out.println("Right");
                break;

            case KeyEvent.VK_UP:
                System.out.println("Top");
                break;

            case KeyEvent.VK_DOWN:
                System.out.println("Down");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
