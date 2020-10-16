package snake;

import java.util.List;

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

    // Command line arguments for game settings
    private int columnCount, rowCount, columnSize, rowSize, width, height;
    private boolean modWall;
    private long speedModifier;

    private Game game;
    private Direction currentDirection;
    private Direction nextDirection;
    private List<Coordinate> previousSnake; // Used to optimize the draw routine

    private GraphicsContext graphicsContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        parseArgs(getParameters());

        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        graphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);

        startGame();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keycode = event.getCode();
                
                switch (keycode) {
                    case SPACE:
                    case ENTER: 
                        startGame(); // Restart
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

    private void parseArgs(Parameters args) {
        columnCount = 130;
        rowCount = 90;
        columnSize = 10;
        rowSize = 10;
        width = 1920;
        height = 1080;
        modWall = true;
        speedModifier = 0;
    }

    private void runGameLoop() {
        if (!game.hasLost() && !game.hasWon()) {
            previousSnake = game.snake();
            game.move(currentDirection);
            currentDirection = nextDirection;
            drawGame();
        }
    }

    private void startGame() {
        game = new Game(columnCount, rowCount, modWall);
        currentDirection = nextDirection = Direction.RIGHT;
        drawInitialGameBoard();
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

    private void drawInitialGameBoard() {
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
    }

    private void drawGame() {
        int startingColumnPosition = (width - (columnCount * columnSize)) / 2;
        int startingRowPosition = (height - (rowCount * rowSize)) / 2;

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setStroke(Color.BLACK);

        for (Coordinate coordinate : previousSnake) {
            graphicsContext.fillRect(startingColumnPosition + columnSize * coordinate.x, startingRowPosition + rowSize * coordinate.y, columnSize, rowSize);
            graphicsContext.strokeRect(startingColumnPosition + columnSize * coordinate.x, startingRowPosition + rowSize * coordinate.y, columnSize, rowSize);
        }

        graphicsContext.setFill(Color.GREEN);

        for (Coordinate coordinate : game.snake()) {
            graphicsContext.fillRect(startingColumnPosition + columnSize * coordinate.x, startingRowPosition + rowSize * coordinate.y, columnSize, rowSize);
        }

        graphicsContext.setFill(Color.RED);

        graphicsContext.fillRect(startingColumnPosition + columnSize * game.food().x, startingRowPosition + rowSize * game.food().y, columnSize, rowSize);
    }

}
