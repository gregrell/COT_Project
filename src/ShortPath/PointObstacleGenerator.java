package ShortPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gregrell on 10/23/16.
 *
 */
class PointObstacleGenerator {
    @SuppressWarnings("unused")
    int numObstacles;
    final List <Point> pointsList;
    private final Random rnd;
    private final int xBound;
    private final int yBound;

    public PointObstacleGenerator(int quantity, int xBound, int yBound){
        this.rnd = new Random();
        this.xBound=xBound;
        this.yBound=yBound;
        pointsList = generatePoints(quantity);
    }


    private List<Point> generatePoints(int n){
        List <Point> newPointList = new ArrayList<Point>();

        for(int i=0; i<n; i++){
            newPointList.add(new Point(rnd.nextInt(xBound),rnd.nextInt(yBound)));
        }
        return newPointList;
    }

    @SuppressWarnings("unused")
    public List<Point> getPointsList(){
        return pointsList;
    }




}
