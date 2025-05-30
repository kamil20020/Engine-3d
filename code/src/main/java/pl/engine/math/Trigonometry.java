package pl.engine.math;

public class Trigonometry {

    public static double cos(double angleInDegree){

        double angleInRad = Math.toRadians(angleInDegree);

        return Math.cos(angleInRad);
    }

    public static double sin(double angleInDegree){

        double angleInRad = Math.toRadians(angleInDegree);

        return Math.sin(angleInRad);
    }

    public static double tan(double angleInDegree){

        double angleInRad = Math.toRadians(angleInDegree);

        return Math.tan(angleInRad);
    }
}
