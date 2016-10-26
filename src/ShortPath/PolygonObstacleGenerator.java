package ShortPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gregrell on 10/24/16.
 */
public class PolygonObstacleGenerator {
    int numberOfPolygonObstacles;
    List<Polygon> Polygons;
    Random rnd;

    public PolygonObstacleGenerator(int numberOfPolygonObstacles, int xBound, int yBound){
        Polygons = new ArrayList<Polygon>();
        rnd = new Random();
        this.numberOfPolygonObstacles=numberOfPolygonObstacles;
        for(int i=0;i<numberOfPolygonObstacles;i++){
            Polygon p = new Polygon(150,150,8);
            p.setRoot(new Point(rnd.nextInt(xBound),rnd.nextInt(1000)));
            Polygons.add(p);
        }
    }

    public int getNumberOfPolygonObstacles() {
        return numberOfPolygonObstacles;
    }

    public List<Polygon> getPolygons() {
        return Polygons;
    }
}
