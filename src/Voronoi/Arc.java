package Voronoi;

import ShortPath.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregrell on 12/1/16.
 */


public class Arc {
    Point focus;
    float directix;
    String formula;
    float a;
    float b;
    float c;
    List<Point> sweep = new ArrayList<Point>();
    Event e;
    Segment segLeft=null, segRight=null;
    Arc prev=null,next=null;

    public Arc(Point focus, float directix, Event e){
        this.e=e;
        this.directix=directix;
        this.focus=focus;
        a=focus.getX();
        b=focus.getY();
        c=directix;
        formula ="y=(x-"+a+")^2+"+b+"^2 - "+c+"^2 /2("+b+"-"+c+")";
        dosweep();
    }

    public Arc(Point focus, Arc prev, Arc next){
        this.focus=focus;
        this.prev=prev;
        this.next=next;

    }
    public Arc(Point focus, Arc prev){
        this.focus=focus;
        this.prev=prev;
        this.next=null;

    }

    private void dosweep(){
        for (int i=0;i<5000;i++){
            float x = ((float)Math.pow(((float)i-a),2)+((float)Math.pow(b,2))-((float)Math.pow(c,2)))/(2*(b-c));
            Point p = new Point(x,(float)i);
            sweep.add(p);
        }
    }

    public boolean containsEvent(Event e){
        return this.e==e;
    }


    public String getFormula() {
        return formula;
    }

    public List<Point> getSweep() {
        return sweep;
    }

    public Event getE() {
        return e;
    }

    public void setE(Event e) {
        this.e = e;
    }

    public Segment getSegLeft() {
        return segLeft;
    }

    public void setSegLeft(Segment segLeft) {
        this.segLeft = segLeft;
    }

    public Segment getSegRight() {
        return segRight;
    }

    public void setSegRight(Segment segRight) {
        this.segRight = segRight;
    }

    public Arc getPrev() {
        return prev;
    }

    public void setPrev(Arc prev) {
        this.prev = prev;
    }

    public Arc getNext() {
        return next;
    }

    public void setNext(Arc next) {
        this.next = next;
    }

    public void setEventNull(){
        this.e=null;
    }

    @Override
    public String toString() {
        return "Arc{" +
                "focus=" + focus +
                " previous exists "+(prev!=null)+
                " next exists "+(next!=null)+
                '}';
    }
}
