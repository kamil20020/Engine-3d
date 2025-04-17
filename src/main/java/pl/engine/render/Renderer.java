package pl.engine.render;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.engine.Triangleable;
import pl.engine.general.QuadConsumer;
import pl.engine.general.TriConsumer;
import pl.engine.math.Vector3;
import pl.engine.shapes.Drawable;
import pl.engine.shapes.spatial.store.loader.GeneralMeshLoader;
import pl.engine.shapes.spatial.Mesh;
import pl.engine.shapes.flat.*;
import pl.engine.shapes.spatial.Cube;
import pl.engine.shapes.spatial.store.loader.MeshLoader;
import pl.engine.shapes.spatial.store.writer.GeneralMeshWriter;
import pl.engine.texture.Texturable;
import pl.engine.texture.Texture;
import pl.engine.texture.TextureVertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Renderer {

    private final List<Triangleable> triangeables = new ArrayList<>();
    private final List<Drawable> drawables = new ArrayList<>();
    private final Camera camera;
    private final Zbuffer zBuffer;
    private final QuadConsumer<Double, Double, Double, Color> drawFunction;
    private final Screen screen;
    private final MeshLoader meshLoader;

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
    }

    private void init(){

        log.debug("Started renderer init");

        Rect rect = new Rect(Vector3.of(0, 0, 100), Vector3.of(100, 100, 100), Color.orange, true);
        Rect recta = new Rect(Vector3.of(200, 200, 5), Vector3.of(100, 200, 5), Color.orange, true);
        Rect rectb = new Rect(Vector3.of(200, 200, 5), Vector3.of(100, 200, 5), Color.orange, true);
        Rect rectc = new Rect(Vector3.of(200, 200, 5), Vector3.of(100, 200, 5), Color.orange, true);
        Rect rect1 = new Rect(Vector3.of(500, 500, 5), Vector3.of(400, 500, 5), Color.orange, true);
        Rect rect2 = new Rect(Vector3.of(200, 200, 10), Vector3.of(300, 300, 10), Color.orange, false);
        Rect rect3 = new Rect(Vector3.of(200, 200, 15), Vector3.of(300, 300, 15), Color.orange, false);
        Rect rect4 = new Rect(Vector3.of(200, 200, 20), Vector3.of(300, 300, 20), Color.orange, false);
        Rect rect5 = new Rect(Vector3.of(200, 200, 25), Vector3.of(300, 300, 25), Color.orange, false);
        Rect rect6 = new Rect(Vector3.of(200, 200, 30), Vector3.of(300, 300, 30), Color.orange, false);
        Rect rect7 = new Rect(Vector3.of(200, 200, 50), Vector3.of(300, 300, 50), Color.orange, false);
        Triangle triangle = new Triangle(
            Vector3.of(600, 500, 10),
            Vector3.of(700, 500, 10),
            Vector3.of(700, 700, 10),
            Color.pink,
            true
        );

        Triangle triangle1 = new Triangle(
            Vector3.of(600, 600, 10),
            Vector3.of(700, 600, 10),
            Vector3.of(600, 600, 100),
            Color.pink,
            true
        );

        Disk disk = new Disk(
            Vector3.of(600, 600, 10),
            100,
            Color.green
        );

        Circle circle = new Circle(
            Vector3.of(700, 700, 10),
            100,
            Color.pink
        );

        Line line = new Line(
            Vector3.of(200, 200, 10),
            Vector3.of(300, 200, 20),
            Color.green
        );

        Line line1 = new Line(
            Vector3.of(100, 100, 10),
            Vector3.of(100, 100, 100),
            Color.green
        );

        Rect floor = new Rect(Vector3.of(-1000, -1000, -1000), Vector3.of(1000, 10, 1000), Color.green, true);

        Cube cube = new Cube(Vector3.of(0, 0, 0), 50, Color.green, false);
        Cube cube1 = new Cube(Vector3.of(600, 0, 0), 50, Color.orange, false);
        Cube cube2 = new Cube(Vector3.of(200, 200, 0), 50, Color.magenta, false);
        Cube cube3 = new Cube(Vector3.of(500, 500, 0), 50, Color.pink, false);
        Cube cube4 = new Cube(Vector3.of(0, 0, 0), 50, Color.gray, false);

        log.debug("Created meshes");

//        Texture towerTexture = Texture.of("./meshes/tower/texture.jpg");
//        Texture moonTexture = Texture.of("./meshes/moon/texture.png");
//        Texture houseTexture = Texture.of("./meshes/house/texture.png");
        Texture grassTexture = Texture.of("./meshes/grass-cube/texture.png");

        log.debug("Loaded textures");

//        Mesh ship = meshLoader.load("./meshes/space-ship.obj", Color.orange, false, -20, 1);
//        Mesh teapot = meshLoader.load("./meshes/teapot.obj", Color.orange, false, 0, 1);
//        Mesh axis = meshLoader.load("./meshes/axis.obj", Color.orange, false, 0, 1);
//        Mesh mountains = meshLoader.load("./meshes/mountains.obj", Color.orange, false, 150, 1);
//        Mesh cubeLoaded = meshLoader.load("./meshes/cube.obj", Color.orange, true, 50, 1);
//        Mesh house = meshLoader.load("./meshes/house/house.obj", Color.orange, true, 50, 1);
//        Mesh moon = meshLoader.load("./meshes/moon/moon.obj", Color.orange, false, 300, 100);
//        Mesh tower = meshLoader.load("./meshes/tower/tower.obj", Color.orange, false, 10, 1);
        Mesh grassCube = meshLoader.load("./meshes/grass-cube/texture-cube.obj", Color.orange, false, 10, 1);
//        tower.setTexture(towerTexture);
//        moon.setTexture(moonTexture);
//        grassCube.setTexture(grassTexture);

        log.debug("Loaded meshes");

//        new GeneralMeshWriter().write("./cube.obj", cube);

        triangeables.addAll(List.of(grassCube));

//        drawables.addAll(List.of(line));

        log.debug("Finished renderer init");
    }

    public void draw(){

        zBuffer.clear();
        screen.clearContent();

        Vertex[] toDrawVertices = new Vertex[]{
            Vertex.of(Vector3.empty(), TextureVertex.of(0, 0)),
            Vertex.of(Vector3.empty(), TextureVertex.of(0, 0)),
            Vertex.of(Vector3.empty(), TextureVertex.of(0, 0))
        };

        Vector3[] toDrawVerticesPositions = new Vector3[3];

        triangeables.forEach(toDraw -> {

            TriConsumer<Vertex[], Color, QuadConsumer<Double, Double, Double, Color>> triangleDrawFunction = toDraw.getTriangleDrawFunction();

            for(int i=0; i <= toDraw.getTriangles().length - 3; i += 3){

                for(int j=0; j < 3; j++){

                    Vertex toDrawVertex = toDraw.getVertexByTriangleIndex(i + j);

                    Vector3 positionTransformedByCamera = camera.transform(toDrawVertex.position);

                    toDrawVertex.position = positionTransformedByCamera;
                    toDrawVerticesPositions[j] = positionTransformedByCamera;
                }

                if(camera.isTriangleHidden(toDrawVerticesPositions)){
                    continue;
                }

                for(int j=0; j < 3; j++){

                    Vertex toDrawVertex = toDrawVertices[j];

                    Vector3 positionTransformedByPerspective = Perspective.transform(toDrawVertex.position);

                    toDrawVertex.position = positionTransformedByPerspective;
                }

                triangleDrawFunction.accept(
                    toDrawVertices,
                    toDraw.randomColors[i / 3],
                    drawFunction
                );
            }
        });

        drawables.forEach(toDraw -> {
            toDraw.draw(drawFunction);
        });

        screen.repaint();
    }
}
