package Voronoi;

import ShortPath.Point;

/**
 * Created by gregrell on 12/1/16.
 */
public class Event implements Comparable{
    Point location;
    Arc arc;
    float x;
    boolean valid=false;
    public Event(float x, Point location, Arc arc){
        this.x=x;
        this.arc=arc;
        this.location=location;
        valid=true;
    }
    public Event(Point location){
        this.location=location;
        valid=true;
    }

    public boolean containsArc(Arc compareArc){
        return arc==compareArc;
    }

    public Point getLocation() {
        return location;
    }


    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Arc getArc() {
        return arc;
    }

    public void setArc(Arc arc) {
        this.arc = arc;
    }

    @Override
    public String toString() {
        return "Event{" +
                "location=" + location +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return (this.location.compareTo(((Event)o).getLocation()));
    }
}
