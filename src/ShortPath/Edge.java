package ShortPath;

import java.io.Serializable;

/**
 * Created by Greg on 11/12/2016.
 */
public class Edge implements Serializable{
    public Point p1 = new Point(-1,-1);
    public Point p2 = new Point(-1,-1);

    public Edge(Point p1, Point p2){
        this.p1=p1;
        this.p2=p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }
}
