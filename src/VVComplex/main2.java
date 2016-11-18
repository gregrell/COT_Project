package VVComplex;

import ShortPath.Edge;
import ShortPath.Point;
import ShortPath.VectorAlgebra;

/**
 * Created by 206417559 on 11/18/2016.
 */
public class main2 {
    public static void main(String[] args){
        Edge e1 = new Edge(new Point(320,370), new Point(558,459));
        Edge e2 = new Edge(new Point(558,459),new Point(569,378));
        //Edge e1 = new Edge(new Point(0,0), new Point(500,500));
        //Edge e2 = new Edge(new Point(500,500),new Point(500,800));

        System.out.println(VectorAlgebra.intersects(e1,e2));
        System.out.println(VectorAlgebra.intersectPt(e1,e2));


    }
}
