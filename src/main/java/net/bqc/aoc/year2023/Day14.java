package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.SolutionUtils;

import java.util.List;

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
        this.pup = part;

        int[][] space = parseSpace(inputLines);
        SolutionUtils.printMatrix(space);

        roll(space, UP);
        SolutionUtils.printMatrix(space);

        long totalLoad = calculateTotalLoad(space);

        return totalLoad;
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
                        space[filledRow][col] = 1;
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
