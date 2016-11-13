package ShortPath;

import java.io.Serializable;

/**
 * Created by gregrell on 10/28/16.
 */
public class Obstacle implements Serializable{
    Point root=new Point(0,0);

    public Point getRoot() {
        return root;
    }

    public void setRoot(Point root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "Obstacle{" +
                "root=" + root +
                '}';
    }
}
