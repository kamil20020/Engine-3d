package pl.engine.texture;

public class GridTexture {

    private Texture texture;
    private int tileWidth;
    private int tileHeight;
    private int gridRows;
    private int gridCols;

    public GridTexture(String path, int tileWidth, int tileHeight, int gridRows, int gridCols) throws IllegalArgumentException{

        texture = new Texture(path);

        if(tileWidth <= 0 || tileWidth * gridCols > texture.getWidth()){

            throw new IllegalArgumentException("Invalid tile width");
        }

        if(tileHeight <= 0 || tileHeight * gridRows > texture.getHeight()){

            throw new IllegalArgumentException("Invalid tile width");
        }

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.gridRows = gridRows;
        this.gridCols = gridCols;
    }

    public void exampleTextures(){

        GridTexture gridTexture = new GridTexture("/textures/grass-grid.png", 88, 88, 5, 7);
        GridTexture gridTexture1 = new GridTexture("/textures/grass-grid1.png", 128, 128, 4, 6);
    }

    public Texture getTile(int tileGridRow, int tileGridCol) throws IllegalArgumentException{

        if(tileGridRow <= 0 || tileGridRow > gridRows){

            throw new IllegalArgumentException("Invalid grid texture row:" + tileGridRow);
        }

        if(tileGridCol <= 0 || tileGridCol > gridCols){

            throw new IllegalArgumentException("Invalid grid texture col:" + tileGridRow);
        }

        int shiftX = (tileGridCol - 1) * tileWidth;
        int shiftY = (tileGridRow - 1) * tileHeight;

        return new Texture(texture.getPixels(), texture.getWidth(), texture.getHeight(), tileWidth, tileHeight, shiftX, shiftY);
    }
}
