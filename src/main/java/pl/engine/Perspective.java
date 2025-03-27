package pl.engine;

public class Perspective {

    private static final int fov = 210;
    private static final double near = 1;
    private static final double far = 100;

    private final static Matrix matrix;

    static {

        int[][] m = new int [4][4];

        m[0][0] = (int) near;
        m[1][1] = (int) near;
        m[2][2] = (int) (near + far);
        m[2][3] = (int) (-near * far);
        m[3][2] = 1;

        matrix = new Matrix(m);
    }

    public static Vector3 transform(Vector3 point){

        return point.multiply(matrix);
    }
}
