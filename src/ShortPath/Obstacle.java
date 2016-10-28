package ShortPath;

/**
 * Created by gregrell on 10/28/16.
 */
public class Obstacle {
    Point root;

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
