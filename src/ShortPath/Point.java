package ShortPath;

import java.io.Serializable;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

/**
 * Created by gregrell on 10/21/16.
 */
public class Point implements Serializable, Comparable{
    float x;
    float y;
    @SuppressWarnings("unused")

    //constructor 1 - default
    public Point(){
        this.x=0;
        this.y=0;
    }



    //constructor 2 - feed in two dimensions as arguments

    public Point(float x, float y){
        this.x=x;
        this.y=y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Point inverse(){
        return new Point (-x,-y);
    }

    public boolean comparedTo(Point testPt){
        return(this.getX()==testPt.getX() && this.getY()==testPt.getY());
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(this.getX()!=((Point)o).getX()) {
            return Float.compare(this.getX(), ((Point) o).getX());
        }
        else return Float.compare(this.getY(), ((Point)o).getY());

    }
}
