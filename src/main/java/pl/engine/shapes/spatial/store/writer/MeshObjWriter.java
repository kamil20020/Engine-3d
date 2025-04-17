package pl.engine.shapes.spatial.store.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.engine.exceptions.FileCreateException;
import pl.engine.exceptions.FileExistsException;
import pl.engine.exceptions.FileWriteException;
import pl.engine.math.Vector3;
import pl.engine.shapes.spatial.Mesh;
import pl.engine.shapes.spatial.store.MeshFormatType;
import pl.engine.texture.Texturable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;

public class MeshObjWriter implements MeshWriter{

    private static final DecimalFormat vertexDecimalFormat;

    private static final Logger log = LoggerFactory.getLogger(MeshObjWriter.class);

    static {

        DecimalFormatSymbols vertexDotDecimalFormat = new DecimalFormatSymbols(Locale.GERMAN);
        vertexDotDecimalFormat.setDecimalSeparator('.');

        vertexDecimalFormat = new DecimalFormat("###0.000000", vertexDotDecimalFormat);
    }

    @Override
    public void write(String path, Mesh mesh) {

        log.debug("Started writing mesh to obj for " + path);

        Optional<File> createdFileOpt;

        try{
            createdFileOpt = createFile(path);
        }
        catch(IOException e){
            throw new FileCreateException(path);
        }

        if(createdFileOpt.isEmpty()){
            throw new FileCreateException(path);
        }

        log.debug("Created file for mesh obj write");

        File createdFile = createdFileOpt.get();

        try(
            BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(createdFile)
            );
        ){

            saveVertices(bufferedWriter, mesh.getVertices());
            saveTriangles(bufferedWriter, mesh.getTriangles());
        }
        catch (IOException e){
            throw new FileWriteException(path, e.getMessage());
        }

        log.debug("Finished writing mesh to obj file");
    }

    private static Optional<File> createFile(String path) throws IOException{

        String extension = MeshFormatType.OBJ.getExtension();

        if(!path.endsWith("." + extension)){

            path = path + "." + extension;
        }

        File file = new File(path);

        if(file.exists()){
            throw new FileExistsException(path);
        }

        boolean wasFileCreated = file.createNewFile();

        if(!wasFileCreated){
            return Optional.empty();
        }

        return Optional.of(file);
    }

    private static void saveVertices(BufferedWriter bufferedWriter, Vector3[] vertices) throws IOException {

        for(int i=0; i < vertices.length; i++){

            Vector3 vertex = vertices[i];
            String vertexStr = vertexToObjFormat(vertex);

            bufferedWriter.write(vertexStr);
            bufferedWriter.newLine();
        }

        log.debug("Saved mesh vertices to obj file");
    }

    private static void saveTriangles(BufferedWriter bufferedWriter, Integer[] triangles) throws IOException {

        for(int i=0; i <= triangles.length - 3; i += 3){

            Integer a = triangles[i] + 1;
            Integer b = triangles[i + 1] + 1;
            Integer c = triangles[i + 2] + 1;

            String trianglesStr = triangleToObjFormat(a, b, c);

            bufferedWriter.write(trianglesStr);
            bufferedWriter.newLine();
        }

        log.debug("Saved mesh triangles to obj file");
    }

    private static String vertexToObjFormat(Vector3 vertex){

        return "v " + vertexDecimalFormat.format(vertex.x) + " " + vertexDecimalFormat.format(vertex.y) + " " + vertexDecimalFormat.format(vertex.z);
    }

    private static String triangleToObjFormat(Integer a, Integer b, Integer c){

        return "f " + a + " " + b + " " + c;
    }
}
