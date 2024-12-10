package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day16 extends Solution {

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "{" + x + ", " + y + "}";
        }
    }

    private final int LEFT = 1;
    private final int UP = 2;
    private final int RIGHT = 3;
    private final int DOWN = 4;

    private int[][] visitedPoints;
    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        super.solve(part, inputLines);

        char[][] map = parseMap(inputLines);

        if (!isPart2()) {
            visitedPoints = new int[map.length][map[0].length];
            move(map, 0, 0, RIGHT);
        }
        else {
            long maxCount = 0;
            for (int row = 0; row < map.length; row++) {
                visitedPoints = new int[map.length][map[0].length];
                move(map, row, 0, RIGHT);
                long currentCount = countEnergizedBlocks(visitedPoints);
                maxCount = Math.max(maxCount, currentCount);

                visitedPoints = new int[map.length][map[0].length];
                move(map, row, map[0].length - 1, LEFT);
                currentCount = countEnergizedBlocks(visitedPoints);
                maxCount = Math.max(maxCount, currentCount);
            }

            for (int col = 0; col < map[0].length; col++) {
                visitedPoints = new int[map.length][map[0].length];
                move(map, 0, col, DOWN);
                long currentCount = countEnergizedBlocks(visitedPoints);
                maxCount = Math.max(maxCount, currentCount);

                visitedPoints = new int[map.length][map[0].length];
                move(map, map.length - 1, col, UP);
                currentCount = countEnergizedBlocks(visitedPoints);
                maxCount = Math.max(maxCount, currentCount);
            }

            return maxCount;
        }

        return countEnergizedBlocks(this.visitedPoints);
    }

    private long countEnergizedBlocks(int[][] visitedPoints) {
        return Arrays.stream(visitedPoints)
            .flatMapToInt(Arrays::stream)
            .filter(value -> value != 0)
            .count();
    }

    private void move(char[][] map, int row, int col, int direction) {

        if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) { // out of the map
            return;
        }

        if (this.visitedPoints[row][col] == direction) { // in a loop
            return;
        }

        this.visitedPoints[row][col] = direction;

        // calculate the next point
        char currentBlock = map[row][col];
        if (currentBlock == '.') {
            switch (direction) {
                case RIGHT -> col += 1;
                case LEFT -> col -= 1;
                case UP -> row -= 1;
                case DOWN -> row += 1;
            }
            move(map, row, col, direction);
        }
        else if (currentBlock == '/') {
            switch (direction) {
                case RIGHT -> move(map, row - 1, col, UP);
                case LEFT -> move(map, row + 1, col, DOWN);
                case UP -> move(map, row, col + 1, RIGHT);
                case DOWN -> move(map, row, col - 1, LEFT);
            }
        }
        else if (currentBlock == '\\') {
            switch (direction) {
                case RIGHT -> move(map, row + 1, col, DOWN);
                case LEFT -> move(map, row - 1, col, UP);
                case UP -> move(map, row, col - 1, LEFT);
                case DOWN -> move(map, row, col + 1, RIGHT);
            }
            move(map, row, col, direction);
        }
        else if (currentBlock == '-') {
            if (direction == LEFT || direction == RIGHT) {
                col += direction - 2;
                move(map, row, col, direction);
            }
            else if (direction == UP || direction == DOWN) {
                move(map, row, col - 1, LEFT);
                move(map, row, col + 1, RIGHT);
            }
        }
        else if (currentBlock == '|') {
            if (direction == UP || direction == DOWN) {
                row += direction - 3;
                move(map, row, col, direction);
            }
            else if (direction == LEFT || direction == RIGHT) {
                move(map, row - 1, col, UP);
                move(map, row + 1, col, DOWN);
            }
        }
    }


    private char[][] parseMap(List<String> inputLines) {
        char[][] map = new char[inputLines.size()][inputLines.get(0).length()];
        for (int i = 0; i < map.length; i++) {
            String line = inputLines.get(i);
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = line.charAt(j);
            }
        }
        return map;
    }
}
