import javafx.scene.paint.Color;
import java.util.List;

public class Obstacle {
    public static final Color COLOR = Color.BLACK;
    private List<Point> points;

    public Obstacle(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }
}
