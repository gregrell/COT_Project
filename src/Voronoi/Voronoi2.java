package Voronoi;

import ShortPath.Obstacle;
import ShortPath.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by gregrell on 12/4/16.
 */
public class Voronoi2 {

    PriorityQueue Q;
    Arc root;
    List<Segment> segmentList = new ArrayList<Segment>();
    GraphicsContext gc;
    float X0=0,Y0=0,X1=0,Y1=0;



    public Voronoi2(List<Obstacle> Obstacles, GraphicsContext gc){
        this.gc=gc;
        Q = new PriorityQueue(); //construct a priority queue and put all points in it, it will pop with X in increasing order
        for(Obstacle o:Obstacles){
            Q.add(new SiteEvent((Point)o.getRoot()));
            updateBoundBox((Point)o.getRoot());
        }

        construct();
        //finishEdges();



    }

    private void updateBoundBox(Point p){
        if(p.getX()<X0){
            X0=p.getX();
        }
        if(p.getX()>X1){
            X1=p.getX();
        }
        if(p.getY()<Y0){
            Y0=p.getY();
        }
        if(p.getY()>Y1){
            Y1=p.getY();
        }
    }


    public List<Segment> getSegmentList() {
        return segmentList;
    }

    private void construct(){
        while (Q.size()!=0){
            Event removed = (Event)Q.remove();
            if(removed instanceof SiteEvent){
                //System.out.println("Site Event "+removed.toString());
                //handle site event
                gc.setFill(Color.WHITE);
                gc.setStroke(Color.WHITE);


                gc.strokeText(removed.getLocation().toString(),removed.getLocation().getX()-5,removed.getLocation().getY()-5);

                gc.fillRect(removed.getLocation().getX(),removed.getLocation().getY(),2,2);

                handleSiteEvent(removed);

            }
            else{
                //System.out.println("Circle Event "+removed.toString());
                //handle circle event

                gc.setStroke(Color.VIOLET);
                gc.setFill(Color.VIOLET);

                // gc.strokeText(removed.getLocation().toString(),removed.getLocation().getX()-5,removed.getLocation().getY()-5);//circle point text
                //gc.fillRect(removed.getLocation().getX(),removed.getLocation().getY(),2,2);//circle center point fill



                gc.strokeOval(removed.getLocation().getX()-removed.getCc().getRadius(),removed.getLocation().getY()-removed.getCc().getRadius(),2*removed.getCc().getRadius(),2*removed.getCc().getRadius());
                ProcessEvent(removed);
            }
        }
    }

    private void handleSiteEvent(Event e){
        if(root==null){
            root=new Arc(e.getLocation(),null,null);
            return;
        }
        //System.out.println("New Iteration");

        for(Arc i=root; i!=null;i=i.getNext()){//traverse linked list
            //find an arc that the new site event intersects with
            Point intersectPt=new Point(0,0);
            if(VCalcs.Intersects(e.getLocation(),i,intersectPt)){
                //it intersects here, check if the arc is connected to a circle event. If so then remove that event from the Q
                if(!(i.getE() instanceof SiteEvent)){
                    Q.remove(i.getE());
                }
                //create three new arcs a,b,c
                Arc a,c;
                Arc b = new Arc(e.location,e.location.getX(),e);
                a=Arc.clone(i);
                c=Arc.clone(i);
                Segment xl=new Segment(intersectPt);
                Segment xr=new Segment(intersectPt);
                //replace i by the sequence a, xl, b, xr, c
                a.setSegLeft(xl);
                b.setSegLeft(xl);
                b.setSegRight(xr);
                c.setSegRight(xr);

                a.setNext(b);
                c.setPrev(b);
                b.setNext(c);
                b.setPrev(a);

                Arc iPrev = i.getPrev();
                Arc iNext = i.getNext();
                i.prev.setNext(a);
                i.next.setPrev(c);
                checkCircleEvent(a);
                checkCircleEvent(c);


            }



        }

    }

    private void ProcessEvent(Event e){


    }



