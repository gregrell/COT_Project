package ShortPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gregrell on 10/23/16.
 */
class ObstacleRange {
    private final int xSize;
    private final int ySize;
    private List<Obstacle> Obstacles = new ArrayList<Obstacle>();
    private int numObstacles;
    private int maxsize=500;


    private PointObstacleGenerator pointGenerator;
    private PolygonObstacleGenerator polygonGenerator;
    static boolean exists =false;

    List<Polygon> Polygons;
    List<PointObstacle> Points;

    public ObstacleRange(@SuppressWarnings("SameParameterValue") int xSize, @SuppressWarnings("SameParameterValue") int ySize, @SuppressWarnings("SameParameterValue") int numObstacles){
        exists =true;
        this.xSize=xSize;
        this.ySize=ySize;
        this.numObstacles=numObstacles;

        Create();
    }

    public void Create(){
        try {
            Obstacles.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //pointGenerator = new PointObstacleGenerator(numObstacles/2,xSize,ySize);
        polygonGenerator = new PolygonObstacleGenerator(numObstacles,xSize,ySize,maxsize);
        //Points=pointGenerator.getPointsList();
        Polygons=polygonGenerator.getPolygons();
        //Obstacles.addAll(Points);
        Obstacles.addAll(Polygons);
    }

    public List<Obstacle> getObstacles() {
        return Obstacles;
    }

    @Override
    public String toString() {
        return "Obstacle Range " +
                "xSize (in meters)=" + xSize +
                ", ySize (in meters)=" + ySize +
                ", Obstacles=" + numObstacles;
    }

}
