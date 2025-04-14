package pl.engine.math;

import java.util.Objects;
import java.util.Vector;
import java.util.function.UnaryOperator;

public class Vector3 {

    public double x, y, z;

    public Vector3(double x, double y, double z){

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3 of(double x, double y, double z){

        return new Vector3(x, y, z);
    }

    public static Vector3 of(Vector3 v){

        return new Vector3(v.x, v.y, v.z);
    }

    public static Vector3 empty(){

        return new Vector3(0, 0, 0);
    }

    public Vector3 change(UnaryOperator<Double> changeFunction){

        return Vector3.of(
            changeFunction.apply(x),
            changeFunction.apply(y),
            changeFunction.apply(z)
        );
    }

    public Vector3 negate(){

        return Vector3.of(-x, -y, -z);
    }

    public Vector3 add(Vector3 vec){

        return Vector3.of(
                x + vec.x,
                y + vec.y,
                z + vec.z
        );
    }

    public Vector3 multiply(double a){

        return Vector3.of(
            x * a,
            y * a,
            z * a
        );
    }

    public Vector3 multiply(Matrix m){

        double weight = multiplyWithMatrixRow(m, 3);

        if(weight == 0){
            weight = 1;
        }

        return new Vector3(
            multiplyWithMatrixRow(m, 0) / weight,
            multiplyWithMatrixRow(m, 1) / weight,
            multiplyWithMatrixRow(m, 2) / weight
        );
    }

    private double multiplyWithMatrixRow(Matrix m, int rowIndex){

        return m.get(rowIndex, 0) * x + m.get(rowIndex, 1) * y + m.get(rowIndex, 2) * z + m.get(rowIndex, 3);
    }

    public static double dotProduct(Vector3 a, Vector3 b){

        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public Vector3 normalize(){

        double width = Math.sqrt(x * x + y * y + z * z);

        if(width == 0) {
            return Vector3.of(this);
        }

        return Vector3.of(
            x / width,
            y / width,
            z / width
        );
    }

    public static Vector3 crossProduct(Vector3 a, Vector3 b){

        // i  j  k
        // ax ay az
        // bx by bz

        // i * ay * bz + k * ax * by + j * bx * az - bx * ay * k - by * az * i - bz * ax * j
        // (ay * bz - by * az) * i + (az * bx - ax * bz) * j + (ax * by - ay * bx) * k
        // [ay * bz - by * az, az * bx - az * bz, ax * by - ay * bx]

        return Vector3.of(
            a.y * b.z - a.z * b.y,
            -a.x * b.z + a.z * b.x,
            a.x * b.y - a.y * b.x
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(Object o){

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Vector3 v = (Vector3) o;

        return x == v.x && y == v.y && z == v.z;
    }

    @Override
    public String toString(){

        return "(" + x + ", " + y + ", " + z + ")";
    }
}
