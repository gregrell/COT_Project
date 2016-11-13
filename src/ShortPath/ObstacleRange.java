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
    private int maxsize=Main.MaxObstacleSize; // maximum square size of the obstacle


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

        polygonGenerator = new PolygonObstacleGenerator(numObstacles,xSize-maxsize,ySize-maxsize,maxsize);

        Polygons=polygonGenerator.getPolygons();


        //TODO here we need to implement GJK algorithm https://www.youtube.com/watch?v=Qupqu1xe7Io for collision detection of convex hulls. Compare it to teh set of Obstacles and if no collision detected then add to set
        //A very good reference is http://www.cs.sjsu.edu/faculty/pollett/masters/Semesters/Spring12/josh/GJK.html

        Obstacles.addAll(Polygons);
    }

    public List<Obstacle> getObstacles() {
        return Obstacles;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        Obstacles = obstacles;
    }

    @Override
    public String toString() {
        return "Obstacle Range " +
                "xSize (in meters)=" + xSize +
                ", ySize (in meters)=" + ySize +
                ", Obstacles=" + numObstacles;
    }

}
