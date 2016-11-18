package VVComplex;


import ShortPath.Point;

/**
 * Created by gregrell on 11/14/16.
 */
public class PointAngleWrapper implements Comparable{
    Point p;
    Double angle;
    Double distToOrigin;
    public PointAngleWrapper(Point p, Double angle, Double distToOrigin){
        this.p=p;
        this.angle=angle;
        this.distToOrigin=distToOrigin;
    }

    public Point getP() {
        return p;
    }

    public Double getAngle() {
        return angle;
    }

    public Double getDistToOrigin() {
        return distToOrigin;
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
        if(this.getAngle()!=((PointAngleWrapper)o).getAngle()) {

            return this.getAngle().compareTo(((PointAngleWrapper) o).getAngle());
        }
        else{
            return this.getDistToOrigin().compareTo(((PointAngleWrapper)o).getDistToOrigin());
        }
    }
}
