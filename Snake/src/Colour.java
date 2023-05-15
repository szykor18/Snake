import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Colour {
    public static void paint(Board board, GraphicsContext gc) {
        gc.setFill(board.COLOR);
        gc.fillRect(0, 0, board.getWidth(), board.getHeight());

        // Now the Food
        gc.setFill(Food.COLOR);
        paintPoint(board.getFood().getPoint(), gc);

        // Now the snake
        Snake snake = board.getSnake();
        gc.setFill(Snake.COLOR);
        snake.getPoints().forEach(point -> paintPoint(point, gc));
        if (!snake.isSafe()) {
            gc.setFill(Snake.DEAD);
            paintPoint(snake.getHead(), gc);
        }

        // The score
        gc.setFill(Color.BEIGE);
        gc.fillText("Score : " + 100 * snake.getPoints().size(), 10, 490);
    }

    private static void paintPoint(Point point, GraphicsContext gc) {
        gc.fillRect(point.getX() * Board.SIZE, point.getY() * Board.SIZE, Board.SIZE, Board.SIZE);
    }

    public static void paintResetMessage(GraphicsContext gc) {
        gc.setFill(Color.AQUAMARINE);
        gc.fillText("Hit RETURN to reset.", 10, 10);
    }
}