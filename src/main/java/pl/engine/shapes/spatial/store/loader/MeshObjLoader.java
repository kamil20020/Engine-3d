package pl.engine.shapes.spatial.store.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.engine.exceptions.FileLoadException;
import pl.engine.exceptions.FileLocationException;
import pl.engine.math.Vector3;
import pl.engine.render.Vertex;
import pl.engine.shapes.spatial.Mesh;
import pl.engine.shapes.spatial.store.MeshFormatType;
import pl.engine.texture.TextureVertex;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

public class MeshObjLoader implements MeshLoader{

    private LinkedList<Vector3> verticesPositions = new LinkedList<>();
    private List<TextureVertex> textureVertices = new LinkedList<>();
    private List<Vertex> vertices = new LinkedList<>();
    private int offset;
    private int scale;

    private static final Logger log = LoggerFactory.getLogger(MeshObjLoader.class);

    @Override
    public Mesh load(String meshFilePath, Color color, boolean isFilled, int offset, int scale) throws FileLocationException, FileLoadException {

        this.offset = offset;
        this.scale = scale;

        log.debug("Started loading mesh from obj format for " + meshFilePath);

        Stream<String> linesStream;

        try {
            linesStream = readLinesFormObjFile(meshFilePath);
        }
        catch(IllegalArgumentException | URISyntaxException e){
            throw new FileLocationException(meshFilePath, e.getMessage());
        }
        catch(IOException e){
            throw new FileLoadException(meshFilePath, e.getMessage());
        }

        log.debug("Read data from obj file");

        linesStream
            .forEach(line -> {

                String[] words = line.trim().split("\\s");

                if(words.length == 0) {
                    return;
                }

                handleLoadedLine(line);
            });

        log.debug("Finished loading obj");

        return new Mesh(
            verticesPositions.toArray(new Vector3[0]),
            textureVertices.toArray(new TextureVertex[0]),
            vertices.toArray(new Vertex[0]),
            color,
            isFilled
        );
    }

    private static Stream<String> readLinesFormObjFile(String meshPath) throws IllegalArgumentException, URISyntaxException, IOException{

        if(!meshPath.endsWith("." + MeshFormatType.OBJ.getExtension())){
            throw new IllegalArgumentException("Invalid file extension, should be ." + MeshFormatType.OBJ.getExtension());
        }

        URL meshURL = Mesh.class.getClassLoader().getResource(meshPath);
        Path path = Path.of(meshURL.toURI());

        return Files.lines(path);
    }

    private void handleLoadedLine(String line){

        String[] words = line.trim().split("\\s");

        if(words.length == 0) {
            return;
        }

        String header = words[0];

        if(header.length() == 0){
            return;
        }

        switch (header){

            case "v":
                handleLoadedVertex(words);
                break;

            case "f":
                handleLoadedTriangle(words);
                break;

            case "vt":
                handleLoadedTextureVertex(words);
                break;
        }
    }

    private void handleLoadedVertex(String[] words) throws FileLoadException{

        if(words.length != 4){
            throw new FileLoadException("Invalid vertex coords length");
        }

        double x = Double.parseDouble(words[1]) * scale + offset;
        double y = Double.parseDouble(words[2]) * scale + offset;
        double z = Double.parseDouble(words[3]) * scale + offset;

        verticesPositions.add(Vector3.of(x, y, z));
    }

    private void handleLoadedTextureVertex(String[] words){

        if(words.length < 3 || words.length > 4){
            throw new FileLoadException("Invalid texture vertex coords length");
        }

        double u = Double.parseDouble(words[1]);
        double v = Double.parseDouble(words[2]);

        textureVertices.add(
            new TextureVertex(u, v)
        );
    }

    private void handleLoadedTriangle(String[] words) throws FileLoadException{

        if(words.length < 4 || words.length > 5){
            throw new FileLoadException("Invalid triangle coords length");
        }

        if(words[1].contains("/")){
            handleLoadedTriangleComplexVertices(words);
            return;
        }

        int a = Integer.parseInt(words[1]) - 1;
        int b = Integer.parseInt(words[2]) - 1;
        int c = Integer.parseInt(words[3]) - 1;

        vertices.add(Vertex.of(a));
        vertices.add(Vertex.of(b));
        vertices.add(Vertex.of(c));
    }

    private void handleLoadedTriangleComplexVertices(String[] words){

        for(int i=1; i < words.length; i++){

            String word = words[i];

            handleLoadedTriangleComplexVertex(word);
        }

        if(words.length == 5){

            handleFourVerticesShape();
        }
    }

    private void handleLoadedTriangleComplexVertex(String word){

        String[] vertexInfo = word.split("/");

        String vertexIndexStr = vertexInfo[0];
        String textureVertexIndexStr = vertexInfo[1];

        int vertexIndex = Integer.parseInt(vertexIndexStr) - 1;
        int textureVertexIndex = -1;

        if(!textureVertexIndexStr.isBlank()){

            textureVertexIndex = Integer.parseInt(textureVertexIndexStr) - 1;
        }

        vertices.add(Vertex.of(vertexIndex, textureVertexIndex));
    }

    private void handleFourVerticesShape(){

        ListIterator<Vertex> trianglesLastElementListIterator = vertices.listIterator(vertices.size());

        Vertex lastVertex = trianglesLastElementListIterator.previous();
        Vertex thirdVertex = trianglesLastElementListIterator.previous();
        Vertex secondVertex = trianglesLastElementListIterator.previous();
        Vertex firstVertex = trianglesLastElementListIterator.previous();

        vertices.add(new Vertex(thirdVertex.positionIndex, thirdVertex.textureVertexIndex));
        vertices.add(new Vertex(firstVertex.positionIndex, firstVertex.textureVertexIndex));
    }
}
