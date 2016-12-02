package Voronoi;

import ShortPath.Edge;
import ShortPath.Point;

/**
 * Created by gregrell on 12/1/16.
 */
public class Segment extends BLitem {
    Point Start;
    Point End;
    boolean active=false;

    public Segment (Point start){
        super(start);
        this.Start=start;
        active=true;

    }


    public void setEnd(Point end) {
        End = end;
        active=false;
    }

    public Edge toEdge(){
        return new Edge(Start,End);
    }
}
