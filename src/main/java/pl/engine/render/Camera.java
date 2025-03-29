package pl.engine.render;

import pl.engine.math.Matrix;
import pl.engine.math.Transformations;
import pl.engine.math.Vector3;

import java.awt.event.KeyEvent;

public class Camera{

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

    public void rotate(Vector3 newAngles){

        angles = angles.add(
            Vector3.of(
            newAngles.x % 360,
            newAngles.y % 360,
            newAngles.z % 360
            )
        );
    }

    public void move(Vector3 vec){

        position = position.add(vec);
    }

    public void handleCamera(int keyCode){

        switch (keyCode){

            case KeyEvent.VK_LEFT:
                rotate(Vector3.of(10, 10, 0));
                break;

            case KeyEvent.VK_RIGHT:
                rotate(Vector3.of(0, -10, 0));
                break;

            case KeyEvent.VK_UP:
                move(Vector3.of(0, 0, 10));
                break;

            case KeyEvent.VK_DOWN:
                move(Vector3.of(0, 0, -10));
                break;
        }
    }

    public Vector3 transform(Vector3 point){

        Vector3 positionNegation = position.negate();

        Matrix rotateYMatrix = Transformations.getRotateYMatrix((int) angles.y);
        Matrix transitionMatrix = Transformations.getTransitionMatrix(positionNegation);

        Matrix mergedMatrix = rotateYMatrix.multiply(transitionMatrix);

        return point.multiply(mergedMatrix);
    }
}
