package Voronoi3;



import ShortPath.Edge;
import ShortPath.Point;
import Voronoi.Circumcircle;

/**
 * Created by gregrell on 12/8/16.
 */
public class Triangle {
    Point A;
    Point B;
    Point C;
    Point Center;

    public Triangle(Point A, Point B, Point C){
        this.A=A;
        this.B=B;
        this.C=C;
        Circumcircle centerCircle = new Circumcircle(A,B,C);
        Center=centerCircle.getCenter();

    }


    public Edge[] Edges(){
        Edge[] edges = new Edge[3];
        edges[0]=new Edge(A,B);
        edges[1]=new Edge(B,C);
        edges[2]=new Edge(C,A);
        return edges;
    }

    public boolean compareTo(Triangle compared){
        return(this.A.comparedTo(compared.A) && this.B.comparedTo(compared.B) && this.C.comparedTo(compared.C));
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "A=" + A +
                ", B=" + B +
                ", C=" + C +
                '}';
    }
}
