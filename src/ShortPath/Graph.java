package ShortPath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greg on 11/12/2016.
 */
public class Graph {
    List<Point> Vertices = new ArrayList<Point>();
    List<Edge> Edges = new ArrayList<Edge>();
    public Graph(){

    }
    public void addVertex(Point v){
        if(!Vertices.contains(v)) {
            Vertices.add(v);
        }
    }

    public void addEdge(Point p1, Point p2){
        if(Edges.contains(new Edge(p1,p2))||Edges.contains(new Edge(p2,p1))){
            //do nothing, already contains edge
        }
        else{
            //add the edge
            Edges.add(new Edge(p1,p2));
            if(!Vertices.contains(p1)){
                Vertices.add(p1);
            }

            if(!Vertices.contains(p2)){
                Vertices.add(p2);
            }
        }
    }


    public void addEdgeList(List<Edge> edges){
        for(Edge e:edges){
            addEdge(e);
        }
    }

    //need second handle case for if the input is an Edge instead of two points
    public void addEdge(Edge e){
        if(!Edges.contains(e)){
            Edges.add(e);

            if(!Vertices.contains(e.getP1())){
                Vertices.add(e.getP1());
            }

            if(!Vertices.contains(e.getP2())){
                Vertices.add(e.getP2());
            }
        }
    }

    public boolean contains (Point compPoint){
        return Vertices.contains(compPoint);

    }

    public boolean contains (Edge compEdge){
        return Edges.contains(compEdge);
    }


    public List<Point> getVertices() {
        return Vertices;
    }

    public List<Edge> getEdges() {
        return Edges;
    }
}
