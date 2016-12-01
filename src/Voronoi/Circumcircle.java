package Voronoi;

import ShortPath.Point;
import ShortPath.VectorAlgebra;

import static Voronoi.CircleCalcs.findCenter;
import static Voronoi.CircleCalcs.radius;

/**
 * Created by gregrell on 11/30/16.
 */
public class Circumcircle {
    Point center;
    float radius;
    Point A,B,C;
    float highestY;
    float lowestY;

    public Circumcircle(Point A, Point B, Point C){
        this.A=A;
        this.B=B;
        this.C=C;
        center = findCenter(A,B,C);
        radius = radius(A,B,C);
        highestY =center.getY()+radius;
        lowestY = center.getY()-radius;
        //System.out.println(radius);
        //System.out.println(center);

    }

    public Point getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    public Point getA() {
        return A;
    }

    public Point getB() {
        return B;
    }

    public Point getC() {
        return C;
    }

    public float getHighestY() {
        return highestY;
    }

    public float getLowestY() {
        return lowestY;
    }

    @Override
    public String toString() {
        return "Circumcircle{" +
                "center=" + center +
                ", radius=" + radius +
                ", A=" + A +
                ", B=" + B +
                ", C=" + C +
                ", highestY=" + highestY +
                ", lowestY=" + lowestY +
                '}';
    }
}

class CircleCalcs{
    public static float Tarea (Point A, Point B, Point C){
        float partial=(B.getX()*C.getY())-(B.getY()*C.getX())-(A.getX()*C.getY())+(A.getY()*C.getX())+(A.getX()*B.getY())-(A.getY()*B.getX());
        return Math.abs(partial/2);
    }

    public static float radius(Point A, Point B, Point C){
        double a = VectorAlgebra.distance2pts(C,B);
        double b = VectorAlgebra.distance2pts(A,C);
        double c = VectorAlgebra.distance2pts(B,A);
        float area=Tarea(A,B,C);
        System.out.println("a= "+a+" b= "+b+" c="+c);
        System.out.println("4xarea = "+4*area);

        float radius = ((float)a*(float)b*(float)c)/(4*area);
        return radius;
    }

    public static Point findCenter(Point A, Point B, Point C){
        Point a = new Point(C.getX()-B.getX(),C.getY()-B.getY());
        Point b = new Point(A.getX()-C.getX(),A.getY()-C.getY());
        Point c = new Point(B.getX()-A.getX(),B.getY()-A.getY());
        float triangleArea = Tarea(A,B,C);
        float fourtimestrianglearea = -4*triangleArea;
        //System.out.println(a);System.out.println(b);System.out.println(c);
        //System.out.println(triangleArea);
        //System.out.println(fourtimestrianglearea);

        double dpaa=VectorAlgebra.dotProduct(a,a);
        double dpbc=VectorAlgebra.dotProduct(b,c);
        double dpbb=VectorAlgebra.dotProduct(b,b);
        double dpac=VectorAlgebra.dotProduct(a,c);
        double dpcc=VectorAlgebra.dotProduct(c,c);
        double dpab=VectorAlgebra.dotProduct(a,b);

        Point Ap=new Point ((float)dpaa*(float)dpbc*A.getX(),(float)dpaa*(float)dpbc*A.getY());
        Point Bp=new Point ((float)dpbb*(float)dpac*B.getX(),(float)dpbb*(float)dpac*B.getY());
        Point Cp=new Point ((float)dpcc*(float)dpab*C.getX(),(float)dpcc*(float)dpab*C.getY());

        //System.out.println(Ap);
        //System.out.println(Bp);
        //System.out.println(Cp);

        Point cc = new Point(((Ap.getX()+Bp.getX()+Cp.getX()))/fourtimestrianglearea,((Ap.getY()+Bp.getY()+Cp.getY())/fourtimestrianglearea));
        System.out.println(VectorAlgebra.distance2pts(A,cc));

        return cc;
    }




}
