package Voronoi3;



import ShortPath.Edge;
import ShortPath.Point;
import ShortPath.VectorAlgebra;
import Voronoi.Circumcircle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregrell on 12/8/16.
 */
public class Triangle {
    Point A;
    Point B;
    Point C;
    Point CircumCenter;
    List<Edge> edges = new ArrayList<Edge>();
    List<Edge> incidentEdges = new ArrayList<Edge>();
    private boolean colinear=false;



    public Triangle(Point A, Point B, Point C){
        this.A=A;
        this.B=B;
        this.C=C;
        Circumcircle centerCircle = new Circumcircle(A,B,C);
        CircumCenter =centerCircle.getCenter();
        Edge edgeAB = new Edge(A,B);
        Edge edgeBC = new Edge(B,C);
        Edge edgeCA = new Edge(C,A);

        edgeAB.setOwner(this);
        edgeBC.setOwner(this);
        edgeCA.setOwner(this);

        edges.add(edgeAB);
        edges.add(edgeBC);
        edges.add(edgeCA);
        checkcolinear();

    }


    public Edge[] Edges(){
        Edge[] edgeArray = new Edge[3];
        edgeArray[0]=edges.get(0);
        edgeArray[1]=edges.get(1);
        edgeArray[2]=edges.get(2);
        return edgeArray;
    }

    public boolean compareTo(Triangle compared){
        return(this.A.comparedTo(compared.A) && this.B.comparedTo(compared.B) && this.C.comparedTo(compared.C));
    }

    public Point getCircumCenter() {
        return CircumCenter;
    }

    public List<Edge> getIncidentEdges() {
        return incidentEdges;
    }

    public void setIncidentEdges(List<Edge> incidentEdges) {
        this.incidentEdges = incidentEdges;
    }

    public void setIncidentEdge(Edge incidentEdge){
        if(!incidentEdges.contains(incidentEdge)){
            incidentEdges.add(incidentEdge);
        }
    }

    public void checkcolinear(){
        if(VectorAlgebra.onSegment(A,B,C)||VectorAlgebra.onSegment(A,C,B)){
            colinear=true;
        }
    }

    public boolean isColinear() {
        return colinear;
    }

    public List<Point> getPoints(){
        List<Point> retVal = new ArrayList<Point>();
        retVal.add(A);
        retVal.add(B);
        retVal.add(C);
        return retVal;

    }

    @Override
    public String toString() {
        return "Triangle{" +
                "A=" + A +
                ", B=" + B +
                ", C=" + C +
                '}';
    }
}
