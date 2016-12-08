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
    List<Edge> Edges = new ArrayList<Edge>();
    List<Edge> nonIncidentEdges = new ArrayList<Edge>();

    public Delaunay(List<Point> Points){
        this.Points=Points;
        compile();
        setTriangleIncidentEdges();
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

                        if(!alreadyExists&& !newTriangle.isColinear()) {
                            //System.out.println("add triangle "+newTriangle.toString());
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
        for(Triangle t:Triangles){
            for(Edge e:t.Edges()){
                boolean exists=false;
                for(Edge edgelistE:Edges){
                    if(e.isEqual(edgelistE)) exists=true;
                }
                //System.out.println(e.getOwner());
                if(!exists) Edges.add(e);
                if(!e.isIncident()) nonIncidentEdges.add(e);
            }

        }
        return Edges;
    }

    public List<Edge> getNonIncidentEdges() {
        return nonIncidentEdges;
    }

    private void setTriangleIncidentEdges(){
        for(Triangle t1:Triangles){
            for(Triangle t2:Triangles){
                if(t1==t2) continue;
                for(Edge t2Edge:t2.Edges()){
                    for(Edge t1Edge:t1.Edges()){
                        if(t2Edge.isEqual(t1Edge)){
                            t1Edge.setIncident(true);
                            t1Edge.addSharer(t2Edge.getOwner());
                            t2Edge.setIncident(true);
                            t2Edge.addSharer(t1Edge.getOwner());
                        }
                    }
                }


            }
        }
    }







}








