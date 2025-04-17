package pl.engine.shapes.spatial.store.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.engine.exceptions.MeshFormatWasNotFoundException;
import pl.engine.shapes.spatial.Mesh;
import pl.engine.shapes.spatial.store.MeshFormatType;
import pl.engine.texture.Texturable;

import java.awt.*;

public class GeneralMeshLoader implements MeshLoader{

    private static final Logger log = LoggerFactory.getLogger(GeneralMeshLoader.class);

    @Override
    public Mesh load(String path, Color color, boolean isFilled, int offset) {

        if(path.endsWith(MeshFormatType.OBJ.getExtension())){

            log.debug("Selected obj mesh loader for file " + path);

            return new MeshObjLoader().load(path, color, isFilled, offset);
        }

        throw new MeshFormatWasNotFoundException(path, true);
    }
}
