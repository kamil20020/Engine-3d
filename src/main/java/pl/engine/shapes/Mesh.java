package pl.engine.shapes;

import pl.engine.Triangleable;
import pl.engine.exceptions.FileLoadException;
import pl.engine.exceptions.FileLocationException;
import pl.engine.math.Vector3;
import pl.engine.shapes.flat.Triangle;
import pl.engine.texture.Texture;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Mesh extends Triangleable {

    public Mesh(Vector3[] v, Integer[] triangles, Color color, boolean isFilled){
        super(v, triangles, color, isFilled);
    }

    public static Mesh loadFromObjFile(String meshPath, Color color, boolean isFilled, int offset) throws FileLocationException, FileLoadException {

        Stream<String> linesStream;

        try {
            linesStream = readLinesFormObjFile(meshPath);
        }
        catch(IllegalArgumentException | URISyntaxException e){
            throw new FileLocationException(e.getMessage());
        }
        catch(IOException e){
            throw new FileLoadException(e.getMessage());
        }

        List<Vector3> vertices = new LinkedList<>();
        List<Integer> triangles = new LinkedList<>();

        linesStream
        .forEach(line -> {

            String[] words = line.trim().split("\\s");

            if(words.length == 0) {
                return;
            }

            handleLoadedLine(line, vertices, triangles, offset);
        });

        return new Mesh(
            vertices.toArray(new Vector3[0]),
            triangles.toArray(new Integer[0]),
            color,
            isFilled
        );
    }

    private static Stream<String> readLinesFormObjFile(String meshPath) throws IllegalArgumentException, URISyntaxException, IOException{

        if(!meshPath.endsWith(".obj")){
            throw new IllegalArgumentException("Invalid file extension, should be .obj");
        }

        URL meshURL = Mesh.class.getClassLoader().getResource(meshPath);
        Path path = Path.of(meshURL.toURI());

        return Files.lines(path);
    }

    private static void handleLoadedLine(String line, List<Vector3> vertices, List<Integer> triangles, int offset){

        String[] words = line.trim().split("\\s");

        if(words.length == 0) {
            return;
        }

        String header = words[0];

        if(header.length() == 0){
            return;
        }

        switch (header.charAt(0)){

            case 'v':
                handleLoadedVertex(words, vertices, offset);
                break;

            case 'f':
                handleLoadedTriangle(words, triangles);
                break;
        }
    }

    private static void handleLoadedVertex(String[] words, List<Vector3> vertices, int offset) throws FileLoadException{

        if(words.length != 4){
            throw new FileLoadException("Invalid vertex coords length");
        }

        double x = Double.parseDouble(words[1]) + offset;
        double y = Double.parseDouble(words[2]) + offset;
        double z = Double.parseDouble(words[3]) + offset;

        vertices.add(Vector3.of(x, y, z));
    }

    private static void handleLoadedTriangle(String[] words, List<Integer> triangles) throws FileLoadException{

        if(words.length != 4){
            throw new FileLoadException("Invalid triangle coords length");
        }

        int a = Integer.parseInt(words[1]) - 1;
        int b = Integer.parseInt(words[2]) - 1;
        int c = Integer.parseInt(words[3]) - 1;

        triangles.add(a);
        triangles.add(b);
        triangles.add(c);
    }
}
