package ShortPath;

import Voronoi3.Triangle;
import Voronoi3.Vertex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Greg on 11/12/2016.
 */
public class Edge implements Serializable, Comparable{
    public Point p1 = new Point(-1,-1);
    public Point p2 = new Point(-1,-1);
    //public ABCform ABC;
    public boolean incident=false;
    public Triangle owner;
    public List<Triangle> sharerList = new ArrayList<Triangle>();
    public float slope;
    public Float length;
    public Vertex v1;
    public Vertex v2;



    public Edge(Point p1, Point p2){
        this.p1=p1;
        this.p2=p2;
        calcSlope();
        setLength();
        //slope=(p1.y-p2.y)/(p1.x-p2.x);
        //ABC=new ABCform(p1,p2);
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    /*public ABCform getABC() {
        return ABC;
    }*/

    public boolean isIncident() {
        return incident;
    }

    public boolean isEqual(Edge compare){
        return ((this.getP1().comparedTo(compare.getP1())&&this.getP2().comparedTo(compare.getP2())) || (this.getP1().comparedTo(compare.getP2())&&this.getP2().comparedTo(compare.getP1())));
    }

    public void setIncident(boolean incident) {
        this.incident = incident;
    }

    public Triangle getOwner() {
        return owner;
    }

    public void setOwner(Triangle owner) {
        this.owner = owner;
    }

    public void addSharer(Triangle sharer){
        sharerList.add(sharer);
    }

    public List<Triangle> getSharerList() {
        return sharerList;
    }

    public void extendP2(float length){
        /*C.x = B.x + (B.x - A.x) / lenAB * length;
        C.y = B.y + (B.y - A.y) / lenAB * length;*/
        float cx=p2.x + (p2.x - p1.x)/length*(float)VectorAlgebra.distance2pts(p1,p2);
        float cy=p2.y + (p2.y - p1.y)/length*(float)VectorAlgebra.distance2pts(p1,p2);

        p2.setX(cx);
        p2.setY(cy);
        setLength();

    }

    public float calculateY(float testX){
        //y-y1=m(x-x1)
        return(slope*(testX-p1.x))+p1.y;

    }

    public float calculateX(float testY){
        //y-y1=m(x-x1) therefore x=((y-y1)/m)+x1
        return(((testY-p1.y)/slope)+p1.x);
    }

    private void calcSlope(){
        /*List<Point> segments = new ArrayList<Point>();
        segments.add(p1);
        segments.add(p2);
        Collections.sort(segments);
        Point A = segments.get(0);
        Point B = segments.get(1);*/
        slope=(p2.y-p1.y)/(p2.x-p1.x);
    }

    public float getSlope() {
        return slope;
    }

    private void setLength(){
        length=(float)VectorAlgebra.distance2pts(p1,p2);
    }

    //public float computeY(float testX){
        //return ABC.computeY(testX);
    //}


    public Vertex getV1() {
        return v1;
    }

    public void setV1(Vertex v1) {
        this.v1 = v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public void setV2(Vertex v2) {
        this.v2 = v2;
    }

    public Float getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.getLength().compareTo(((Edge)o).getLength());
    }
}











/*
class ABCform implements Serializable {
    float A;
    float B;
    float C;
    float slope;
    boolean slopeInf=false;
    ABCform(Point p1, Point p2){
        slope=((float)p2.y-(float)p1.y)/((float)p2.x-(float)p1.x);
        if (slope==Double.POSITIVE_INFINITY) {
            A = 1;
            B = 0;
            C = p1.getX();
            slopeInf=true;
        }
        else {

            A = -slope;
            B = 1;
            C = -slope * p1.x + p1.y;
        }

    }

    private int lcm(float a){
        int lcm=0;
        float multipliedby;
        for(int i=1;i<100;i++){
            multipliedby=a*i;
            int intpart=(int)multipliedby;
            float decpart = intpart-multipliedby;
            if(decpart==0){
                lcm=i;
                break;
            }

        }
        return lcm;
    }

    private boolean fractional(float a){
        int intpart=(int)a;
        float decpart = intpart-a;
        return(!(decpart==0));
    }

    //public boolean slopeInf(){
        return slopeInf;
    }




    @Override
    public String toString() {
        return "ABCform{" +
                "A=" + A +
                ", B=" + B +
                ", C=" + C +
                '}';
    }



}
*/
