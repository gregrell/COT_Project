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



    /*
    REF: http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect

   */

    public static boolean intersects(Edge e1, Edge e2){

        int p0x=e1.getP1().getX();
        int p0y=e1.getP1().getY();
        int p1x=e1.getP2().getX();
        int p1y=e1.getP2().getY();
        int p2x=e2.getP1().getX();
        int p2y=e2.getP1().getY();
        int p3x=e2.getP2().getX();
        int p3y=e2.getP2().getY();
        double s02x,s02y,s10x,s10y,s32x,s32y,snumer,tnumer,denom,t;
        s10x=p1x-p0x;
        s10y = p1y - p0y;
        s32x = p3x - p2x;
        s32y = p3y - p2y;

        denom = s10x * s32y - s32x * s10y;
        if (denom == 0) {
            // Collinear
            return false;
        }
        boolean denomPositive = denom > 0;
        s02x = p0x - p2x;
        s02y = p0y - p2y;
        snumer = s10x * s02y - s10y * s02x;
        if ((snumer < 0) == denomPositive) {
            // No collision
            return false;
        }

        tnumer = s32x * s02y - s32y * s02x;
        if ((tnumer < 0) == denomPositive) {
            // No collision
            return false;
        }

        if (((snumer > denom) == denomPositive) || ((tnumer > denom) == denomPositive)) {
            // No collision
            return false;
        }
        // Collision detected
        t = tnumer / denom;
        double xintercept = p0x + (t * s10x);
        double yintercept = p0y + (t * s10y);
        System.out.println("collision");

        return true;


    }

    public static Point intersectPt(Edge e1, Edge e2){
        Point ip = new Point (0,0);
        int p0x=e1.getP1().getX();
        int p0y=e1.getP1().getY();
        int p1x=e1.getP2().getX();
        int p1y=e1.getP2().getY();
        int p2x=e2.getP1().getX();
        int p2y=e2.getP1().getY();
        int p3x=e2.getP2().getX();
        int p3y=e2.getP2().getY();
        double s02x,s02y,s10x,s10y,s32x,s32y,snumer,tnumer,denom,t;
        s10x=p1x-p0x;
        s10y = p1y - p0y;
        s32x = p3x - p2x;
        s32y = p3y - p2y;

        denom = s10x * s32y - s32x * s10y;
        if (denom == 0) {
            // Collinear
            System.out.println("1");
            return ip;
        }
        boolean denomPositive = denom > 0;
        s02x = p0x - p2x;
        s02y = p0y - p2y;
        snumer = s10x * s02y - s10y * s02x;
        if ((snumer < 0) == denomPositive) {
            // No collision
            System.out.println("2");

            return ip;
        }

        tnumer = s32x * s02y - s32y * s02x;
        if ((tnumer < 0) == denomPositive) {
            // No collision
            System.out.println("3");

            return ip;
        }

        if (((snumer > denom) == denomPositive) || ((tnumer > denom) == denomPositive)) {
            // No collision
            System.out.println("4");

            return ip;
        }
        // Collision detected
        t = tnumer / denom;
        double xintercept = p0x + (t * s10x);
        double yintercept = p0y + (t * s10y);

        ip=new Point((int)xintercept,(int)yintercept);

        return ip;



    }



}
