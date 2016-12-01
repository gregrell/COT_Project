package VVComplex;

import ShortPath.Edge;
import ShortPath.Point;
import ShortPath.VectorAlgebra;
import Voronoi.Circumcircle;

/**
 * Created by 206417559 on 11/18/2016.
 */
public class main2 {
    public static void main(String[] args){
        //Edge e1 = new Edge(new Point(320,370), new Point(558,459));
        //Edge e2 = new Edge(new Point(558,459),new Point(569,378));
        Edge e2 = new Edge(new Point(1,1), new Point(3,1));
        //Edge e1 = new Edge(new Point(3,5), new Point(2,1));
        Edge e1 = new Edge(new Point(2,0),new Point(2,4));

        Point A = new Point(1,0);
        Point B = new Point(2,0);
        Point C = new Point(1,1);
        Circumcircle cc = new Circumcircle(A,B,C);
        System.out.println(cc.toString());

        //System.out.println(VectorAlgebra.segmentProperIntersection(e1,e2));


    }
}
