package pl.engine.shapes.flat;

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
