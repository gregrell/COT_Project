package Voronoi;

import ShortPath.Obstacle;
import ShortPath.Point;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by gregrell on 11/21/16.
 */
public class VoronoiDiagram {
    PriorityQueue Q;


    public VoronoiDiagram(List<Obstacle> Obstacles){
        Q = new PriorityQueue(); //construct a priority queue and put all points in it, it will pop with Y in increasing order
        for(Obstacle o:Obstacles){
            Q.add(new SiteEvent((Point)o.getRoot()));

        }


        construct();

    }


    private void construct(){
        while (Q.size()!=0){
            Event removed = (Event)Q.remove();
            if(removed instanceof SiteEvent){
                System.out.println("Site Event "+removed.toString());
                //handle site event
            }
            else{
                System.out.println("Circle Event "+removed.toString());
                //handle circle event
            }
        }

    }

    private void handleSiteEvent(Event e){

    }

    private void handleCircleEvent(Event e){

    }


}
