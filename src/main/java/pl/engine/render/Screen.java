package pl.engine.render;

import pl.engine.math.Vector3;
import pl.engine.shapes.flat.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen extends JPanel {

    private final BufferedImage content;
    private Rect clearRect;

    private Renderer renderer;

    private Screen(){

        Dimension screenSize = getScreenSize();

        setPreferredSize(screenSize);

        content =  new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
    }

    public void setRenderer(Renderer renderer){

        this.renderer = renderer;
    }

    public void init(){

        Dimension screenSize = getScreenSize();

        clearRect = new Rect(
            Vector3.of(0, 0, 0),
            Vector3.of(screenSize.width - 1, screenSize.height - 1, 0),
            Color.black,
            true
        );
    }

    public void clearContent(){

        clearRect.draw();
    }

    public static Screen getInstance(){

        return Holder.INSTANCE;
    }

    public void draw(double x, double y, Color color){

        try{
            content.setRGB((int) x, (int) y, color.getRGB());
        }
        catch (RuntimeException e){

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        clearContent();

        renderer.draw();

        g.drawImage(content, 0, 0, null);
    }

    public static Dimension getScreenSize(){
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    private static class Holder{

        private static final Screen INSTANCE = new Screen();
    }
}
