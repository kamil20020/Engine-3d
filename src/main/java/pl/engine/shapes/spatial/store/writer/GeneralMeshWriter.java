package pl.engine.shapes.spatial.store.writer;

import pl.engine.exceptions.MeshFormatWasNotFoundException;
import pl.engine.shapes.spatial.Mesh;
import pl.engine.shapes.spatial.store.MeshFormatType;

public class GeneralMeshWriter implements MeshWriter{

    @Override
    public void write(String path, Mesh mesh) {

        if(path.endsWith(MeshFormatType.OBJ.getExtension())){
            new MeshObjWriter().write(path, mesh);
        }
        else{
            throw new MeshFormatWasNotFoundException(path, false);
        }
    }
}
