package VVComplex;


import ShortPath.Point;

/**
 * Created by gregrell on 11/14/16.
 */
public class PointAngleWrapper implements Comparable{
    Point p;
    Double angle;
    public PointAngleWrapper(Point p, Double angle){
        this.p=p;
        this.angle=angle;
    }

    public Point getP() {
        return p;
    }

    public Double getAngle() {
        return angle;
    }

    @Override
    public String toString() {
        return "PointAngleWrapper{" +
                "p=" + p +
                ", angle=" + angle +
                '}';
    }


    @Override
    public int compareTo(Object o) {
        return this.getAngle().compareTo(((PointAngleWrapper)o).getAngle());
    }
}
