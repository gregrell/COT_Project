package ShortPath;

import java.lang.reflect.Array;
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
    /*
    Input = a set S of n points
   Assume that there are at least 2 points in the input set S of points
   QuickHull (S)
   {
       // Find convex hull from the set S of n points
       Convex Hull := {}
       Find left and right most points, say A & B, and add A & B to convex hull
       Segment AB divides the remaining (n-2) points into 2 groups S1 and S2
           where S1 are points in S that are on the right side of the oriented line from A to B,
           and S2 are points in S that are on the right side of the oriented line from B to A
       FindHull (S1, A, B)
       FindHull (S2, B, A)
   } */

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
        List<Point> S1 = FindAllRight(pointSet,minX,maxX);
        List<Point> S2 = FindAllRight(pointSet,maxX,minX);
        FindHull(S1,minX,maxX);
        FindHull(S2,maxX,minX);




    }



    public static List<Point> getHull() {
        return Hull;
    }
/*
    FindHull (Sk, P, Q)
   {
       // Find points on convex hull from the set Sk of points
       // that are on the right side of the oriented line from P to Q
       If Sk has no point, then return.
       From the given set of points in Sk, find farthest point, say C, from segment PQ
       Add point C to convex hull at the location between P and Q
       Three points P, Q, and C partition the remaining points of Sk into 3 subsets: S0, S1, and S2
           where S0 are points inside triangle PCQ, S1 are points on the right side of the oriented
           line from  P to C, and S2 are points on the right side of the oriented line from C to Q.
       FindHull(S1, P, C)
       FindHull(S2, C, Q)
   }
   Output = convex hull
    */



    private void FindHull(List<Point> Sk, Point P, Point Q){
        double distance=0;
        double tmp_dist=distance;
        Point furthest=null;

        for(Point p:Sk){
            tmp_dist=DistancePointToLine(p,P,Q);
            if(tmp_dist>distance){
                distance=tmp_dist;
                furthest=p;
            }
        }
        if(furthest != null){
            Hull.add(Hull.indexOf(P)+1,furthest);
        }
        if(Sk.size()!=0){

            List<Point> S1 = FindAllRight(Sk,P,furthest);
            List<Point> S2 = FindAllRight(Sk,furthest,Q);
            FindHull(S1,P,furthest);
            FindHull(S2,furthest,Q);
        }

    }

    /* Function FindAllRight produces a list of points that are right of the line produced by PQ*/


    private List<Point> FindAllRight(List<Point> set, Point P, Point Q){
        float d;
        List<Point> S1 = new ArrayList<Point>();
        for(Point p:set){
            d=((p.getX()- P.getX())*(Q.getY()-P.getY()))-(p.getY()-P.getY())*(Q.getX()-P.getX());

            if(d>0){
                S1.add(p);
                //System.out.println(p.getX()+" "+p.getY()+" went right of "+P.getX()+" "+ P.getY()+" to "+Q.getX()+" "+Q.getY());
            }
        }
        return S1;
    }

    /*Find orthogonal Distance
    Line given by two points P1=(x1,y1) P2=(x2,y2) the distance of (x0,y0) from the line is = abs((y2-y1)x0-(x2-x1)y0+x2y1-y2x1)/sqrt(y2-y1)^2+(x2-x1)^2)
    */

    private double DistancePointToLine (Point P0, Point P1, Point P2){
        double distance;
        double numerator;
        double denominator;

        numerator = (P2.y-P1.y)*P0.x-(P2.x-P1.x)*P0.y+P2.x*P1.y-P2.y*P1.x;
        Math.abs(numerator);
        denominator = Math.pow((P2.y-P1.y),2) + Math.pow((P2.x-P1.x),2);
        denominator=Math.sqrt(denominator);

        distance=numerator/denominator;

        return distance;

    }




}
