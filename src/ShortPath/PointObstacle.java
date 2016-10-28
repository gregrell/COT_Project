package ShortPath;

/**
 * Created by gregrell on 10/28/16.
 */
public class PointObstacle extends Obstacle{
    Point p;
    public PointObstacle(int x, int y){
        p=new Point(x,y);
    }
}
