package pl.engine;

public class Transformations {

    public static Matrix getTransitionMatrix(Vector3 transition){

        int[][] t = {
            {1, 0, 0, transition.x},
            {0, 1, 0, transition.y},
            {0, 0, 1, transition.z},
            {0, 0, 0, 1},
        };

        return new Matrix(t);
    }

    public static Matrix getRotateYMatrix(int angleDegree){

        double angleInRadians = Math.toRadians(angleDegree);

        int cosValue = (int) Math.cos(angleInRadians);
        int sinValue = (int) Math.sin(angleInRadians);

        int[][] r = {
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

    public static Vector3 rotateY(Vector3 point, int angleDegree){

        Matrix rotationMatrix = getRotateYMatrix(angleDegree);

        return point.multiply(rotationMatrix);
    }
}
