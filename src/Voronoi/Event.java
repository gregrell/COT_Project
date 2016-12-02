package Voronoi;

import ShortPath.Point;

/**
 * Created by gregrell on 12/1/16.
 */
public class Event implements Comparable{
    Point location;
    Arc arc;
    public Event(Point location, Arc arc){
        this.arc=arc;
        this.location=location;
    }

    public boolean containsArc(Arc compareArc){
        return arc==compareArc;
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Event{" +
                "location=" + location +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return -(this.location.compareTo(((Event)o).getLocation()));
    }
}
