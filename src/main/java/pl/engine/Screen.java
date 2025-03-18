package pl.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen extends JPanel {

    private final BufferedImage content;

    public Screen(){

        Dimension screenSize = getScreenSize();

        System.out.println(screenSize.width + " x " + screenSize.height);

        setPreferredSize(screenSize);

        content =  new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);

        Line line = new Line(
            Vector3.of(100, 100, 0),
            Vector3.of(200, 200, 0),
            Color.green,
            2
        );

        Line line1 = new Line(
            Vector3.of(300, 400, 0),
            Vector3.of(400, 300, 0),
            Color.red,
            2
        );

        Line line2 = new Line(
            Vector3.of(500, 500, 0),
            Vector3.of(600, 500, 0),
            Color.blue,
            2
        );

        Line line3 = new Line(
            Vector3.of(700, 700, 0),
            Vector3.of(700, 800, 0),
            Color.orange,
            2
        );

        line.draw(this);
        line1.draw(this);
        line2.draw(this);
        line3.draw(this);

        Triangle triangle = new Triangle(
            Vector3.of(800, 800, 0),
            Vector3.of(900, 900, 0),
            Vector3.of(1000, 700, 0),
            Color.green,
            true
        );

        triangle.draw(this);

        Triangle triangle1 = new Triangle(
            Vector3.of(800, 800, 0),
            Vector3.of(900, 900, 0),
            Vector3.of(1000, 700, 0),
            Color.orange,
            false
        );

        triangle1.draw(this);
    }

    public void draw(int x, int y, Color color){

        content.setRGB(x, y, color.getRGB());
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
