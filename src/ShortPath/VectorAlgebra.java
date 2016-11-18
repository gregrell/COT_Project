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

    public static double distance2pts(Point p1, Point p2){
        double p2minusp1X = p2.getX()-p1.getX();
        double p2minusp1Y = p2.getY()-p1.getY();
        double xvalSqrd = Math.pow(p2minusp1X,2);
        double yvalSqrd = Math.pow(p2minusp1Y,2);
        double XYadded = xvalSqrd+yvalSqrd;
        return Math.sqrt(XYadded);
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



    public static boolean intersection(Edge e1, Edge e2){
        double A1B2 = e1.getABC().A*e2.getABC().B;
        double A2B1 = e2.getABC().A*e1.getABC().B;
        boolean retVal=false;
        if(A1B2!=A2B1){
            retVal=true;
        }

        return retVal;
    }

    public static Point intersectionPt(Edge e1, Edge e2){
        ABCform l1 = e1.getABC();
        ABCform l2 = e2.getABC();

        /*
        y = (c1a2- c2a1)/(a1b2-a2b1)

        x = -1/a1 - b1y/a1
        */

        double Y = (l1.C*l2.A)/(l1.A*l2.B-l2.A*l2.B);
        double X = (-1/l1.A)-(l1.B*Y/l1.A);

        return new Point((int)X,(int)Y);

    }

}
