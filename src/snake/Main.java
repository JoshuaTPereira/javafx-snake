package snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Game game;
    private Direction currentDirection;
    private Direction nextDirection;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        game = new Game();
        game.print();
        currentDirection = nextDirection = Direction.RIGHT;

        Scene scene = new Scene(new StackPane(), 640, 480);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keycode = event.getCode();
                
                switch (keycode) {
                    case SPACE:
                    case ENTER: restart(); break;
                }

                handleMovement(keycode);
            }
        });

        new AnimationTimer() {
            public void handle(long currentNanoTime) { 
                if (!game.hasLost() && !game.hasWon()) {
                    game.move(currentDirection);
                    currentDirection = nextDirection;
                    game.print();
                }
            }
        }.start();
        
        stage.setScene(scene);
        stage.show();
    }

    private void restart() {
        game = new Game();
        game.print();
        currentDirection = nextDirection = Direction.RIGHT;
    }

    private void handleMovement(KeyCode keycode) {
        if (keycode == KeyCode.UP && currentDirection != Direction.DOWN) {
            nextDirection = Direction.UP;
        }
        else if (keycode == KeyCode.DOWN && currentDirection != Direction.UP) {
            nextDirection = Direction.DOWN;
        }
        else if (keycode == KeyCode.LEFT && currentDirection != Direction.RIGHT) {
            nextDirection = Direction.LEFT;
        }
        else if (keycode == KeyCode.RIGHT && currentDirection != Direction.LEFT) {
            nextDirection = Direction.RIGHT;
        }
    }

}
