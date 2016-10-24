package ShortPath;

import java.util.Arrays;

/**
 * Created by gregrell on 10/23/16.
 */
class ObstacleRange {
    private final int xSize;
    private final int ySize;
    private final int Obstacles;

    final boolean[][] grid;
    private PointObstacleGenerator pointGenerator;
    static boolean exists =false;


    public ObstacleRange(@SuppressWarnings("SameParameterValue") int xSize, @SuppressWarnings("SameParameterValue") int ySize, @SuppressWarnings("SameParameterValue") int obstaclesIn){
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
