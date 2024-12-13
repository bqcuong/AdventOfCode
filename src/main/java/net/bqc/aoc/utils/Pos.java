package net.bqc.aoc.utils;

public class Pos {
    public int x;
    public int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(x) ^ Integer.hashCode(y);
    }

    @Override
    public boolean equals(Object obj) {
        return x == ((Pos) obj).x && y == ((Pos) obj).y;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", x, y);
    }
}
