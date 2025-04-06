package pl.engine.math;

import pl.engine.math.Matrix;
import pl.engine.math.Vector3;

public class Transformations {

    public static Matrix getTransitionMatrix(Vector3 transition){

        double[][] t = {
            {1, 0, 0, transition.x},
            {0, 1, 0, transition.y},
            {0, 0, 1, transition.z},
            {0, 0, 0, 1},
        };

        return new Matrix(t);
    }

    public static Matrix getRotateXMatrix(int angleDegree){

        double angleInRadians = Math.toRadians(angleDegree);

        double cosValue = Math.cos(angleInRadians);
        double sinValue = Math.sin(angleInRadians);

        double[][] r = {
            {1, 0, 0, 0},
            {0, cosValue, -sinValue, 0},
            {0, sinValue, cosValue, 0},
            {0, 0, 0, 1},
        };

        return new Matrix(r);
    }

    public static Matrix getRotateYMatrix(int angleDegree){

        double angleInRadians = Math.toRadians(angleDegree);

        double cosValue = Math.cos(angleInRadians);
        double sinValue = Math.sin(angleInRadians);

        double[][] r = {
            {cosValue, 0, sinValue, 0},
            {0, 1, 0, 0},
            {-sinValue, 0, cosValue, 0},
            {0, 0, 0, 1},
        };

        return new Matrix(r);
    }

    public static Vector3 transition(Vector3 point, Vector3 transition){

        Matrix transitionMatrix = getTransitionMatrix(transition);

       return point.multiply(transitionMatrix);
    }

    public static Vector3 rotateX(Vector3 point, int angleDegree){

        Matrix rotationMatrix = getRotateXMatrix(angleDegree);

        return point.multiply(rotationMatrix);
    }

    public static Vector3 rotateY(Vector3 point, int angleDegree){

        Matrix rotationMatrix = getRotateYMatrix(angleDegree);

        return point.multiply(rotationMatrix);
    }
}
