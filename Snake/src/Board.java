import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int SIZE = 10;
    public static final Color COLOR = new Color(0.1, 0.1, 0.1, 1);

    private final int cols;     // The number of columns
    private final int rows;     // The number of rows

    private Snake snake;
    private Food food;
    private List<Obstacle> obstacles;

    public Board(final double width, final double height) {
        rows = (int) width / SIZE;
        cols = (int) height / SIZE;

        // initialize the snake at the centre of the screen
        snake = new Snake(this, new Point(rows / 2, cols / 2));

        // put the food at a random location
        food = new Food(getRandomPoint());

        obstacles = new ArrayList<>();
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
        } while (point.equals(snake.getHead()));
        return point;
    }
    private Point getRandomPoint() {
        Random random = new Random();
        Point point;
        do {
            point = new Point(random.nextInt(rows), random.nextInt(cols));
        } while (snake.getPoints().contains(point) || obstacles.stream().anyMatch(obstacle -> obstacle.getPoint().equals(point)));
        return point;
    }

    public void addRandomObstacle() {
        obstacles.add(new Obstacle(getRandomPoint()));
    }

    public void update() {
        if (food.getPoint().equals(snake.getHead())) {
            snake.extend();
            food.setPoint(getRandomPoint());
        } else {
            snake.move();
        }
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
