package pl.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen extends JPanel {

    private final BufferedImage content;

    public Screen(){

        Dimension screenSize = getScreenSize();

        setPreferredSize(screenSize);

        content =  new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(content, 0, 0, null);
    }

    public static Dimension getScreenSize(){
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
