package pl.engine.render;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.engine.Triangleable;
import pl.engine.general.QuadConsumer;
import pl.engine.math.Vector3;
import pl.engine.render.engine.Screen;
import pl.engine.shapes.Drawable;
import pl.engine.shapes.spatial.Cube;
import pl.engine.shapes.spatial.store.loader.GeneralMeshLoader;
import pl.engine.shapes.spatial.Mesh;
import pl.engine.shapes.spatial.store.loader.MeshLoader;
import pl.engine.texture.Texture;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private final Random random = new Random();
    private final Color[] randomColors;

    private Mesh grassCube;
    private Cube cube;

    private static final Logger log = LoggerFactory.getLogger(Renderer.class);

    public Renderer(Camera camera, Screen screen){

        this.camera = camera;
        this.screen = screen;
        this.zBuffer = Zbuffer.getInstance();
        meshLoader = new GeneralMeshLoader();
        this.drawFunction = (Double x, Double y, Double z, Color color) -> {

            if(zBuffer.update(x.intValue(), y.intValue(), z)){
                 screen.drawPixel(x, y, z, color);
            }
        };

        randomColors = new Color[10];

        for(int i=0; i < randomColors.length; i++){

            randomColors[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

        init();
    }

    private void init(){

        log.debug("Started renderer init");

        log.debug("Created meshes");

        Texture grassTexture = Texture.of("./meshes/grass-cube/texture.png");

        log.debug("Loaded textures");

        cube = new Cube(Vector3.of(0, 0, 0), 1, Color.gray, true);

        grassCube = meshLoader.load("./meshes/mountains.obj", Color.white, false, 100, 1);
//        grassCube.setTexture(grassTexture);

        log.debug("Loaded meshes");

        triangeables.addAll(List.of(grassCube));

        log.debug("Finished renderer init");
    }

    private void drawTriangleable(Triangleable toDraw, Vertex[] toDrawVertices,  Vector3[] toDrawVerticesPositions, Vector3 toMove){

        for(int i=0; i <= toDraw.getVertices().length - 3; i += 3){

            if(handleCameraForTriangle(toDraw, toDrawVertices, toDrawVerticesPositions, i, toMove)){
                continue;
            }

            handlePerspectiveForTriangle(toDrawVerticesPositions);

            toDraw.drawTriangle(
                toDrawVerticesPositions,
                toDrawVertices,
                toDraw.getColor(),
//                (toMove.x + toMove.y + toMove.z) % 2 == 0 ? Color.white : Color.green,
                drawFunction
            );
        }
    }

    private boolean handleCameraForTriangle(Triangleable toDraw, Vertex[] toDrawVertices,  Vector3[] toDrawVerticesPositions, int firstVertexIndex, Vector3 toMove){

        for(int j=0; j < 3; j++){

            Vertex toDrawVertex = toDraw.getVertexByIndex(firstVertexIndex + j);
            Vector3 toDrawVertexPosition = toDraw.getVertexPositionByVertex(toDrawVertex);

            Vector3 positionTransformedByCamera = camera.transform(toDrawVertexPosition);
            positionTransformedByCamera = positionTransformedByCamera.add(toMove);

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

        Vector3 toMove = Vector3.empty();

//        for(int z = 0; z < 16; z++){
//
//            toMove.z = z;
//
//            for(int y = 0; y < 16; y++){
//
//                toMove.y = y;
//
//                for(int x = 0; x < 16; x++){
//
//                    toMove.x = x;
//
//                    drawTriangleable(cube, toDrawVertices, toDrawVerticesPositions, toMove);
//                }
//            }
//        }
//
//        for(int z = 40; z < 50; z++){
//
//            toMove.z = z;
//
//            for(int y = 40; y < 50; y++){
//
//                toMove.y = y;
//
//                for(int x = 40; x < 50; x++){
//
//                    toMove.x = x;
//
//                    drawTriangleable(cube, toDrawVertices, toDrawVerticesPositions, toMove);
//                }
//            }
//        }

        triangeables.forEach(toDraw -> drawTriangleable(toDraw, toDrawVertices, toDrawVerticesPositions, toMove));

        drawables.forEach(toDraw -> {
            toDraw.draw(drawFunction);
        });

        screen.repaint();
    }
}
