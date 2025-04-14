package pl.engine.render;

import pl.engine.math.Matrix;
import pl.engine.math.Trigonometry;
import pl.engine.math.Vector3;

public class Perspective {

    private static final double fov = 50;
    private static final double near = 0.1;
    private static final double far = 1000;

    private final static Matrix matrix;

    static {

        double[][] m = new double[4][4];

        double tanInverse = 1d / Trigonometry.tan(fov / 2);
        double zDenominator = far - near;

        m[0][0] = tanInverse * 1080/1920;
        m[1][1] = tanInverse;
        m[2][2] = -(near + far)/zDenominator;
        m[2][3] = -2*(near * far)/zDenominator;
        m[3][2] = -1;

        matrix = new Matrix(m);
    }

    public static Vector3 transform(Vector3 point){

       Vector3 normalizedResult = point.multiply(matrix);

       normalizedResult.x += 1920 / 2;
       normalizedResult.y += 1080 / 2;

//       System.out.println(normalizedResult);

       return normalizedResult;
    }
}
