package Voronoi3;

import ShortPath.*;

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


        //Voronoi3 VD = new Voronoi3(Points);
        //System.out.println(VD.getDG().getEdges());

        //colinear test
        Point p1 = new Point(153,404);
        Point p2 = new Point(342,515);
        Point p3 = new Point(741,747);

        System.out.println(VectorAlgebra.onSegment(p1,p2,p3));

        System.out.println(VectorAlgebra.distance2pts(p1,p2));
        System.out.println(VectorAlgebra.distance2pts(p2,p3));
        System.out.println(VectorAlgebra.distance2pts(p1,p2)+VectorAlgebra.distance2pts(p2,p3));
        System.out.println(VectorAlgebra.distance2pts(p1,p3));








    }
}
