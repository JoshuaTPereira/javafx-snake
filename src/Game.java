package snake;

import java.util.ArrayList;

public class Game {

    private boolean hasWon;
    private boolean hasLost;

    private final int columnCount;
    private final int rowCount;
    private final boolean modWall;
    private ArrayList<Coordinate> snake;
    private Coordinate food; 

    public Game() {
        this(21, 21);
    }

    public Game(int columnCount, int rowCount) {
        this(columnCount, rowCount, true);
    }

    public Game(int columnCount, int rowCount, boolean modWall) {
        if (columnCount < 1 || rowCount < 1) {
            throw new IllegalArgumentException("columnCount and rowCount must be greater than or equal to 1");
        }

        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.modWall = modWall;

        this.snake = new ArrayList<Coordinate>();
        this.snake.add(new Coordinate(columnCount / 2, rowCount / 2));
        this.food = generateFood();
    }

    private Coordinate generateFood() {
        ArrayList<Coordinate> openCoords = new ArrayList<Coordinate>();
        for (Coordinate coordinate : snake) {
            
        }
        return new Coordinate();
    }

    public void move(Direction direction) {
        switch(direction) {
            case LEFT:
                System.out.println("I moved left!");
            break;
            case RIGHT:
            break;
            case UP:
            break;
            case DOWN:
            break;
        }
    }

    public boolean hasWon() {
        return hasWon;
    }

    public boolean hasLost() {
        return hasLost;
    }

    public String toString() {
        return snake.get(0).toString();
    }
}