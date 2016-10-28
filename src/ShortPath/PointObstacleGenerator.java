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
    final List <PointObstacle> pointsList;
    private final Random rnd;
    private final int xBound;
    private final int yBound;

    public PointObstacleGenerator(int quantity, int xBound, int yBound){
        this.rnd = new Random();
        this.xBound=xBound;
        this.yBound=yBound;
        pointsList = generatePoints(quantity);
    }


    private List<PointObstacle> generatePoints(int n){
        List <PointObstacle> newPointObstacleList = new ArrayList <PointObstacle>();

        for(int i=0; i<n; i++){
            PointObstacle NewPointObstacle = new PointObstacle(rnd.nextInt(xBound),rnd.nextInt(yBound));
            NewPointObstacle.setRoot(new Point(NewPointObstacle.p.getX(), NewPointObstacle.p.getY()));
            newPointObstacleList.add(NewPointObstacle);
        }
        return newPointObstacleList;
    }

    @SuppressWarnings("unused")
    public List<PointObstacle> getPointsList(){
        return pointsList;
    }




}
