package Voronoi3;

import ShortPath.*;
import Voronoi.VoronoiDiagram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregrell on 12/7/16.
 */
public class Voronoi3 {
    private List<Obstacle> ObstaclesIn;
    Delaunay DG;
    public List<Edge> edges = new ArrayList<Edge>();

    public Voronoi3(List<Obstacle> Obstacles){
        this.ObstaclesIn=Obstacles;
        List<Point> ObstaclePoints = new ArrayList<Point>();
        for(Obstacle o:Obstacles){
            ObstaclePoints.add(o.getRoot());
        }

        DG = new Delaunay(ObstaclePoints);
        compileEdges();
        trimEdges();

    }

    public List<Point> getPoints(){
        List<Point> pointlist = new ArrayList<Point>();
        for(Obstacle o:ObstaclesIn){
            pointlist.add(o.getRoot());
        }
        return pointlist;
    }

    public Delaunay getDG() {
        return DG;
    }

    private void compileEdges(){
        for(Triangle tri:DG.getTriangles()){
            for(Edge e:tri.Edges()){
                if(e.isIncident()){         //add the edge between the triangle center to the center of any triangle that shares an edge with this one

                    edges.add(new Edge(e.getOwner().getCircumCenter(),e.getSharerList().get(0).getCircumCenter()));
                }
                else {                      //the edge is not incident. Check to see if the circumcenter is bound within the triangle. if it is then we need a segment that propogates out
                    //use quickhull with the triangle corners and the circumcenter, if the hull contains 3 points then its in the triangle, if not then it lies outside of it

                    List<Point> qhPoints = tri.getPoints();
                    qhPoints.add(tri.getCircumCenter());
                    QuickHull qh = new QuickHull(qhPoints);

                    if(qh.getHull().size()==3) {
                        Edge newEdge = new Edge(e.getOwner().getCircumCenter(), VectorAlgebra.midpoint(e.getP1(), e.getP2()));
                        //System.out.println("original "+newEdge);
                        newEdge.extendP2(1);
                        //System.out.println("Extended "+newEdge);
                        edges.add(newEdge);
                    }
                    else{
                        //System.out.println(qh.getHull().size());
                    }
                }
            }
        }
    }

    private void trimEdges(){ //trim any edge that is below x=0 or above x=1500 or less than y=0 or greater than y=1000
        for(Edge e:edges){
            if(e.getP1().getX()>1500 || e.getP1().getX()<0){
                //fix p1 to get x in bound
                if(e.p1.getX()<0){
                    e.p1.setY(e.calculateY(0));
                    e.p1.setX(0);
                    //System.out.println(e);
                }
                if(e.p1.getX()>1500){
                    e.p1.setY(e.calculateY(1500));
                    e.p1.setX(1500);
                }

            }
            if(e.getP2().getX()>1500 || e.getP2().getX()<0) {
                //fix p2 to get x in bound
                if(e.p2.getX()>1500){
                    //System.out.println(e);
                    e.p2.setY(e.calculateY(1500));
                    e.p2.setX(1500);
                    //System.out.println(e);

                }
                if(e.p2.getX()<0){
                    e.p2.setY(e.calculateY(0));
                    e.p2.setX(0);
                }


            }///////////

            if(e.getP1().getY()>1500 || e.getP1().getY()<0){
                //fix p1 to get x in bound
                if(e.p1.getY()<0){
                    e.p1.setX(e.calculateX(0));
                    e.p1.setY(0);
                    //System.out.println(e);
                }
                if(e.p1.getY()>1500){
                    e.p1.setX(e.calculateX(1500));
                    e.p1.setY(1500);
                }

                if(e.getP2().getY()>1500 || e.getP2().getY()<0) {
                    //fix p2 to get x in bound
                    if (e.p2.getY() > 1500) {
                        //System.out.println(e);
                        e.p2.setX(e.calculateX(1500));
                        e.p2.setY(1500);
                        //System.out.println(e);

                    }
                    if (e.p2.getY() < 0) {
                        e.p2.setX(e.calculateX(0));
                        e.p2.setY(0);
                    }
                }

            }


        }

    }

    public List<Edge> getEdges() {
        return edges;
    }
}

//Create a Delaunay graph

//function given three points will check if those points are colinear, if not it will output a triangulation of those points given by three edges