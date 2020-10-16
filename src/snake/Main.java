package snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private int columnCount, rowCount, columnSize, rowSize, width, height;
    private long speedModifier;

    private Game game;
    private Direction currentDirection;
    private Direction nextDirection;

    private GraphicsContext graphicsContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Parameters args = getParameters();
        columnCount = 21;
        rowCount = 21;
        columnSize = 20;
        rowSize = 20;
        width = 640;
        height = 480;
        speedModifier = 50_000_000;

        game = new Game();
        currentDirection = nextDirection = Direction.RIGHT;

        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        graphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keycode = event.getCode();
                
                switch (keycode) {
                    case SPACE:
                    case ENTER: 
                        restartGame(); 
                    break;
                    default: 
                        // NO OP
                    break;
                }

                handleMovement(keycode);
            }
        });

        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long currentNanoTime) { 
                if (currentNanoTime - lastUpdate >= speedModifier) {
                    runGameLoop();
                    lastUpdate = currentNanoTime;
                }
            }
        }.start();
        
        stage.setScene(scene);
        stage.show();
    }

    private void runGameLoop() {
        if (!game.hasLost() && !game.hasWon()) {
            game.move(currentDirection);
            currentDirection = nextDirection;
            drawGame();
        }
    }

    private void restartGame() {
        game = new Game();
        currentDirection = nextDirection = Direction.RIGHT;
    }

    private void handleMovement(KeyCode keycode) {
        boolean isStartSize = game.snake().size() == 1;

        if (keycode == KeyCode.UP && (currentDirection != Direction.DOWN || isStartSize)) {
            nextDirection = Direction.UP;
        }
        else if (keycode == KeyCode.DOWN && (currentDirection != Direction.UP || isStartSize)) {
            nextDirection = Direction.DOWN;
        }
        else if (keycode == KeyCode.LEFT && (currentDirection != Direction.RIGHT || isStartSize)) {
            nextDirection = Direction.LEFT;
        }
        else if (keycode == KeyCode.RIGHT && (currentDirection != Direction.LEFT || isStartSize)) {
            nextDirection = Direction.RIGHT;
        }
    }

    private void drawGame() {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(1);

        int startingColumnPosition = (width - (columnCount * columnSize)) / 2;
        int startingRowPosition = (height - (rowCount * rowSize)) / 2;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                graphicsContext.fillRect(startingColumnPosition + columnSize * j, startingRowPosition + rowSize * i, columnSize, rowSize);
                graphicsContext.strokeRect(startingColumnPosition + columnSize * j, startingRowPosition + rowSize * i, columnSize, rowSize);
            }
        }

        graphicsContext.setFill(Color.GREEN);

        for (Coordinate coordinate : game.snake()) {
            graphicsContext.fillRect(startingColumnPosition + columnSize * coordinate.x, startingRowPosition + rowSize * coordinate.y, columnSize, rowSize);
        }

        graphicsContext.setFill(Color.RED);

        graphicsContext.fillRect(startingColumnPosition + columnSize * game.food().x, startingRowPosition + rowSize * game.food().y, columnSize, rowSize);
    }

}
