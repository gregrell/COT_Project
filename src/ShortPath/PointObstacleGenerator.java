package ShortPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gregrell on 10/23/16.
 *
 */
public class PointObstacleGenerator {
    int numObstacles;
    List <Point> pointsList;
    Random rnd;
    int xBound;
    int yBound;

    public PointObstacleGenerator(int quantity, int xBound, int yBound){
        this.rnd = new Random();
        this.xBound=xBound;
        this.yBound=yBound;
        pointsList = generatePoints(quantity);
    }


    public List<Point> generatePoints (int n){
        List <Point> newPointList = new ArrayList<Point>();

        for(int i=0; i<n; i++){
            newPointList.add(new Point(rnd.nextInt(xBound),rnd.nextInt(yBound),i));
        }
        return newPointList;
    }

    public List<Point> getPointsList(){
        return pointsList;
    }




}
