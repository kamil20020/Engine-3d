package pl.engine;

public class Camera {

    private Vector3 position;
    private Vector3 lookAt;
    private Vector3 angles;
    private Vector3 topDirection;

    public Camera(Vector3 position, Vector3 lookAt, Vector3 topDirection, Vector3 angles) {

        this.position = position;
        this.lookAt = lookAt;
        this.topDirection = topDirection;
        this.angles = angles;
    }

    public Vector3 transform(Vector3 point){

        Vector3 positionNegation = position.negate();

        Matrix rotateYMatrix = Transformations.getRotateYMatrix(angles.y);
        Matrix transitionMatrix = Transformations.getTransitionMatrix(positionNegation);

        Matrix mergedMatrix = rotateYMatrix.multiply(transitionMatrix);

        return point.multiply(mergedMatrix);
    }
}
