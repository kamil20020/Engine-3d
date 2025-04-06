package pl.engine.render;

import pl.engine.exceptions.InvalidCloneException;
import pl.engine.math.Matrix;
import pl.engine.math.Transformations;
import pl.engine.math.Trigonometry;
import pl.engine.math.Vector3;
import pl.engine.shapes.flat.Triangle;

import java.awt.event.KeyEvent;

public class Camera{

    private Vector3 position;
    private Vector3 lookAt;
    private Vector3 angles;
    private Vector3 topDirection;

    public Camera(Vector3 position, Vector3 angles) {

        this.position = position;
//        this.lookAt = lookAt;
//        this.topDirection = topDirection;
        this.angles = angles;
    }

    public void rotate(Vector3 newAngles){

        angles = angles.add(newAngles);
        angles = angles.change(x -> x % 360);
    }

    public void move(Vector3 vec){

        position = position.add(vec);
    }

    public void handleCamera(int keyCode){

        switch (keyCode){

            case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                rotate(Vector3.of(0, 1, 0));
                break;

            case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                rotate(Vector3.of(0, -1, 0));
                break;

            case KeyEvent.VK_W:
                move(getMoveInDirection());
                break;

            case KeyEvent.VK_S:
                move(getMoveInDirection().negate());
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
        }

        System.out.println(this);
    }

    private Vector3 getMoveInDirection(){

        double moveX = Trigonometry.cos(angles.x) * 10;
        double moveY = Trigonometry.sin(angles.y) * 10;
        double moveZ = Trigonometry.cos(angles.z) * 10;

        return Vector3.of(moveX, moveY, moveZ);
    }

    public Vector3 transform(Vector3 point){

        Vector3 positionNegation = position.negate();

        Matrix rotateXMatrix = Transformations.getRotateXMatrix((int) angles.x);
        Matrix rotateYMatrix = Transformations.getRotateYMatrix((int) angles.y);
        Matrix transitionMatrix = Transformations.getTransitionMatrix(positionNegation);

        Matrix mergedMatrix = transitionMatrix.multiply(
            rotateYMatrix.multiply(rotateXMatrix)
        );

        return point.multiply(mergedMatrix);
    }

    @Override
    public String toString(){

        return "Camera {position: " + position + ", angles: " + angles + "}";
    }
}
