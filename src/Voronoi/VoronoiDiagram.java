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
    float X0=0,Y0=0,X1=0,Y1=0;



    public VoronoiDiagram(List<Obstacle> Obstacles, GraphicsContext gc){
        this.gc=gc;
        Q = new PriorityQueue(); //construct a priority queue and put all points in it, it will pop with X in increasing order
        for(Obstacle o:Obstacles){
            Q.add(new SiteEvent((Point)o.getRoot()));
            updateBoundBox((Point)o.getRoot());
        }

        construct();
        finishEdges();



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
            //if intersect
            Point z = new Point(0,0);
            Point zz= new Point(0,0);
            if(VoronoiCalcs.Intersects(e.getLocation(),i,z)){
                if(i.getNext()!=null && !VoronoiCalcs.Intersects(e.getLocation(),i.getNext(),zz)){

                    i.getNext().setPrev(new Arc(i.focus,i,i.getNext()));
                    i.setNext(i.getNext().getPrev());
                }
                else{
                    Arc dupArc = new Arc(i.focus,i);//duplicate of i with previous pointing back to original i
                    i.setNext(dupArc);
                }
                Arc inext=i.getNext();
                if(i.getSegRight()!=null) {
                    inext.setSegRight(new Segment(i.getSegRight().Start));
                }
                //add p between i and i next
                Arc newArc = new Arc(e.getLocation(),i,inext);
                inext.setPrev(newArc);
                i.setNext(newArc);
                i=newArc;// i points to new arc


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

        }

        //if p never intersects an arc then append it to the list
        Arc s;
        //System.out.println("New iteration");
        for(s=root;s.getNext()!=null;s=s.getNext()) {//get to end of list
            //System.out.println(s.toString());
        }
        s.setNext(new Arc(e.getLocation(),s));
        Point start = new Point(X0,0);
        start.setY((s.focus.getY()+s.getNext().focus.getY()) / 2);
        Segment newSeg = new Segment(start);
        s.getNext().setSegLeft(newSeg);
        s.setSegRight(newSeg);


    }

    private void ProcessEvent(Event e){
        if(e.valid){
            Segment s = new Segment(e.getLocation());
            Arc a = e.getArc();
            if(a.getPrev()!=null){
                a.getPrev().setNext(a.getNext());
                a.getNext().setSegRight(s);
                //a.getNext().setSegLeft(s);
            }
            if(a.getNext()!=null){
                a.getNext().setPrev(a.getPrev());
                a.getNext().setSegLeft(s);
                //a.getNext().setSegRight(s);
            }

            // Finish the edges before and after a.
            if (a.getSegLeft()!=null){
                a.getSegLeft().setEnd(e.getLocation());
                segmentList.add(a.getSegLeft());
                System.out.println("Process Event added Segment Left "+a.getSegLeft().toString()+" from event "+e.toString());
            }
            if (a.getSegRight()!=null){
                a.getSegRight().setEnd(e.getLocation());
                segmentList.add(a.getSegRight());
                System.out.println("Process Event added segment Right "+a.getSegRight().toString()+" from event "+e.toString());
            }


            // Recheck circle events on either side of p:
            if (a.getPrev()!=null) checkCircleEvent(a.getPrev(), e.x);
            if (a.getNext()!=null) checkCircleEvent(a.getNext(), e.x);

        }
        //System.out.println(e.toString()+" event was not valid");
        //delete e;
        //remove e from anywhere in the arc list
        removeEvent(e);

    }

    private void removeEvent(Event e){
        for(Arc a=root;a.getNext()!=null;a=a.getNext()){
            if(a.getE()==e){
                a.setE(null);
            }
        }
    }


    private boolean checkCircleEvent(Arc i, double x0){
        //invalidate any old event
        if(i.getE()!=null && i.getE().location.getX()!=x0){
            i.getE().setValid(false);
            i.setEventNull();
        }
        //i.setEventNull();
        if(i.getPrev()==null||i.getNext()==null){
            return false;
        }
        float x;
        Point o;
        Circumcircle cc = new Circumcircle(i.prev.focus,i.focus,i.next.focus);
        o=cc.getCenter();
        x=cc.highestX;
        //System.out.println("Cirle is valid "+cc.valid());
        if(cc.valid() && x>x0){//TODO remove TRUE from IF evaluation
            i.setE(new Event(x,o,i,cc));
            Q.add(i.getE());
            return true;
        }
        return false;
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
