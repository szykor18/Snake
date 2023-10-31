import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Board {
    public static final int SIZE = 10;
    public static final Color COLOR = new Color(0.1, 0.1, 0.1, 1);

    private final int cols;     // The number of columns
    private final int rows;     // The number of rows

    private Snake snake;
    private Food food;
    private Random random = new Random();
    private List<Obstacle> obstacles;

    public Board(final double width, final double height) {
        this.obstacles = new ArrayList<>();

        rows = (int) width / SIZE;
        cols = (int) height / SIZE;

        // initialize the snake at the centre of the screen
        snake = new Snake(this, new Point(rows / 2, cols / 2));

        // put the food at a random location
        food = new Food(getRandomPoint());

        for (int i = 0; i < 5; i++) {
            addRandomObstacle();
        }
    }

    public Point wrap(Point point) {
        int x = point.getX();
        int y = point.getY();
        if (x >= rows) x = 0;
        if (y >= cols) y = 0;
        if (x < 0) x = rows - 1;
        if (y < 0) y = cols - 1;
        return new Point(x, y);
    }
    private Point getRandomPoint() {
        Random random = new Random();
        Point point;
        do {
            point = new Point(random.nextInt(rows), random.nextInt(cols));
        } while (snake.getPoints().contains(point) || obstaclesContainPoint(point));
        return point;
    }

    private boolean obstaclesContainPoint(Point point) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getPoints().contains(point)) {
                return true;
            }
        }
        return false;
    }

    public void addRandomObstacle() {
        Random random = new Random();
        List<Point> obstaclePoints = new ArrayList<>();
        Point center = getRandomPoint();
        int obstacleSize = random.nextInt(5) + 3; // Random size between 3 and 7
        for (int i = 0; i < obstacleSize; i++) {
            int x = center.getX() + random.nextInt(3) - 1; // -1, 0, or 1
            int y = center.getY() + random.nextInt(3) - 1; // -1, 0, or 1
            obstaclePoints.add(new Point(x, y));
        }
        obstacles.add(new Obstacle(obstaclePoints));
    }

    public void update() {
        if (!snake.isSafe()) {
            return; 
        }

        if (food.getPoint().equals(snake.getHead())) {
            snake.extend();
            food.setPoint(getRandomPoint());
        } else {
            snake.move();
        }

        checkSnakeSafety();
    }

    private void checkSnakeSafety() {
        if (checkCollisionWithObstacles()) {
            snake.setSafe(false);
        }
    }

    private boolean checkCollisionWithObstacles() {
        Point head = snake.getHead();
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getPoints().contains(head)) {
                return true;
            }
        }
        return false;
    }

    public int getCols() {
        return this.cols;
    }

    public int getRows() {
        return this.rows;
    }

    public double getWidth() {
        return this.rows * SIZE;
    }

    public double getHeight() {
        return this.cols * SIZE;
    }

    public Snake getSnake() {
        return this.snake;
    }

    public Food getFood() {
        return this.food;
    }
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }
}
