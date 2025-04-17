package pl.engine.render;

import pl.engine.math.Vector3;
import pl.engine.texture.TextureVertex;

public class Vertex {

    public Vector3 position;
    public TextureVertex textureVertex;

    public Vertex(Vector3 position, double u, double v) {

        this.position = position;

        textureVertex = new TextureVertex(u, v);
    }

    public Vertex(Vector3 position, TextureVertex textureVertex) {

        this.position = position;
        textureVertex = textureVertex;
    }

    public static Vertex of(Vector3 position, TextureVertex textureVertex) {

        return new Vertex(position, textureVertex);
    }

    public void setTextureVertex(TextureVertex textureVertex){

       if(textureVertex == null || this.textureVertex == null){
           this.textureVertex = TextureVertex.of(textureVertex);
       }
       else{
           this.textureVertex.setUV(textureVertex);
       }
    }
}
