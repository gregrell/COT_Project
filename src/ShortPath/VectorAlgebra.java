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

    public static Point intersection(Edge e1, Edge e2){
        float x=0;
        float y=0;
        ABCform l1=e1.getABC();
        ABCform l2=e2.getABC();
        if(!(l1.slopeInf())&&!(l2.slopeInf())){
            x = (l2.C-l1.C)/(l2.A-l1.A);
            y = (-l1.A*x)+l1.C;
        }
        else if(l1.slopeInf()&&(!(l2.slopeInf()))){
            System.out.println("infinite slope l1 "+l1.toString());
            ABCform temp=l1;
            l1=l2;
            l2=temp;
            x=l2.C;
            y = (-l1.A*x)+l1.C;
        }
        else if (l2.slopeInf()&&(!l1.slopeInf())){
            System.out.println("infinite slope l2 "+l2.toString());

            x=l2.C;
            y = (-l1.A*x)+l1.C;
        }
        else{
            //both are infinite slope do nothing
        }
        Point intersectPt= new Point(x,y);
        //System.out.println(intersectPt);
        return intersectPt;
    }

    public static Point segmentIntersection(Edge e1, Edge e2){
        Point intersectPt=intersection(e1,e2);
        boolean existsL1=false;
        boolean existsL2=false;
        //line 1:
        existsL1=onSegment(e1.getP1(),e1.getP2(),intersectPt);
        existsL2=onSegment(e2.getP1(),e2.getP2(),intersectPt);

        if(existsL1&&existsL2){
            return intersectPt;
        }
        else
            return new Point(0,0);

    }

    public static Point segmentProperIntersection(Edge e1, Edge e2){
        Point intersectPt=intersection(e1,e2);
        boolean existsL1=false;
        boolean existsL2=false;
        boolean notEndpoit=true;
        //line 1:
        existsL1=onSegment(e1.getP1(),e1.getP2(),intersectPt);
        existsL2=onSegment(e2.getP1(),e2.getP2(),intersectPt);

        System.out.println("Intersect Pt "+intersectPt+" e1-p1 "+e1.getP1()+" e1-p2 "+e1.getP2()+" e2-p1 "+e2.getP1()+" e2-p2 "+e2.getP2());

        if(samePoints(intersectPt,e1.getP1())){
            System.out.println("same as e1p1");
            notEndpoit=false;
        }
        else if(samePoints(intersectPt,e1.getP2())){
            notEndpoit=false;

        }
        else if(samePoints(intersectPt,e2.getP1())){
            notEndpoit=false;

        }
        else if(samePoints(intersectPt,e2.getP2())){
            notEndpoit=false;

        }


        if(existsL1&&existsL2&&notEndpoit){
            return intersectPt;
        }
        else
            return new Point(0,0);

    }

    public static boolean samePoints(Point p1, Point p2){ // compute if two points are nearly the same with only rounding error differences
        boolean samepts=false;
        if((Math.round(p1.getX())==Math.round(p2.getX()))&&(Math.round(p1.getY())==Math.round(p2.getY()))){
            samepts=true;
        }

        System.out.println("Same Points Check P1 "+p1.toString()+" P2 "+p2+" Results in "+samepts);
        return samepts;
    }

    public static boolean onSegment(Point a, Point b, Point c){
        double distAC = distance2pts(a,c);
        double distCB = distance2pts(c,b);
        double distAB = distance2pts(a,b);
        //doing a floating point comparison requires getting the integer value to three decimal places and comparing those components
        distAB=distAB*1000;
        distAC=distAC*1000;
        distCB=distCB*1000;

        //System.out.println("Distance AC "+distAC+" Distance CB "+distCB+" Distance AB "+distAB+" Distance AC to CB "+(distAC+distCB));


        return ((int)(distAC+distCB)==(int)(distAB));
    }





}
