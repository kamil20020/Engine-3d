package pl.engine.render;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.engine.Triangleable;
import pl.engine.general.QuadConsumer;
import pl.engine.math.Vector3;
import pl.engine.shapes.Drawable;
import pl.engine.shapes.spatial.store.loader.GeneralMeshLoader;
import pl.engine.shapes.spatial.Mesh;
import pl.engine.shapes.spatial.store.loader.MeshLoader;
import pl.engine.texture.Texture;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Renderer {

    private final List<Triangleable> triangeables = new ArrayList<>();
    private final List<Drawable> drawables = new ArrayList<>();
    private final Camera camera;
    private final Zbuffer zBuffer;
    private final QuadConsumer<Double, Double, Double, Color> drawFunction;
    private final Screen screen;
    private final MeshLoader meshLoader;
    private final ExecutorService service = Executors.newFixedThreadPool(8);

    private Mesh grassCube;

    private static final Logger log = LoggerFactory.getLogger(Renderer.class);

    public Renderer(Camera camera){

        this.camera = camera;
        this.screen = Screen.getInstance();
        this.zBuffer = Zbuffer.getInstance();
        meshLoader = new GeneralMeshLoader();
        this.drawFunction = (Double x, Double y, Double z, Color color) -> {

            if(zBuffer.update(x.intValue(), y.intValue(), z)){
                 screen.draw(x, y, color);
            }
        };

        init();
        ExecutorService service = Executors.newFixedThreadPool(8);
    }

    private void init(){

        log.debug("Started renderer init");

        log.debug("Created meshes");

        Texture grassTexture = Texture.of("./meshes/grass-cube/texture.png");

        log.debug("Loaded textures");

        grassCube = meshLoader.load("./meshes/grass-cube/grass-cube.obj", Color.orange, false, 0, 1);
        grassCube.setTexture(grassTexture);

        log.debug("Loaded meshes");

        triangeables.addAll(List.of(grassCube));

        log.debug("Finished renderer init");
    }

    private void drawTrianlgeable(Triangleable toDraw, Vertex[] toDrawVertices,  Vector3[] toDrawVerticesPositions){

        for(int i=0; i <= toDraw.getVertices().length - 3; i += 3){

            if(handleCameraForTriangle(toDraw, toDrawVertices, toDrawVerticesPositions, i)){
                continue;
            }

            handlePerspectiveForTriangle(toDrawVerticesPositions);

            toDraw.drawTriangle(
                toDrawVerticesPositions,
                toDrawVertices,
                toDraw.randomColors[i / 3],
                drawFunction
            );
        }
    }

    private boolean handleCameraForTriangle(Triangleable toDraw, Vertex[] toDrawVertices,  Vector3[] toDrawVerticesPositions, int firstVertexIndex){

        for(int j=0; j < 3; j++){

            Vertex toDrawVertex = toDraw.getVertexByIndex(firstVertexIndex + j);
            Vector3 toDrawVertexPosition = toDraw.getVertexPositionByVertex(toDrawVertex);

            Vector3 positionTransformedByCamera = camera.transform(toDrawVertexPosition);

            toDrawVertices[j] = toDrawVertex;
            toDrawVerticesPositions[j] = positionTransformedByCamera;
        }

        return camera.isTriangleHidden(toDrawVerticesPositions);
    }

    private void handlePerspectiveForTriangle(Vector3[] toDrawVerticesPositions){

        for(int j=0; j < 3; j++){

            Vector3 toDrawVertexPosition = toDrawVerticesPositions[j];

            Vector3 positionTransformedByPerspective = Perspective.transform(toDrawVertexPosition);

            toDrawVerticesPositions[j] = positionTransformedByPerspective;
        }
    }

    public void draw(){

        zBuffer.clear();
        screen.clearContent();

        Vertex[] toDrawVertices = new Vertex[3];
        Vector3[] toDrawVerticesPositions = new Vector3[3];

        triangeables.forEach(toDraw -> drawTrianlgeable(toDraw, toDrawVertices, toDrawVerticesPositions));

        drawables.forEach(toDraw -> {
            toDraw.draw(drawFunction);
        });

        screen.repaint();
    }
}
