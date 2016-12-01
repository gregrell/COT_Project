package ShortPath;

/**
 * Created by gregrell on 10/28/16.
 */
public class PointObstacle extends Obstacle implements Comparable{
    Point p;
    public PointObstacle(int x, int y){
        p=new Point(x,y);
    }

    public Point getP() {
        return p;
    }

    @Override
    public int compareTo(Object o) {
        return this.getP().compareTo(((PointObstacle)o).getP());
    }
}
