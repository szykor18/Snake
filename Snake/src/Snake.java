import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    public static final Color COLOR = Color.CORNSILK;
    public static final Color DEAD = Color.RED;
    private Board board;
    private int length;
    private boolean safe;
    private List<Point> points;
    private Point head;
    private int xVelocity;
    private int yVelocity;
    private boolean isSafe;

    public Snake(Board board, Point initialPoint) {
        length = 1;
        points = new LinkedList<>();
        points.add(initialPoint);
        head = initialPoint;
        safe = true;
        this.board = board;
        xVelocity = 0;
        yVelocity = 0;
        this.isSafe = true;
    }

    private void growTo(Point point) {
        length++;
        checkAndAdd(point);
    }

    private void shiftTo(Point point) {
        checkAndAdd(point);
        points.remove(0);
    }

    private void checkAndAdd(Point point) {
        point = board.wrap(point);
        safe &= !points.contains(point);
        points.add(point);
        head = point;
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean isSafe() {
        return isSafe;
    }
    public void setSafe(boolean isSafe) {
        this.isSafe = isSafe;
    }

    public Point getHead() {
        return head;
    }

    private boolean isStill() {
        return xVelocity == 0 & yVelocity == 0;
    }

    public void move() {
        if (!isStill()) {
            Point newHead = head.translate(xVelocity, yVelocity);
            if (board.getObstacles().stream().anyMatch(obstacle -> obstacle.getPoints().equals(newHead))) {
                safe = false;
            } else {
                shiftTo(newHead);
            }
        }
    }

    public void extend() {
        if (!isStill()) {
            Point newHead = head.translate(xVelocity, yVelocity);
            if (board.getObstacles().stream().anyMatch(obstacle -> obstacle.getPoints().equals(newHead))) {
                safe = false;
            } else {
                growTo(newHead);
            }
        }
    }

    public void setUp() {
        if (yVelocity == 1 && length > 1) return;
        xVelocity = 0;
        yVelocity = -1;
    }

    public void setDown() {
        if (yVelocity == -1 && length > 1) return;
        xVelocity = 0;
        yVelocity = 1;
    }

    public void setLeft() {
        if (xVelocity == 1 && length > 1) return;
        xVelocity = -1;
        yVelocity = 0;
    }

    public void setRight() {
        if (xVelocity == -1 && length > 1) return;
        xVelocity = 1;
        yVelocity = 0;
    }
}
