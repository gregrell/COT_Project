package Voronoi;

import ShortPath.Obstacle;
import ShortPath.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by gregrell on 11/21/16.
 */
public class VoronoiDiagram {
    PriorityQueue Q;
    Arc root;
    List<Segment> segmentList = new ArrayList<Segment>();
    GraphicsContext gc;


    public VoronoiDiagram(List<Obstacle> Obstacles, GraphicsContext gc){
        this.gc=gc;
        Q = new PriorityQueue(); //construct a priority queue and put all points in it, it will pop with X in increasing order
        for(Obstacle o:Obstacles){
            Q.add(new SiteEvent((Point)o.getRoot()));
        }

        construct();
        finishEdges();

        //System.out.println(segmentList.size());
        for(Segment se:segmentList){
            System.out.println(se.toString());
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

                gc.strokeText(removed.getLocation().toString(),removed.getLocation().getX()-5,removed.getLocation().getY()-5);//circle point text
                gc.fillRect(removed.getLocation().getX(),removed.getLocation().getY(),2,2);//circle center point fill



                gc.strokeOval(removed.getLocation().getX()-removed.getCc().getRadius(),removed.getLocation().getY()-removed.getCc().getRadius(),2*removed.getCc().getRadius(),2*removed.getCc().getRadius());
                ProcessEvent(removed);
            }
        }
    }

    private void handleSiteEvent(Event e){
        if(root==null){
            root=new Arc(e.getLocation(),null,null);
            System.out.println("Was NULL");

            return;
        }
        //System.out.println("New Iteration");

        for(Arc i=root; i!=null;i=i.getNext()){//traverse linked list
            //System.out.println(i.toString());
            //if intersect
            Point z = new Point(0,0);
            Point zz= new Point(0,0);
            if(VoronoiCalcs.Intersects(e.getLocation(),i,z)){
                //System.out.println("Intersects case");

                //new parabola intersects i, if necessary then duplicate i
                if(i.getNext()!=null && !VoronoiCalcs.Intersects(e.getLocation(),i.getNext(),zz)){
                    //System.out.println("Case 1");

                    i.getNext().setPrev(new Arc(i.focus,i,i.getNext()));
                    i.setNext(i.getNext().getPrev());
                }
                else{
                    //System.out.println("Case 2");

                    i.setNext(new Arc(i.focus,i));

                }
                i.getNext().setSegRight(i.getSegRight());
                //add p between i and i next
                i.getNext().setPrev(new Arc(e.getLocation(),i,i.getNext()));
                i.setNext(i.getNext().getPrev());
                i=i.getNext();// i points to new arc


                 // Add new half-edges connected to i's endpoints.
                 //i->prev->s1 = i->s0 = new seg(z);
                i.getPrev().setSegRight(new Segment(z));
                 //i->next->s0 = i->s1 = new seg(z);
                i.getNext().setSegLeft(new Segment(z));

                //System.out.println(z.toString()+"segment added");


                // Check for new circle events around the new arc:
                checkCircleEvent(i, e.x);
                checkCircleEvent(i.getPrev(), e.x);
                checkCircleEvent(i.getNext(), e.x);

                return;
            }
            //if p never intersects an arc then append it to the list
            Arc s;
            //System.out.println("New iteration");
            for(s=root;s!=null;s=s.getNext()) {//get to end of list
                //System.out.println(z.toString());
            }
            //z.setNext(new Arc(e.getLocation(),z));
            /*
            // Insert segment between p and i
            point start;
            start.x = X0;
            start.y = (i->next->p.y + i->p.y) / 2;
            i->s1 = i->next->s0 = new seg(start);
             */

        }




        //no intersection detected, add arc to end of linked list
        for(Arc i=root; i!=null;i=i.getNext()){
            //System.out.println(i.toString());
            if (i.getNext()==null){
                i.setNext(new Arc(e.getLocation(),i));
                break;
            }
        }



        }

    private void ProcessEvent(Event e){
        if(e.valid){
            Segment s = new Segment(e.getLocation());
            Arc a = e.getArc();
            if(a.getPrev()!=null){
                a.getPrev().setNext(a.getNext());
                a.getNext().setSegRight(s);
            }
            if(a.getNext()!=null){
                a.getNext().setPrev(a.getPrev());
                a.getNext().setSegLeft(s);
            }

            // Finish the edges before and after a.
            if (a.getSegLeft()!=null){
                a.getSegLeft().setEnd(e.getLocation());
                segmentList.add(a.getSegLeft());
                System.out.println("Process Finishing Segment Left "+a.getSegLeft().toString());
            }
            if (a.getSegRight()!=null){
                a.getSegRight().setEnd(e.getLocation());
                segmentList.add(a.getSegRight());
                System.out.println("Process Finishing segment Right "+a.getSegRight().toString());
            }


            // Recheck circle events on either side of p:
            if (a.getPrev()!=null) checkCircleEvent(a.getPrev(), e.x);
            if (a.getNext()!=null) checkCircleEvent(a.getNext(), e.x);

        }
        //System.out.println(e.toString()+" event was not valid");
        //delete e;

    }


    private void checkCircleEvent(Arc i, double x0){
        //invalidate any old event
        if(i.getE()!=null && i.getE().location.getX()!=x0){
            i.getE().setValid(false);
            i.setEventNull();
        }
        //i.setEventNull();
        if(i.getPrev()==null||i.getNext()==null){
            return;
        }
        float x;
        Point o;
        Circumcircle cc = new Circumcircle(i.prev.focus,i.focus,i.next.focus);
        o=cc.getCenter();
        x=cc.highestX;
        //System.out.println("Cirle is valid "+cc.valid());
        if(cc.valid()||true && x>x0){//TODO remove TRUE from IF evaluation
            i.setE(new Event(x,o,i,cc));
            Q.add(i.getE());
        }
    }


    private void finishEdges(){
        float l = 4000;
        for(Arc i=root;i.getNext()!=null;i=i.getNext()){
            if(i.getSegRight()!=null){
                i.getSegRight().setEnd(VoronoiCalcs.parabolaIntersection(i.focus,i.getNext().focus,2*l));
                //segmentList.add(i.getSegRight());
                System.out.println("Finish segment added"+i.getSegRight().toString());
            }
        }
    }


}


/* Voronoi calcs interpreted from online implementation
https://www.cs.hmc.edu/~mbrubeck/voronoi.html
 */

class VoronoiCalcs{

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
