package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;
import net.bqc.aoc.utils.Pair;
import net.bqc.aoc.utils.Pos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day06 extends Solution {

    enum Direction {
        NORTH("^", -1, 0), EAST(">", 0, 1),
        SOUTH("v", 1, 0), WEST("<", 0, -1);

        public final String symbol;
        public final int dx;
        public final int dy;

        Direction(String symbol, int dx, int dy) {
            this.symbol = symbol;
            this.dx = dx;
            this.dy = dy;
        }

        public static Direction findBySymbol(String symbol) {
            return Arrays.stream(values()).filter(x -> x.symbol.equals(symbol)).findFirst().orElse(null);
        }

        public Direction turnRight() {
            return switch (this) {
                case NORTH -> EAST;
                case EAST -> SOUTH;
                case SOUTH -> WEST;
                case WEST -> NORTH;
            };
        }

        public boolean isOpposite(Direction o) {
            return this == NORTH && o == SOUTH
                || this == SOUTH && o == NORTH
                || this == WEST && o == EAST
                || this == EAST && o == WEST;
        }
    }

    private char[][] matrix;
    private Set<String> visited = new HashSet<>();
    private int loopCount = 0;

    @Override
    public long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        this.matrix = Array2DUtils.readAsMatrix(inputLines);
        Pair<Pos, Direction> start = findStartingPos(matrix);

        patrol(start.first, start.second);

        return !isPart2() ? Array2DUtils.countInMatrix(matrix, 'X') : loopCount;
    }

    private void patrol(Pos pos, Direction direction) {
        while (true) {
            matrix[pos.x][pos.y] = 'X';
            visited.add(pos.x + "," + pos.y + "," + direction.symbol);

            int newX = pos.x + direction.dx;
            int newY = pos.y + direction.dy;

            if (newX < 0 || newX >= matrix.length || newY < 0 || newY >= matrix[0].length) {
                return;
            }
            else if (matrix[newX][newY] == '#') {
                direction = direction.turnRight();
            }
            else {
                if (matrix[newX][newY] == '.') {
                    char[][] tmpMatrix = Array2DUtils.copyMatrix(matrix);
                    tmpMatrix[newX][newY] = 'O';
                    if (checkLoop(tmpMatrix, new HashSet<>(visited), pos, direction)) {
//                        System.out.printf("%d,%d %s\n", pos.x, pos.y, direction.symbol);
//                        SolutionUtils.printMatrix(tmpMatrix);
                        loopCount++;
                    }
                }
                pos = new Pos(newX, newY);
            }
        }
    }

    private boolean checkLoop(char[][] tmpMatrix, Set<String> visited, Pos pos, Direction direction) {
        while (true) {
            tmpMatrix[pos.x][pos.y] = 'X';
            visited.add(pos.x + "," + pos.y + "," + direction.symbol);

            int newX = pos.x + direction.dx;
            int newY = pos.y + direction.dy;

            if (newX < 0 || newX >= tmpMatrix.length || newY < 0 || newY >= tmpMatrix[0].length) {
                break;
            }
            if (tmpMatrix[newX][newY] == '#' || tmpMatrix[newX][newY] == 'O') {
                direction = direction.turnRight();
            }
            else {
                if (visited.contains(newX + "," + newY + "," + direction.symbol)) {
                    return true;
                }
                pos = new Pos(newX, newY);
            }
        }
        return false;
    }

    private Pair<Pos, Direction> findStartingPos(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if ("^v<>".contains("" + matrix[i][j])) {
                    return new Pair<>(new Pos(i, j), Direction.findBySymbol("" + matrix[i][j]));
                }
            }
        }
        return new Pair<>(new Pos(-1, -1), Direction.SOUTH);
    }
}