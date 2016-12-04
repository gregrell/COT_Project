package Voronoi;

import ShortPath.Point;

/**
 * Created by gregrell on 12/1/16.
 */
public class CircleEvent extends Event {
    public CircleEvent (Point location){
        super(location.getX(),location);
    }
}
