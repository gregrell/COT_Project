package Voronoi;

import ShortPath.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregrell on 12/1/16.
 */


public class Arc extends BLitem {
    Point focus;
    float directix;
    String formula;
    float a;
    float b;
    float c;
    List<Point> sweep = new ArrayList<Point>();
    Event e;

    public Arc(Point focus, float directix, Event e){
        super(focus);
        this.e=e;
        this.directix=directix;
        this.focus=focus;
        a=focus.getX();
        b=focus.getY();
        c=directix;
        formula ="y=(x-"+a+")^2+"+b+"^2 - "+c+"^2 /2("+b+"-"+c+")";
        dosweep();
    }

    private void dosweep(){
        for (int i=0;i<5000;i++){
            float y = ((float)Math.pow(((float)i-a),2)+((float)Math.pow(b,2))-((float)Math.pow(c,2)))/(2*(b-c));
            Point p = new Point((float)i,y);
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
}
