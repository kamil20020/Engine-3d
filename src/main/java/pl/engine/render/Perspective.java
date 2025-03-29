package pl.engine.render;

import pl.engine.math.Matrix;
import pl.engine.math.Vector3;

public class Perspective {

    private static final double fov = 210;
    private static final double near = 1;
    private static final double far = 100;

    private final static Matrix matrix;

    static {

        double[][] m = new double [4][4];

        m[0][0] = near;
        m[1][1] = near;
        m[2][2] = near + far;
        m[2][3] = -near * far;
        m[3][2] = 1;

        matrix = new Matrix(m);
    }

    public static Vector3 transform(Vector3 point){

        return point.multiply(matrix);
    }
}
