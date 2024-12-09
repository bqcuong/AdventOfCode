package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.SolutionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends Solution {

    private final int LEFT = 0;
    private final int UP = 1;
    private final int RIGHT = 2;
    private final int DOWN = 3;

    private final int ROUND = 1;
    private final int CUBE = 5;
    private final int DOT = 0;

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        super.solve(part, inputLines);

        int[][] space = parseSpace(inputLines);

        if (part == PART_NUMBER.ONE) {
            roll(space, UP);
        }
        else {
            Map<String, Integer> map = new HashMap<>();

            int firstCycle = 0;
            int lastCycle = 0;

            long[] loads = new long[1001];
            for (int i = 1; i <= 1000; i++) {
                rollOneCycle(space);
                String hash = SolutionUtils.generateSHA256(space);
                loads[i] = calculateTotalLoad(space);

                if (map.containsKey(hash)) {
                    firstCycle = map.get(hash);
                    lastCycle = i - 1;
                    break;
                }
                else {
                    map.put(hash, i);
                }
            }

            int targetCycle = 1000000000;
            int matchedCycle = (targetCycle - lastCycle - 1) % (lastCycle - firstCycle + 1) + firstCycle;
            return loads[matchedCycle];
        }

        return calculateTotalLoad(space);
    }

    private void rollOneCycle(int[][] space) {
        roll(space, UP);
        roll(space, LEFT);
        roll(space, DOWN);
        roll(space, RIGHT);
    }

    private long calculateTotalLoad(int[][] space) {
        long totalLoad = 0;
        int len = space.length;
        for (int i = 0; i < space.length; i++) {
            for (int j = 0; j < space[0].length; j++) {
                if (space[i][j] == ROUND) {
                    totalLoad += len - i;
                }
            }
        }
        return totalLoad;
    }

    private void roll(int[][] space, int direction) {
        int len = space.length; // matrix: NxN

        if (direction == UP) {
            for (int col = 0; col < len; col++) { // column: left -> right

                int row = 0; // row: top -> bottom
                int countRoundRocks = 0;
                while (row < len) {
                    while (row < len && space[row][col] == CUBE) {
                        row++;
                    }

                    int start = row;

                    while (row < len && space[row][col] != CUBE) {
                        if (space[row][col] != DOT) {
                            countRoundRocks++;
                            space[row][col] = DOT;
                        }
                        row++;
                    }

//                    System.out.printf("[%d, %d]: %d\n", row, col, countRoundRocks);
                    for (int filledRow = start; filledRow < start + countRoundRocks; filledRow++) {
                        space[filledRow][col] = ROUND;
                    }
                    countRoundRocks = 0; // reset count
                }
            }
        }
        else if (direction == DOWN) {
            for (int col = 0; col < len; col++) { // column: left -> right

                int row = len - 1; // row: bottom -> top
                int countRoundRocks = 0;
                while (row >= 0) {
                    while (row >= 0 && space[row][col] == CUBE) {
                        row--;
                    }

                    int start = row;

                    while (row >= 0 && space[row][col] != CUBE) {
                        if (space[row][col] != DOT) {
                            countRoundRocks++;
                            space[row][col] = DOT;
                        }
                        row--;
                    }

                    for (int filledRow = start; filledRow > start - countRoundRocks; filledRow--) {
                        space[filledRow][col] = ROUND;
                    }
                    countRoundRocks = 0; // reset count
                }
            }
        }
        else if (direction == LEFT) {
            for (int row = 0; row < len; row++) { // row: top -> bottom

                int col = 0; // row: left -> right
                int countRoundRocks = 0;
                while (col < len) {
                    while (col < len && space[row][col] == CUBE) {
                        col++;
                    }

                    int start = col;

                    while (col < len && space[row][col] != CUBE) {
                        if (space[row][col] != DOT) {
                            countRoundRocks++;
                            space[row][col] = DOT;
                        }
                        col++;
                    }

                    for (int filledCol = start; filledCol < start + countRoundRocks; filledCol++) {
                        space[row][filledCol] = ROUND;
                    }
                    countRoundRocks = 0; // reset count
                }
            }
        }
        else if (direction == RIGHT) {
            for (int row = 0; row < len; row++) { // row: top -> bottom

                int col = len - 1; // row: right -> left
                int countRoundRocks = 0;
                while (col >= 0) {
                    while (col >= 0 && space[row][col] == CUBE) {
                        col--;
                    }

                    int start = col;

                    while (col >= 0 && space[row][col] != CUBE) {
                        if (space[row][col] != DOT) {
                            countRoundRocks++;
                            space[row][col] = DOT;
                        }
                        col--;
                    }

                    for (int filledCol = start; filledCol > start - countRoundRocks; filledCol--) {
                        space[row][filledCol] = ROUND;
                    }
                    countRoundRocks = 0; // reset count
                }
            }
        }
    }

    private int[][] parseSpace(List<String> inputLines) {
        int[][] space = new int[inputLines.size()][inputLines.get(0).length()];

        for (int i = 0; i < space.length; i++) {
            String row = inputLines.get(i);
            for (int j = 0; j < space[0].length; j++) {
                space[i][j] = row.charAt(j) == 'O' ? ROUND : row.charAt(j) == '.' ? DOT : CUBE;
            }
        }
        return space;
    }
}
