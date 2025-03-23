package pl.engine;

import pl.engine.shapes.flat.*;
import pl.engine.texture.GridTexture;
import pl.engine.texture.Texture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen extends JPanel {

    private final BufferedImage content;

    private Screen(){

        Dimension screenSize = getScreenSize();

        System.out.println(screenSize.width + " x " + screenSize.height);

        setPreferredSize(screenSize);

        content =  new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
    }

    public void initDraw(){

        Line line = new Line(
                Vector3.of(100, 100, 0),
                Vector3.of(200, 200, 0),
                Color.green,
                2
        );

        line.draw();

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

        line.draw();
        line1.draw();
        line2.draw();
        line3.draw();

        Triangle triangle = new Triangle(
            Vector3.of(800, 800, 0),
            Vector3.of(900, 900, 0),
            Vector3.of(1000, 700, 0),
            Color.green,
            true
        );

        triangle.draw();

        Triangle triangle1 = new Triangle(
            Vector3.of(800, 800, 0),
            Vector3.of(900, 900, 0),
            Vector3.of(1000, 700, 0),
            Color.orange,
            false
        );

        triangle1.draw();

        Disk disk = new Disk(Vector3.of(400, 400, 0), 100, Color.green);
        disk.draw();

        Circle circle = new Circle(Vector3.of(600, 600, 0), 100, Color.gray);
        circle.draw();

        Square square = new Square(Vector3.of(600, 600, 0), 100, Color.magenta);
        square.draw();

        Square square1 = new Square(Vector3.of(700, 700, 0), 160, Color.pink, true);
        square1.draw();

        Rect rect = new Rect(Vector3.of(800, 800, 0), 200, 160, Color.orange, true);
        rect.draw();

        Texture texture = Texture.of("/textures/grass-grid.png");
        GridTexture gridTexture = new GridTexture("/textures/grass-grid.png", 88, 88, 5, 7);
        GridTexture gridTexture1 = new GridTexture("/textures/grass-grid1.png", 128, 128, 4, 6);
        Texture tileTexture = gridTexture.getTile(1, 4);
        Texture tileTexture1 = gridTexture1.getTile(1, 1);

        Square texturedSquare = new Square(Vector3.of(0, 0, 0), 1000, tileTexture);
        texturedSquare.draw();

//        Disk texturedDisk = new Disk(Vector3.of(500, 500, 0), 200, texture);
//        texturedDisk.draw();

//        Rect texturedRect = new Rect(Vector3.of(0, 0, 0), 88, 88, tileTexture1);
//        texturedRect.draw();
    }

    public static Screen getInstance(){

        return Holder.INSTANCE;
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

    private static class Holder{

        private static final Screen INSTANCE = new Screen();
    }
}