    private void checkCircleEvent(Arc p){
        //l is arc on left of p;
        Arc l = p.getPrev();
        //r is arc on right of p
        Arc r = p.getNext();
        //if l or r are attached to any circle events then remove them from Q
        if(!(l.getE() instanceof SiteEvent)){
            Q.remove(l.getE());
        }
        if(!(r.getE() instanceof SiteEvent)){
            Q.remove(r.getE());
        }

        //xl and xr are edges by the p
        Segment xl = p.segLeft;
        Segment xr = p.segRight;
        if(xl==null||xr==null) {//missing if xl.site==xr.site
            return;
        }


    }


    private void finishEdges(){
        float l = X1+(X1-X0)+(Y1-Y0);
        for(Arc i=root;i.getNext()!=null;i=i.getNext()){
            if(i.getSegRight()!=null){
                i.getSegRight().setEnd(VoronoiCalcs.parabolaIntersection(i.focus,i.getNext().focus,l*2));
                segmentList.add(i.getSegRight());
                System.out.println("Finish segment added"+i.getSegRight().toString());
            }
        }
    }


}


/* Voronoi calcs interpreted from online implementation
https://www.cs.hmc.edu/~mbrubeck/voronoi.html
 */

class VCalcs{

    public static boolean Intersects (Point p, Arc i, Point result){
        //Point result = new Point(0,0);
        if(i.focus.getX()==p.getX()){
            return false;
        }
        float a=0,b=0;
        if(i.getPrev()!=null){
            a=parabolaIntersection(i.getPrev().focus,i.focus, p.getX()).getY();
        }
        if(i.getNext()!=null){
            b=parabolaIntersection(i.focus,i.getNext().focus,p.getX()).getY();
        }
        if((i.getPrev()==null||a<=p.getY()) && (i.getNext()==null|| p.getY()<= b)){
            result.setY(p.getY());
            float xresult=(((float)Math.pow(i.focus.getX(),2) + ((float)Math.pow(i.focus.getY() - result.getY(),2)) - ((float)Math.pow(p.getX(),2)))/(2*i.focus.getX()-2*p.getX()));
            //System.out.println("Somemath = "+xresult);
            result.setX(xresult);
            return true;
        }
        return false;
    }




    //Determine the point of intersection between two parabolas given focus A, focus B and directix
    public static Point parabolaIntersection (Point focusA, Point focusB, float directix){
        Point result=new Point(0,0);
        Point p=focusA;

        if(focusA.getX()==focusB.getX()){
            result.setY((focusA.getY()+focusB.getY())/2);
        }
        if(focusB.getX()==directix){
            result.setY(focusB.getY());
        }
        if(focusA.getX()==directix){
            result.setY(focusA.getY());
            p=focusB;

        }
        else{
            /*double z0 = 2*(p0.x - l);
      double z1 = 2*(p1.x - l);

      double a = 1/z0 - 1/z1;
      double b = -2*(p0.y/z0 - p1.y/z1);
      double c = (p0.y*p0.y + p0.x*p0.x - l*l)/z0
               - (p1.y*p1.y + p1.x*p1.x - l*l)/z1;

      res.y = ( -b - sqrt(b*b - 4*a*c) ) / (2*a);*/
            float z0=2*(focusA.getX()-directix);
            float z1=2*(focusB.getX()-directix);
            float a=(1/z0)-(1/z1);
            float b=-2*((focusA.getY()/z0) - (focusB.getY()/z1));
            float c=(focusA.getY()*focusA.getY() + focusA.getX()*focusA.getX() -directix*directix)/z0 - (focusB.getY()*focusB.getY() + focusB.getX()*focusB.getX() -directix*directix)/z1;

            result.setY(-b - (float)Math.sqrt((double)(b*b - 4*a*c)) / 2*a);
        }
        // Plug back into one of the parabola equations.
        //res.x = (p.x*p.x + (p.y-res.y)*(p.y-res.y) - l*l)/(2*p.x-2*l);
        result.setX((p.getX()*p.getX() + (p.getY()-result.getY())*(p.getY()-result.getY()) - directix*directix)/(2*p.getX()-2*directix));
        //System.out.println("result of parabolaintersection "+result.toString());
        return result;
    }
}




