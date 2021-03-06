package Voronoi3;

import ShortPath.Edge;
import ShortPath.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregrell on 12/9/16.
 */
public class Vertex implements Comparable{
    Point p;
    public List<Edge> edges = new ArrayList<Edge>();

    public Vertex(Point p, Edge e){
        this.p=p;
        edges.add(e);

    }

    public Point getP() {
        return p;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public int compareTo(Object o) {
        return this.getP().compareTo(((Vertex)o).getP());
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "p=" + p +
                '}';
    }
}
