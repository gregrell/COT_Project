package ShortPath;

/**
 * Created by gregrell on 11/13/16.
 */
public class VectorAlgebra {

    public static double dotProduct(Point p1, Point p2){
        double result;
        result=p1.getX()*p2.getX()+p1.getY()*p2.getY();
        return result;
    }

    public static double vectorMagnitude(Point p1){
        double xsqrd = Math.pow(p1.getX(),2);
        double ysqrd = Math.pow(p1.getY(),2);
        double sum=xsqrd+ysqrd;
        double result=Math.sqrt(sum);
        return result;
    }

    public static double findAngle(Point p1, Point p2){
        Point horizVector = new Point(p1.getX()+100,p1.getY());
        Point regularVector = new Point(p1.getX()-p2.getX(),p1.getY()-p2.getY());

        double numerator = dotProduct(horizVector,regularVector);
        double denominator =vectorMagnitude(horizVector)*vectorMagnitude(regularVector);
        //double numerator = dotProduct(p1,p2);
        //double denominator =vectorMagnitude(p1)*vectorMagnitude(p2);
        return Math.acos(numerator/denominator)*(180/Math.PI);
    }


}
