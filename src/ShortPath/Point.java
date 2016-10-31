package ShortPath;

/**
 * Created by gregrell on 10/21/16.
 */
class Point {
    int x;
    int y;
    @SuppressWarnings("unused")

    //constructor 1 - default
    public Point(){
        this.x=0;
        this.y=0;
    }



    //constructor 2 - feed in two dimensions as arguments

    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point inverse(){
        return new Point (-x,-y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
