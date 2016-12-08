package Voronoi3;

import ShortPath.Obstacle;
import ShortPath.Point;
import ShortPath.PointObstacle;
import ShortPath.debug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregrell on 12/7/16.
 */
public class Main {
    public static void main(String[] args){
        PointObstacle a = new PointObstacle (200,200);
        PointObstacle b = new PointObstacle (400,250);
        PointObstacle c = new PointObstacle (300,500);
        PointObstacle d = new PointObstacle (500,600);
        a.setRoot(new Point(700,400));
        b.setRoot(new Point(400,250));
        c.setRoot(new Point(300,500));
        d.setRoot(new Point(500,600));

        List<Obstacle> Points = new ArrayList<Obstacle>();

        Points.clear();
        Points.add(a);
        Points.add(b);
        Points.add(c);
        Points.add(d);


        Voronoi3 VD = new Voronoi3(Points);
        //System.out.println(VD.getDG().getEdges());







    }
}
