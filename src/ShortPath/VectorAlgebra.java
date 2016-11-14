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
        Point horizVector = new Point(p1.getX()+1,0);
        Point normalVector = new Point(0,p1.getY()+1);
        Point regularVector = new Point(p2.getX()-p1.getX(),p2.getY()-p1.getY());
        Point regularVectorInv = new Point(p1.getX()-p2.getX(),p1.getY()-p2.getY());

        double result=0;
        double resultRad;

        double divisorLine=dotProduct(normalVector,regularVector);
        double normalLine = dotProduct(horizVector, regularVector);
        if(divisorLine>0){
           if(normalLine<0) {
               double numerator = dotProduct(regularVectorInv, normalVector);
               double denominator = vectorMagnitude(normalVector) * vectorMagnitude(regularVectorInv);
               resultRad = Math.acos(numerator / denominator);
               result = resultRad * (180 / Math.PI) + 90;
           }
           else{
               double numerator = dotProduct(horizVector, regularVectorInv);
               double denominator = vectorMagnitude(horizVector) * vectorMagnitude(regularVectorInv);
               resultRad = Math.acos(numerator / denominator);
               result = resultRad * (180 / Math.PI)+180;
           }

        }
        else {
            double numerator = dotProduct(horizVector, regularVector);
            double denominator = vectorMagnitude(horizVector) * vectorMagnitude(regularVector);
            resultRad = Math.acos(numerator / denominator);
            result = resultRad * (180 / Math.PI);
        }

        return Math.abs(result-360);
    }


}
