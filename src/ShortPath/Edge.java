package ShortPath;

import java.io.Serializable;

/**
 * Created by Greg on 11/12/2016.
 */
public class Edge implements Serializable{
    public Point p1 = new Point(-1,-1);
    public Point p2 = new Point(-1,-1);
    public ABCform ABC;


    public Edge(Point p1, Point p2){
        this.p1=p1;
        this.p2=p2;
        ABC=new ABCform(p1,p2);
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public ABCform getABC() {
        return ABC;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}


class ABCform implements Serializable {
    double A;
    double B;
    double C;

    ABCform(Point p1, Point p2){
        A=p1.getY()-p2.getY();
        B=p2.getX()-p1.getX();
        C=(p1.getX()-p2.getX())*p1.getY() + (p2.getY()-p1.getY())*p1.getX();
    }
}