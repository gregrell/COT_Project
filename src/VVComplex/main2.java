package VVComplex;

import ShortPath.Point;
import Voronoi.Circumcircle;

/**
 * Created by 206417559 on 11/18/2016.
 */
public class main2 {
    public static void main(String[] args){


        Point A = new Point(1,0);
        Point B = new Point(2,0);
        Point C = new Point(1,1);
        Circumcircle cc = new Circumcircle(A,B,C);

        System.out.println(cc.toString()+" the lowest X event is "+cc.getLowestX());

        //Arc parab = new Arc(new Point(0,2),0);
        //System.out.println(parab.getSweep());

        //System.out.println(VectorAlgebra.segmentProperIntersection(e1,e2));


    }
}
