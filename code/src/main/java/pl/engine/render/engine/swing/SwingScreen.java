package pl.engine.render.engine.swing;

import pl.engine.math.Vector3;
import pl.engine.render.engine.Screen;
import pl.engine.shapes.flat.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SwingScreen extends JPanel implements Screen {

    private final BufferedImage content;
    private Rect clearRect;

    public SwingScreen(){

        Dimension screenSize = getScreenSize();

        setPreferredSize(screenSize);

        content = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void init(){

        Dimension screenSize = getScreenSize();

        clearRect = new Rect(
            Vector3.of(0, 0, 0),
            Vector3.of(screenSize.width, screenSize.height, 0),
            Color.black,
            true
        );
    }

    @Override
    public void clearContent(){

        clearRect.draw(this::drawPixel);
    }

    @Override
    public void drawPixel(double x, double y, double z, Color color){

        try{
            content.setRGB((int) x, (int) y, color.getRGB());
        }
        catch (RuntimeException e){

        }
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
