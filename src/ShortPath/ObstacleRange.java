package ShortPath;

import java.util.Arrays;

/**
 * Created by gregrell on 10/23/16.
 */
public class ObstacleRange {
    int xSize;
    int ySize;
    int Obstacles;

    boolean[][] grid;
    PointObstacleGenerator pointGenerator;
    static boolean exists =false;


    public ObstacleRange(int xSize, int ySize, int obstaclesIn){
        exists =true;
        this.xSize=xSize;
        this.ySize=ySize;
        this.Obstacles=obstaclesIn;

        grid = new boolean[xSize][ySize];
        pointGenerator = new PointObstacleGenerator(Obstacles,xSize,ySize);
        populateGrid();
    }

    public void reCreate(){
        clearGrid();
        pointGenerator = new PointObstacleGenerator(Obstacles,xSize,ySize);
        populateGrid();
    }

    private void populateGrid() {
        for(Point p:pointGenerator.pointsList){
            grid[p.x][p.y]=true;
        }
    }

    private void clearGrid(){
        for(int i=0;i<grid.length;i++){
            Arrays.fill(grid[i],false);
        }
    }

    @Override
    public String toString() {
        return "Obstacle Range " +
                "xSize (in meters)=" + xSize +
                ", ySize (in meters)=" + ySize +
                ", Obstacles=" + Obstacles;
    }
}
