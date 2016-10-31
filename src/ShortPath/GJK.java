package ShortPath;

/**
 * Created by gregrell on 10/30/16.
 * This algorithm is based on Gilbert-Johnson-Keerthi algorithm
 * Ref: http://www.cs.sjsu.edu/faculty/pollett/masters/Semesters/Spring12/josh/GJK.html
 * Ref: https://mollyrocket.com/849
 * Ref: https://en.wikipedia.org/wiki/Gilbert%E2%80%93Johnson%E2%80%93Keerthi_distance_algorithm
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
}
