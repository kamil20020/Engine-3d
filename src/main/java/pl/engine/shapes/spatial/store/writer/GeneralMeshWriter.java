package pl.engine.shapes.spatial.store.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.engine.exceptions.MeshFormatWasNotFoundException;
import pl.engine.shapes.spatial.Mesh;
import pl.engine.shapes.spatial.store.MeshFormatType;
import pl.engine.texture.Texturable;

public class GeneralMeshWriter implements MeshWriter{

    private static final Logger log = LoggerFactory.getLogger(GeneralMeshWriter.class);

    @Override
    public void write(String path, Mesh mesh) {

        if(path.endsWith(MeshFormatType.OBJ.getExtension())){

            log.debug("Selected obj mesh writer for " + path);

            new MeshObjWriter().write(path, mesh);
        }
        else{
            throw new MeshFormatWasNotFoundException(path, false);
        }
    }
}
