package pl.engine.shapes;

import pl.engine.Triangleable;
import pl.engine.math.Vector3;
import pl.engine.shapes.flat.Triangle;
import pl.engine.texture.Texture;

import java.awt.*;

public class Mesh extends Triangleable {

    public Mesh(Vector3[] v, Integer[] triangles, Color color, boolean isFilled){
        super(v, triangles, color, isFilled);
    }
}
