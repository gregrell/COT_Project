package Voronoi3;

import ShortPath.Edge;
import ShortPath.Point;
import Voronoi.Circumcircle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gregrell on 12/7/16.
 */
public class Delaunay {
    List<Point> Points;
    List<Triangle> Triangles = new ArrayList<Triangle>();

    public Delaunay(List<Point> Points){
        this.Points=Points;
        compile();

    }

    public void compile(){
        Triangles.clear();// clear the triangles list because they are about to be recalculated

        //take the n^4 approach and figure out the delauney triangulation of everything.
        for(Point i:Points){
            for(Point j:Points){
                for(Point k:Points){
                    if(i==j||i==k||j==k) continue;
                    List<Point> orderedIJK = new ArrayList<Point>();
                    orderedIJK.clear();
                    boolean inCircle=false;

                    orderedIJK.add(i);
                    orderedIJK.add(j);
                    orderedIJK.add(k);

                    Collections.sort(orderedIJK); // order the ijk to prevent duplicates
                    Circumcircle newCircle = new Circumcircle(orderedIJK.get(0),orderedIJK.get(1),orderedIJK.get(2));

                    for(Point insideCheck:Points){// check if any other point exists in the circumcircle
                        if(insideCheck==i||insideCheck==j||insideCheck==k) continue;// break this iteration because we don't need to check the inside check condition of one of the vertices


                        if(newCircle.inCircle(insideCheck)){
                            inCircle=true;
                            break;
                        }
                    }

                    if(!inCircle){
                        Triangle newTriangle = new Triangle(orderedIJK.get(0),orderedIJK.get(1),orderedIJK.get(2));
                        boolean alreadyExists=false;
                        for(Triangle tr:Triangles){
                            if(tr.compareTo(newTriangle)){
                                alreadyExists=true;
                            }

                        }

                        if(!alreadyExists) {
                            System.out.println("add triangle "+newTriangle.toString());
                            Triangles.add(newTriangle);
                        }

                    }
                    else; //System.out.println("point exists in circle "+newCircle.toString());




                }
            }

        }

    }

    public List<Triangle> getTriangles() {
        return Triangles;
    }

    public List<Edge> getEdges(){
        List<Edge> edges = new ArrayList<Edge>();
        for(Triangle t:Triangles){
            edges.addAll(java.util.Arrays.asList(t.Edges()));
        }
        return edges;
    }
}








class Triangle{
    Point A;
    Point B;
    Point C;
    public Triangle(Point A, Point B, Point C){
        this.A=A;
        this.B=B;
        this.C=C;

    }


    public Edge[] Edges(){
        Edge[] edges = new Edge[3];
        edges[0]=new Edge(A,B);
        edges[1]=new Edge(B,C);
        edges[2]=new Edge(C,A);
        return edges;
    }

    public boolean compareTo(Triangle compared){
        return(this.A.comparedTo(compared.A) && this.B.comparedTo(compared.B) && this.C.comparedTo(compared.C));
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
