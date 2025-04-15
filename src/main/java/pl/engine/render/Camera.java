package pl.engine.render;

import pl.engine.exceptions.InvalidCloneException;
import pl.engine.math.Matrix;
import pl.engine.math.Transformations;
import pl.engine.math.Trigonometry;
import pl.engine.math.Vector3;
import pl.engine.shapes.flat.Triangle;

import java.awt.event.KeyEvent;

public class Camera{

    public Vector3 position;
    private Vector3 lookAt;
    private Vector3 angles;
    private Vector3 topDirection;
    public Vector3 forward;

    public Camera(Vector3 position, Vector3 angles) {

        this.position = position;
//        this.lookAt = lookAt;
//        this.topDirection = topDirection;
        this.angles = angles;

        updateForward();
    }

    private void updateForward(){

        Matrix rotateXMatrix = Transformations.getRotateXMatrix((int) angles.x);
        Matrix rotateYMatrix = Transformations.getRotateYMatrix((int) angles.y);
        Matrix rotateZMatrix = Transformations.getRotateZMatrix((int) angles.z);

        Matrix mergedMatrix = rotateZMatrix.multiply(rotateYMatrix.multiply(rotateXMatrix));

        this.forward = Vector3.of(0, 0, -1).multiply(mergedMatrix);
    }

    public void rotate(Vector3 newAngles){

        angles = angles.add(newAngles);
        angles = angles.change(x -> x % 360);

        updateForward();
    }

    public void move(Vector3 vec){

        position = position.add(vec);
    }

    public void handleCamera(int keyCode){

        switch (keyCode){

            case KeyEvent.VK_W:
                move(forward.negate());
                break;

            case KeyEvent.VK_S:
                move(forward);
                break;

            case KeyEvent.VK_A:
                rotate(Vector3.of(0, -1, 0));
                break;

            case KeyEvent.VK_D:
                rotate(Vector3.of(0, 1, 0));
                break;

            case KeyEvent.VK_LEFT:
                move(Vector3.of(10, 0, 0));
                break;

            case KeyEvent.VK_RIGHT:
                move(Vector3.of(-10, 0, 0));
                break;

            case KeyEvent.VK_UP:
                rotate(Vector3.of(1, 0, 0));
                break;

            case KeyEvent.VK_DOWN:
                rotate(Vector3.of(-1, 0, 0));
                break;

            case KeyEvent.VK_SPACE:
                move(Vector3.of(0, -10, 0));
                break;

            case KeyEvent.VK_Z:
                move(Vector3.of(0, 10, 0));
                break;

            case KeyEvent.VK_NUMPAD1:
                rotate(Vector3.of(0, 0, -1));
                break;

            case KeyEvent.VK_NUMPAD2:
                rotate(Vector3.of(0, 0, 1));
                break;
        }
    }

    public Vector3 transform(Vector3 point){

        Vector3 positionNegation = position.negate();

        Matrix rotateXMatrix = Transformations.getRotateXMatrix((int) angles.x);
        Matrix rotateYMatrix = Transformations.getRotateYMatrix((int) angles.y);
        Matrix rotateZMatrix = Transformations.getRotateZMatrix((int) angles.z);
        Matrix transitionMatrix = Transformations.getTransitionMatrix(positionNegation);

        Matrix mergedMatrix = transitionMatrix.multiply(
            rotateXMatrix.multiply(
                rotateYMatrix.multiply(rotateZMatrix)
            )
        );

        return point.multiply(mergedMatrix);
    }

    public boolean isTriangleHidden(Vector3 triangleA, Vector3 triangleB){

        Vector3 triangleCrossProduct = Vector3.crossProduct(triangleA, triangleB);

        double dotProductWithCamera = Vector3.dotProduct(triangleCrossProduct, forward);

        return dotProductWithCamera < 0;
    }

    @Override
    public String toString(){

        return "Camera {position: " + position + ", angles: " + angles + "}";
    }
}
