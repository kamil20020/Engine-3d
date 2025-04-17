package pl.engine.shapes.spatial;

import pl.engine.Triangleable;
import pl.engine.exceptions.FileLoadException;
import pl.engine.exceptions.FileLocationException;
import pl.engine.math.Vector3;
import pl.engine.render.Vertex;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Mesh extends Triangleable{

    public Mesh(Vertex[] v, Integer[] triangles, Color color, boolean isFilled){
        super(v, triangles, color, isFilled);
    }
}
