package Voronoi;

import ShortPath.Point;
import ShortPath.VectorAlgebra;

import static Voronoi.CircleCalcs.*;

/**
 * Created by gregrell on 11/30/16.
 */
public class Circumcircle {
    Point center;
    float radius;
    Point A,B,C;
    float highestX;
    float lowestX;
    float Tarea;
    float Carea;
    boolean valid=true;

    public Circumcircle(Point A, Point B, Point C){
        this.A=A;
        this.B=B;
        this.C=C;
        center = findCenter(A,B,C);
        radius = radius(A,B,C);
        highestX =center.getX()+radius;
        lowestX = center.getX()-radius;
        Tarea= Tarea(A,B,C);
        Carea=Carea(A,B,C);
        checkValidity();
        //System.out.println("Validity check "+valid);

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

    public float getHighestX() {
        return highestX;
    }

    public float getLowestX() {
        return lowestX;
    }

    public float getTarea() {
        return Tarea;
    }

    public float getCarea() {
        return Carea;
    }

    public boolean valid(){
        return valid;
    }

    private void checkValidity(){
        /*
        // Check that bc is a "right turn" from ab.
        if ((b.x-a.x)*(c.y-a.y) - (c.x-a.x)*(b.y-a.y) > 0)
            return false;

        // Algorithm from O'Rourke 2ed p. 189.
        double A = b.x - a.x,  B = b.y - a.y,
                C = c.x - a.x,  D = c.y - a.y,
                E = A*(a.x+b.x) + B*(a.y+b.y),
                F = C*(a.x+c.x) + D*(a.y+c.y),
                G = 2*(A*(c.y-b.y) - B*(c.x-b.x));

        if (G == 0) return false;  // Points are co-linear.

        // Point o is the center of the circle.
        o->x = (D*E-B*F)/G;
        o->y = (A*F-C*E)/G;

        // o.x plus radius equals max x coordinate.
   *x = o->x + sqrt( pow(a.x - o->x, 2) + pow(a.y - o->y, 2) );
        return true;*/

        // Check that bc is a "right turn" from ab.
        /*
        if(((B.getX()-A.getX())*(C.getY()-A.getY()) - ((C.getX()-A.getX())*(B.getY()-A.getY())))>0){
            valid=false;
            return;
        }*/
        // Algorithm from O'Rourke 2ed p. 189.
        float a = B.getX()-A.getX(), b = B.getX()-A.getY(),
                G=2*(a*(C.getY()-B.getY())) - b*(C.getX()-B.getX());

        if(G==0){//points are colinear
            valid=false;
            return;
        }

        valid=true;

    }


    @Override
    public String toString() {
        return "Circumcircle{" +
                "center=" + center +
                ", radius=" + radius +
                ", A=" + A +
                ", B=" + B +
                ", C=" + C +
                ", highestX=" + highestX +
                ", lowestX=" + lowestX +
                ", Triangle Area=" + Tarea +
                ", Circle Area=" + Carea +
                '}';
    }
}

class CircleCalcs{//class of static calls
    public static float Tarea (Point A, Point B, Point C){//calculate triangle area of three points
       /* float partial=(B.getX()*C.getY())-(B.getY()*C.getX())-(A.getX()*C.getY())+(A.getY()*C.getX())+(A.getX()*B.getY())-(A.getY()*B.getX());
        return partial/2;*/
        //float radius = radius(A,B,C);
        //return (float)(Math.PI*Math.pow(radius,2));
        double a = VectorAlgebra.distance2pts(C,B);
        double b = VectorAlgebra.distance2pts(A,C);
        double c = VectorAlgebra.distance2pts(B,A);
        /*float area=Tarea(A,B,C);
        System.out.println("a= "+a+" b= "+b+" c="+c);
        System.out.println("4xarea = "+4*area);

        float radius = ((float)a*(float)b*(float)c)/(4*area);*/

        float s = (float)(a+b+c)/2;

        float innerDenom = (float)(s*(s-a)*(s-b)*(s-c));
        float sqrtinnerDenom = (float)Math.sqrt(innerDenom);
        //System.out.println("side a="+a+" side b="+b+" side c="+c+" Area="+sqrtinnerDenom);
        return sqrtinnerDenom;
    }

    public static float Carea(Point A, Point B, Point C){//calculate area of circumcircle
        float radius = radius(A,B,C);
        return (float)(Math.PI*Math.pow(radius,2));

    }


    public static float radius(Point A, Point B, Point C){//find radius of circumcircle given three points
        double a = VectorAlgebra.distance2pts(C,B);
        double b = VectorAlgebra.distance2pts(A,C);
        double c = VectorAlgebra.distance2pts(B,A);


        float s = (float)(a+b+c)/2;
        float numerator = (float)(a*b*c);
        float innerDenom = (float)(s*(s-a)*(s-b)*(s-c));
        float sqrtinnerDenom = (float)Math.sqrt(innerDenom);
        float denom = 4*sqrtinnerDenom;
        float radius = numerator/denom;

        return radius;
    }

    public static Point findCenter(Point A, Point B, Point C){ //find center of circumcircle given three points
        Point a = new Point(C.getX()-B.getX(),C.getY()-B.getY());
        Point b = new Point(A.getX()-C.getX(),A.getY()-C.getY());
        Point c = new Point(B.getX()-A.getX(),B.getY()-A.getY());
        float triangleArea = Tarea(A,B,C);

        float Axsqrd=A.getX()*A.getX();
        float Aysqrd=A.getY()*A.getY();
        float Bxsqrd=B.getX()*B.getX();
        float Bysqrd=B.getY()*B.getY();
        float Cxsqrd=C.getX()*C.getX();
        float Cysqrd=C.getY()*C.getY();

        float D = 2*(A.getX()*(B.getY()-C.getY())+(B.getX()*(C.getY()-A.getY()))+ (C.getX()*(A.getY()-B.getY())));

        float Ux=(((Axsqrd+Aysqrd)*(B.getY()-C.getY()))+((Bxsqrd+Bysqrd)*(C.getY()-A.getY()))+(Cxsqrd+Cysqrd)*(A.getY()-B.getY()))/(D);
        float Uy=(((Axsqrd+Aysqrd)*(C.getX()-B.getX()))+((Bxsqrd+Bysqrd)*(A.getX()-C.getX()))+(Cxsqrd+Cysqrd)*(B.getX()-A.getX()))/(D);

        return new Point(Ux,Uy);

    }




}
