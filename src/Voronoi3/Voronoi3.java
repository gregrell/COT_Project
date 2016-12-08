package Voronoi3;

import ShortPath.Edge;
import ShortPath.Obstacle;
import ShortPath.Point;
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
                if(e.isIncident()){

                    edges.add(new Edge(e.getOwner().getCircumCenter(),e.getSharerList().get(0).getCircumCenter()));
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