package ShortPath;

import java.util.*;

/**
 * Created by gregrell on 10/24/16.
 */
public class Polygon extends Obstacle {
    List<Point> points;
    List<Point> Hull;
    List<Edge>  Edges = new ArrayList<Edge>();
    QuickHull qh;
    Random rnd;

    /*Constructor - generate as many points as fed into the contstructor for the polygon, then sort them in order to generate convex hull*/

    public Polygon(int MaxWidth, int MaxHeight, int MaxPoints){
        points = new ArrayList<Point>();

        rnd=new Random();
        int numPoints = (int)(rnd.nextFloat()*(MaxPoints-4)+4);

        for(int i=0;i<numPoints;i++){
            Point p = new Point(rnd.nextInt(MaxWidth),rnd.nextInt(MaxHeight));
            points.add(p);
        }

        qh=new QuickHull(points);
        Hull=qh.getHull();
        setEdges(Hull);

    }

    public List<Point> getHull(){
        List<Point> tempHull=new ArrayList<Point>();
        tempHull.clear();
        for(Point P:Hull){
            Point newPoint = new Point(P.getX()+root.getX(),P.getY()+root.getY());
            tempHull.add(newPoint);
        }
        return tempHull;
    }


    public List<Point> getPoints() {
        return points;
    }

    private void setEdges(List<Point> inHull){
        for(int i=0;i<inHull.size()-1;i++){
            Edges.add(new Edge(inHull.get(i),inHull.get(i+1)));
        }
        Edges.add(new Edge(inHull.get(inHull.size()-1),inHull.get(0)));
    }

    public List<Edge> getEdges() {
        return Edges;
    }

    public int[] getXpoints(){
        int i=0;
        int[] xPoints = new int[points.size()];

        for(Point p:points){
            xPoints[i]=p.x;

            i++;
        }
        return xPoints;
    }

    public int[] getYpoints(){
        int i=0;

        int[] yPoints = new int[points.size()];
        for(Point p:points){

            yPoints[i]=p.y;
            i++;
        }
        return yPoints;
    }

    public double[] getXpointsAsDouble(){
        int i=0;
        double[] xPointsDbl = new double[points.size()];

        for(Point p:points){
            xPointsDbl[i]= ((double) p.x) + root.x;

            i++;
        }
        return xPointsDbl;
    }

    public double[] getYpointsAsDouble(){
        int i=0;
        double[] yPointsDbl = new double[points.size()];

        for(Point p:points){
            yPointsDbl[i]= ((double) p.y) + root.y;

            i++;
        }

        return yPointsDbl;
    }

    public double[] getHullXpointsAsDouble(){
        int i=0;
        double[] HullxPointsDbl = new double[Hull.size()];

        for(Point p:Hull){
            HullxPointsDbl[i]= ((double) p.x) + root.x;

            i++;
        }
        return HullxPointsDbl;
    }

    public double[] getHullYpointsAsDouble(){
        int i=0;
        double[] HullyPointsDbl = new double[Hull.size()];

        for(Point p:Hull){
            HullyPointsDbl[i]= ((double) p.y) + root.y;

            i++;
        }
        return HullyPointsDbl;
    }








}
