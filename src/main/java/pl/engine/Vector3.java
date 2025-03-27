package pl.engine;

import java.util.Objects;

public class Vector3 {

    public int x, y, z;

    public Vector3(Integer x, Integer y, Integer z){

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3 of(Integer x, Integer y, Integer z){

        return new Vector3(x, y, z);
    }

    public static Vector3 of(Vector3 v){

        return new Vector3(v.x, v.y, v.z);
    }

    public Vector3 multiply(Matrix m){

        int weight = multiplyWithMatrixRow(m, 3);

        if(weight == 0){
            weight = 1;
        }

        return new Vector3(
            multiplyWithMatrixRow(m, 0) / weight,
            multiplyWithMatrixRow(m, 1) / weight,
            multiplyWithMatrixRow(m, 2) / weight
        );
    }

    public Vector3 negate(){

        return Vector3.of(-x, -y, -z);
    }

    private int multiplyWithMatrixRow(Matrix m, int rowIndex){

        return m.get(rowIndex, 0) * x + m.get(rowIndex, 1) * y + m.get(rowIndex, 2) * z + m.get(rowIndex, 3);
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
