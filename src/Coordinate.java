package snake;

public final class Coordinate {

    public final int x;
    public final int y;

    public Coordinate() {
        this(0, 0);        
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    public String toString() {
        return "x: " + x + " y: " + y;
    }
} 