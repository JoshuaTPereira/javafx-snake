package snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Game game;
    private Direction direction;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        game = new Game();
        direction = Direction.RIGHT;

        Scene scene = new Scene(new StackPane(), 640, 480);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    direction = Direction.UP;    break;
                    case DOWN:  direction = Direction.DOWN;  break;
                    case LEFT:  direction = Direction.LEFT;  break;
                    case RIGHT: direction = Direction.RIGHT; break;
                }
            }
        });

        new AnimationTimer() {
            public void handle(long currentNanoTime) { 
                game.move(direction);
                game.print();
            }
        }.start();
        
        stage.setScene(scene);
        stage.show();
    }

}
