package snake;

import java.util.ArrayList;
import java.util.Random;

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
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                openCoords.add(new Coordinate(i, j));
            }
        }

        for (int i = 0; i < snake.size(); i++) {
            Coordinate coordinate = snake.get(i);
            openCoords.remove((columnCount * coordinate.x) + (rowCount * coordinate.y) - i);
        }

        Random random = new Random();
        return openCoords.get(random.nextInt(openCoords.size()));
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

    public void print() {
        String[][] board = new String[columnCount][rowCount];

        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                board[i][j] = "\u25A1 ";
            }
        }

        board[food.x][food.y] = "# ";

        for (Coordinate coordinate : snake) {
            board[coordinate.x][coordinate.y] = "\u25A0 ";
        }

        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                System.out.print(board[i][j]);
            }
            System.out.print("\n");
        }
    }

    public String toString() {
        return snake.get(0).toString();
    }
}