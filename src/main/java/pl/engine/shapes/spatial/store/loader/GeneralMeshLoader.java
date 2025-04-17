package pl.engine.shapes.spatial.store.loader;

import pl.engine.exceptions.MeshFormatWasNotFoundException;
import pl.engine.shapes.spatial.Mesh;
import pl.engine.shapes.spatial.store.MeshFormatType;

import java.awt.*;

public class GeneralMeshLoader implements MeshLoader{

    @Override
    public Mesh load(String path, Color color, boolean isFilled, int offset) {

        if(path.endsWith(MeshFormatType.OBJ.getExtension())){

            return new MeshObjLoader().load(path, color, isFilled, offset);
        }

        throw new MeshFormatWasNotFoundException(path, true);
    }
}
