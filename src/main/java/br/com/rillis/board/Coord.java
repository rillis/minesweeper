package br.com.rillis.board;

public class Coord {
    public int x;
    public int y;
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "X: " + x + ", Y: " + y;
    }
}
