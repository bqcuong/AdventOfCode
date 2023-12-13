package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day10 extends Solution {

    private final int LEFT = 0;
    private final int UP = 1;
    private final int RIGHT = 2;
    private final int DOWN = 3;

    Map<Character, List<Integer>> PIPE_INCOMING_DIRECTIONS = Map.of(
        '|', List.of(UP, DOWN),
        '-', List.of(LEFT, RIGHT),
        'L', List.of(DOWN, LEFT),
        'J', List.of(DOWN, RIGHT),
        '7', List.of(RIGHT, UP),
        'F', List.of(UP, LEFT),
        'S', List.of(LEFT, RIGHT, DOWN, UP)
    );

    private List<int[]> visitedPoints;

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        int m = inputLines.size();
        int n = inputLines.get(0).length();
        char[][] matrix = new char[m][n];

        for (int row = 0; row < m; row++) {
            String line = inputLines.get(row);
            for (int col = 0; col < n; col++) {
                matrix[row][col] = line.charAt(col);
            }
        }

        int[] startPos = locateElement(matrix, 'S');
        this.visitedPoints = new ArrayList<>();

        long steps = traversal(matrix, startPos) / 2;

        if (part == PART_NUMBER.TWO) {
            // Use Shoelace's theorem to compute the area of polygon:
            // A = 1/2(x1y2 + x2y3 + ... + xny1) - (y1x2 + y2x3 + ... + x1yn)
            // The area of the polygon can also be computed with Rick's theorem
            // A = (1/2)P + I - 1, with P = #lattice points on the edges, I = #lattice points in the interior
            // For the part 2, we have to find I

            long area = 0;
            for (int i = 0; i < visitedPoints.size(); i++) {
                int[] currentPoint = visitedPoints.get(i);
                int[] nextPoint = visitedPoints.get((i + 1) % visitedPoints.size());
                 area += (long) currentPoint[0] * nextPoint[1] - (long) nextPoint[0] * currentPoint[1];
            }
            area = Math.abs(area) / 2;

            return (area + 1 - visitedPoints.size() / 2);
        }

        return steps;
    }

    public long traversal(char[][] mat, int[] pos) {

        int currentX = pos[0];
        int currentY = pos[1];
        long stepCount = 0;

        int lastDirection = -1;
        while (true) {
            char currentTile = mat[currentX][currentY];

            if (currentTile == 'S' && stepCount > 0) {
                break;
            }

            for (int dir = 0; dir <= 3; dir++) {
                int x = 0;
                int y = 0;

                switch (dir) {
                    case LEFT -> y = -1;
                    case RIGHT -> y = 1;
                    case DOWN -> x = 1;
                    case UP -> x = -1;
                }

                int nextX = currentX + x;
                int nextY = currentY + y;

                if (nextX < 0 || nextY < 0 || nextX >= mat.length || nextY >= mat[0].length) {
                    continue;
                }

                char nextTitle = mat[nextX][nextY];
                boolean canMove = checkValidMove(currentTile, nextTitle, dir);
                if (canMove && Math.abs(dir - lastDirection) != 2) { // do not go back to where you come from
                    currentX = nextX;
                    currentY = nextY;
                    lastDirection = dir;
                    stepCount++;
                    this.visitedPoints.add(new int[] {currentX, currentY});
                    break;
                }
            }
        }

        return stepCount;
    }

    private boolean checkValidMove(char currentTile, char nextTile, int direction) {
        if (PIPE_INCOMING_DIRECTIONS.containsKey(nextTile)) {
            // find in accepted out-coming connection list of the current pipe
            for (int outComing : PIPE_INCOMING_DIRECTIONS.get(currentTile)) {
                // make sure the next pipe also accepts incoming connection from this direction
                if (Math.abs(outComing - direction) == 2 && PIPE_INCOMING_DIRECTIONS.get(nextTile).contains(direction)) {
                    return true;
                }
            }
        }

        return false;
    }

    private int[] locateElement(char[][] mat, char toFind) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == toFind) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[]{};
    }
}
