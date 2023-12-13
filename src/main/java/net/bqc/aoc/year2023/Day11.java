package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day11 extends Solution {

    private final List<Integer> emptyRows = new ArrayList<>();
    private final List<Integer> emptyCols = new ArrayList<>();

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        int[][] space = parseSpace(inputLines);
        List<int[]> galaxies = findGalaxies(space);
        return computeTotalDistance(galaxies, part == PART_NUMBER.ONE ? 2 : 1000000);
    }

    private long computeTotalDistance(List<int[]> galaxies, int expansionRatio) {
        long totalDistance = 0;

        for (int i = 0; i < galaxies.size() - 1; i++) {
            int[] srcGalaxy = findLocationAfterExpansion(galaxies.get(i), expansionRatio);
            for (int j = i + 1; j < galaxies.size(); j++) {
                int[] dstGalaxy = findLocationAfterExpansion(galaxies.get(j), expansionRatio);
                long distance = Math.abs(srcGalaxy[0] - dstGalaxy[0]) + Math.abs(srcGalaxy[1] - dstGalaxy[1]);
                totalDistance += distance;
            }
        }
        return totalDistance;
    }

    private int[] findLocationAfterExpansion(int[] pos, int expansionRatio) {
        int x = pos[0];
        int y = pos[1];
        expansionRatio = expansionRatio - 1;

        int expandedX = 0;
        int expandedY = 0;
        for (Integer row : this.emptyRows) {
            if (x > row) expandedX += expansionRatio;
        }

        for (Integer col : this.emptyCols) {
            if (y > col) expandedY += expansionRatio;
        }
        return new int[] {x + expandedX, y + expandedY};
    }

    private List<int[]> findGalaxies(int[][] space) {
        List<int[]> galaxies = new ArrayList<>();
        for (int i = 0; i < space.length; i++) {
            for (int j = 0; j < space[i].length; j++) {
                if (space[i][j] != 0) {
                    galaxies.add(new int[] {i, j});
                }
            }
        }
        return galaxies;
    }

    private int[][] parseSpace(List<String> inputLines) {
        int[][] space = new int[inputLines.size()][inputLines.get(0).length()];

        for (int i = 0; i < space.length; i++) {
            String row = inputLines.get(i);
            if (!row.contains("#")) {
                this.emptyRows.add(i);
            }

            for (int j = 0; j < space[0].length; j++) {
                space[i][j] = row.charAt(j) == '.' ? 0 : 1;
            }
        }

        for (int j = 0; j < space[0].length; j++) {
            boolean isEmptyCol = true;
            for (int i = 0; i < space.length; i++) {
                if (space[i][j] != 0) {
                    isEmptyCol = false;
                    break;
                }
            }

            if (isEmptyCol) {
                this.emptyCols.add(j);
            }
        }

        return space;
    }
}
