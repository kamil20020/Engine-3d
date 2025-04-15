package pl.engine.render;

import pl.engine.math.Matrix;
import pl.engine.math.Trigonometry;
import pl.engine.math.Vector3;

public class Perspective {

    private static final double fov = 120.0;
    private static final double near = 0.1;
    private static final double far = 100.0;
    private static final double aspectRatio = 1920.0 / 1080.0;

    private final static Matrix matrix;

    static {

        double tanInverse = 1.0 / Trigonometry.tan(fov / 2.0);
        double reverseAspectRatio = 1.0 / aspectRatio;
        double zDenominator = 1.0 / (far - near);

        double[][] m = {
            {tanInverse * reverseAspectRatio, 0,          0,                            0                               },
            {0,                               tanInverse, 0,                            0                               },
            {0,                               0,          (near + far) * zDenominator, (-near * far) * zDenominator     },
            {0,                               0,          -1.0,                         0                               }
        };

        matrix = new Matrix(m);
    }

    public static void main(String[] args){

        Perspective.transform(Vector3.empty());
    }

    public static Vector3 transform(Vector3 point){

       Vector3 normalizedResult = point.multiply(matrix);

       normalizedResult.x = (normalizedResult.x + 1) * 1920.0 / 2.0;
       normalizedResult.y = (1 - normalizedResult.y) * 1080.0 / 2.0;
       normalizedResult.z = point.z;

       return normalizedResult;
    }
}
