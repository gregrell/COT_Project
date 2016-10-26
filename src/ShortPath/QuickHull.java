package ShortPath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregrell on 10/26/16.
 *
 *
 * This algorithm modelled after the quick hull algorithm to generate convex hull given a set of points
 * Ref: http://www.cse.yorku.ca/~aaw/Hang/quick_hull/Algorithm.html
 * Ref: https://en.wikipedia.org/wiki/Quickhull
 *
 * To determine which side of the line from A=(x1,y1) to B=(x2,y2) a point P=(x,y) falls on you need to compute the value:-
 * d=(x−x1)(y2−y1)−(y−y1)(x2−x1)

 * If d<0 then the point lies on one side of the line, and if d>0 then it lies on the other side. If d=0 then the point lies exactly line.

 * To see whether points on the left side of
 * the line are those with positive or negative values compute the value for dd for a point you know is to the left of the line, such as (x1−1,y1)(x1−1,y1)
 * and then compare the sign with the point you are interested in. Ref http://math.stackexchange.com/questions/274712/calculate-on-which-side-of-straign-line-is-dot-located
 *
 *
 */
public class QuickHull {
    public static List<Point> Hull;

    //Constructor
    public QuickHull(List<Point> pointSet){
        Hull = new ArrayList<Point>();
        Point minX=pointSet.get(0);
        Point maxX=minX;
        for(Point p:pointSet){
            if(p.getX()<minX.getX()){
                minX=p;
            }

            if(p.getX()>maxX.getX()){
                maxX=p;
            }
        }

        //add to the hull set and remove from the original set
        Hull.add(minX);
        Hull.add(maxX);
        pointSet.remove(minX);
        pointSet.remove(maxX);

        //construct two sets
        List<Point> S1 = new ArrayList<Point>();
        List<Point> S2 = new ArrayList<Point>();
        float d;
        for(Point p:pointSet){
            d=((p.getX()- minX.getX())*(maxX.getY()-minX.getY()))-(p.getY()-minX.getY())*(maxX.getX()-minX.getX());
            if(d<0){
                S1.add(p);
            }
            else if(d>0){
                S2.add(p);
            }
        }
        System.out.println(S1);


    }

    private void FindHull(){



    }




}
