package VVComplex;

import ShortPath.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Greg on 11/12/2016.
 *
 * Algorithm VISIBILITYGRAPH(S)
     Input. A set S of disjoint polygonal obstacles.
     Output. The visibility graph Gvis(S).
     1. Initialize a graph G = (V,E) where V is the set of all vertices of the
     polygons in S and E = /0.
     2. for all vertices v ∈ V
     3. do W ← VISIBLEVERTICES(v,S)
     4. For every vertex w ∈ W, add the arc (v,w) to E.
     5. return G


 VISIBLEVERTICES(p,S)
     Input. A set S of polygonal obstacles and a point p that does not lie in the
     interior of any obstacle.
     Output. The set of all obstacle vertices visible from p.
     1. Sort the obstacle vertices according to the clockwise angle that the halfline
     from p to each vertex makes with the positive x-axis. In case of
     ties, vertices closer to p should come before vertices farther from p. Let
     w1,...,wn be the sorted list.
     2. Let ρ be the half-line parallel to the positive x-axis starting at p. Find
     the obstacle edges that are properly intersected by ρ, and store them in a
     balanced search tree T in the order in which they are intersected by ρ.
     3. W ← /0
     4. for i ← 1 to n
     5. do if VISIBLE(wi) then Add wi to W.
     6. Insert into T the obstacle edges incident to wi that lie on the clockwise
     side of the half-line from p to wi.
     7. Delete from T the obstacle edges incident to wi that lie on the
     counterclockwise side of the half-line from p to wi.
     8. return W

 VISIBLE(wi)
     1. if pwi intersects the interior of the obstacle of which wi is a vertex, locally
     at wi
     2. then return false
     3. else if i = 1 or wi−1 is not on the segment pwi
     4. then Search in T for the edge e in the leftmost leaf.
     5. if e exists and pwi intersects e
     6. then return false
     7. else return true
     8. else if wi−1 is not visible
     9. then return false
     10. else Search in T for an edge e that intersects wi−1wi.
     11. if e exists
     12. then return false
     13. else return true
 */


public class VisibilityGraph {
    private Graph G;
    List<Double> angles = new ArrayList<Double>();
    List<Point> V;

    public VisibilityGraph(){
        G = new Graph();
    }

    public void addObstacles(List<Obstacle> O){

        for (Object i :O) {
            if(i instanceof Polygon) {
                Polygon j = (Polygon) i; //downcast to subclass
                G.addEdgeList(j.getEdges());
            }
        }
    }

    public Graph getGraph() {
        return G;
    }



    public List<Edge> VisibleVertices(Point p, List<Obstacle> O){
        angles.clear();
        V = new ArrayList<Point>();
        List<Edge> radialEdges = new ArrayList<Edge>();
        for(Obstacle o:O){
            o=(Polygon)o;
            for(Point q:((Polygon) o).getHull()){
                V.add(q);
            }

        }
        //find angle between point p to all vertices in O and p to x->oo
        //find the distance between p and all vertices in O in case of a tie
        //sort a list based on increasing clockwise angle


        for(Point q:V){
            radialEdges.add(new Edge(p,q));
            angles.add(VectorAlgebra.findAngle(p,q));
        }

        return radialEdges;
    }

    public List<Double> getAngles() {
        return angles;
    }

    public List<Point> getV() {
        return V;
    }
}
