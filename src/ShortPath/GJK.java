package ShortPath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregrell on 10/30/16.
 * This algorithm is based on Gilbert-Johnson-Keerthi algorithm
 * Ref: http://www.cs.sjsu.edu/faculty/pollett/masters/Semesters/Spring12/josh/GJK.html
 * Ref: https://mollyrocket.com/849
 * Ref: https://en.wikipedia.org/wiki/Gilbert%E2%80%93Johnson%E2%80%93Keerthi_distance_algorithm
 * Ref: http://www.dyn4j.org/2010/04/gjk-gilbert-johnson-keerthi/#gjk-simplex
 */

public class GJK {

    public Point Support(Polygon polygon, Point direction){
        Point closestPoint=polygon.getHull().get(0);
        double maxDotProduct=dotProduct(polygon.getHull().get(0),direction);
        for(Point P:polygon.getHull()){
            int dotProduct=P.getX()*direction.getX()+P.getY()*direction.getY();
            if (dotProduct>maxDotProduct){
                maxDotProduct=dotProduct;
                closestPoint=P;
            }

        }
        return closestPoint;
    }

    public double dotProduct(Point A, Point B){
        return (A.getX()*B.getX()+A.getY()*B.getY());

    }

    public Point pointSubtraction(Point A, Point B){
        return new Point(A.getX()-B.getX(),A.getY()-B.getY());
    }

    public Boolean GJKCollision (Polygon poly1, Polygon poly2){
        Boolean retVal=false;
        Point arbitraryVector = new Point(100,100);
        Point Support1 = Support(poly1,arbitraryVector);
        Point Support2 = Support(poly2,arbitraryVector.inverse());
        Point VectorA = new Point(Support1.getX()-Support2.getX(),Support1.getY()-Support2.getY());
        List<Point> Simplex = new ArrayList<Point>();
        Simplex.add(VectorA);
        Point VectorD = VectorA.inverse();
        while(Simplex.size()<4){
            VectorA=pointSubtraction(Support(poly1,VectorD),Support(poly2,VectorD));
            if(dotProduct(VectorA,VectorD)<0){
                break;
            }
            else {
                Simplex.add(VectorA);
                if(doSimplex(Simplex,VectorD)) {
                    retVal = true;
                    break;
                }


            }
        }

        System.out.println(Simplex.toString());
        return retVal;
    }

    public boolean doSimplex(List<Point> Simplex, Point direction) {
        boolean foundOrigin = false;
        Point origin = new Point(0, 0);
        boolean containsOrigin = Simplex.contains(origin);
        if (Simplex.size() == 2) { //two point simplex case
            Point simplexLine = new Point(Simplex.get(0).getX() - Simplex.get(1).getX(), Simplex.get(0).getY() - Simplex.get(1).getY());
            Point newpointToOrigin = new Point(0 - (Simplex.get(1).getX()), 0 - (Simplex.get(1).getY()));
            double dotProductResult = dotProduct(simplexLine, newpointToOrigin);
            if (dotProductResult > 0) {
                direction= newpointToOrigin;
                //positive therefore region 1
            } else {
                //region2
            }
        }

        else if (Simplex.size() >= 3) { //three point simplex case


            }
        return foundOrigin||containsOrigin;


        }



}
