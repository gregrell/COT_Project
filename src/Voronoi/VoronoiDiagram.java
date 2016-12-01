package Voronoi;

import ShortPath.Obstacle;
import ShortPath.Point;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by gregrell on 11/21/16.
 */
public class VoronoiDiagram {
    public VoronoiDiagram(List<Obstacle> Sites){
        PriorityQueue Q = new PriorityQueue(); //construct a priority queue and put all points in it, it will pop with Y in increasing order
        Q.addAll(Sites);
        System.out.println(Q);


    }

}
