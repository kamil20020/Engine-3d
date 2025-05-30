package pl.engine.texture;

public class TextureVertex {

    public double u, v, w;

    public TextureVertex(double u, double v, double w){

        this.u = u;
        this.v = v;
        this.w = w;
    }

    public TextureVertex(double u, double v){

        this.u = u;
        this.v = v;
    }

    public static TextureVertex of(double u, double v){

        return new TextureVertex(u, v);
    }

    public static TextureVertex of(TextureVertex textureVertex){

        if(textureVertex == null){
            return null;
        }

        return new TextureVertex(textureVertex.u, textureVertex.v, textureVertex.w);
    }

    public void setUV(TextureVertex textureVertex){

        u = textureVertex.u;
        v = textureVertex.v;
    }
}
