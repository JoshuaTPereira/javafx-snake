package snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        List<Coordinate> openCoords = new ArrayList<Coordinate>(columnCount * rowCount);
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                openCoords.add(new Coordinate(i, j));
            }
        }

        if (openCoords.size() == 0) {
            hasWon = true;
            return new Coordinate(-1, -1);
        }

        for (int i = 0; i < snake.size(); i++) {
            Coordinate coordinate = snake.get(i);
            openCoords.remove((columnCount * coordinate.x) + coordinate.y - i);
        }

        Random random = new Random();
        return openCoords.get(random.nextInt(openCoords.size()));
    }

    private int mod(int a, int b) {
        return (a % b + b) % b;
    }

    public void move(Direction direction) {
        Coordinate head = snake.get(0);
        Coordinate nextHead = null;

        switch(direction) {
            case LEFT:
                nextHead = new Coordinate(modWall ? mod(head.x - 1, columnCount) : head.x - 1, head.y);
            break;
            case RIGHT:
                nextHead = new Coordinate(modWall ? mod(head.x + 1, columnCount) : head.x + 1, head.y);
            break;
            case UP:
                nextHead = new Coordinate(head.x, modWall ? mod(head.y - 1, rowCount) : head.y - 1);
            break;
            case DOWN:
                nextHead = new Coordinate(head.x, modWall ? mod(head.y + 1, rowCount) : head.y + 1);
            break;
        }

        if (
            nextHead.x < 0 || 
            nextHead.x >= columnCount || 
            nextHead.y < 0 || 
            nextHead.y >= rowCount || 
            (snake.contains(nextHead) && !snake.get(snake.size() - 1).equals(nextHead))
            ) 
        {
            hasLost = true;
        }
        else if (nextHead.equals(food)) {
            snake.add(0, nextHead);
            food = generateFood();
        }
        else {
            snake.add(0, nextHead);
            snake.remove(snake.size() - 1);
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

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                board[j][i] = "\u25A1 ";
            }
        }

        board[food.x][food.y] = "# ";

        for (Coordinate coordinate : snake) {
            board[coordinate.x][coordinate.y] = "\u25A0 ";
        }

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                System.out.print(board[j][i]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    @Override
    public String toString() {
        return "Game [columnCount=" + columnCount + ", food=" + food.toString() + ", hasLost=" + hasLost + ", hasWon=" + hasWon
                + ", modWall=" + modWall + ", rowCount=" + rowCount + ", snake=" + Arrays.deepToString(snake.toArray()) + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + columnCount;
        result = prime * result + ((food == null) ? 0 : food.hashCode());
        result = prime * result + (hasLost ? 1231 : 1237);
        result = prime * result + (hasWon ? 1231 : 1237);
        result = prime * result + (modWall ? 1231 : 1237);
        result = prime * result + rowCount;
        result = prime * result + ((snake == null) ? 0 : snake.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        if (columnCount != other.columnCount)
            return false;
        if (food == null) {
            if (other.food != null)
                return false;
        } else if (!food.equals(other.food))
            return false;
        if (hasLost != other.hasLost)
            return false;
        if (hasWon != other.hasWon)
            return false;
        if (modWall != other.modWall)
            return false;
        if (rowCount != other.rowCount)
            return false;
        if (snake == null) {
            if (other.snake != null)
                return false;
        } else if (!snake.equals(other.snake))
            return false;
        return true;
    }
}