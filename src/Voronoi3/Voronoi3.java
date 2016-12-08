package Voronoi3;

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

    public Voronoi3(List<Obstacle> Obstacles){
        this.ObstaclesIn=Obstacles;
        List<Point> ObstaclePoints = new ArrayList<Point>();
        for(Obstacle o:Obstacles){
            ObstaclePoints.add(o.getRoot());
        }

        DG = new Delaunay(ObstaclePoints);

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
}

//Create a Delaunay graph

//function given three points will check if those points are colinear, if not it will output a triangulation of those points given by three edges