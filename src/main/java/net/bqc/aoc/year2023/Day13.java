package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day13 extends Solution {

    private final int ROCK = 5;
    private final int ASH = 0;

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        super.solve(part, inputLines);
        List<int[][]> mirrors = parseMirrors(inputLines);
        return summarize(mirrors, part == PART_NUMBER.ONE ? 0 : 1);
    }

    private long summarize(List<int[][]> mirrors, int acceptedWrongSymbol) {
        long sum = 0;
        for (int[][] mirror : mirrors) {
            int vLine = findVerticalReflectionLine(mirror, acceptedWrongSymbol);
            if (vLine != -1) {
                sum += vLine + 1;
            }

            int hLine = findHorizontalReflectionLine(mirror, acceptedWrongSymbol);
            if (hLine != -1) {
                sum += (hLine + 1) * 100L;
            }
        }
        return sum;
    }

    private int findVerticalReflectionLine(int[][] mirror, int acceptedWrongSymbol) {
        for (int col = 0; col < mirror[0].length - 1; col++) {
            int numColsToCheck = Math.min(col + 1, mirror[0].length - col - 1);

            int wrongSymbolCount = 0;
            for (int checkedCol = 0; checkedCol < numColsToCheck; checkedCol++) {
                for (int row = 0; row < mirror.length; row++) {
                    int lhs = mirror[row][col - checkedCol];
                    int rhs = mirror[row][col + 1 + checkedCol];

                    if (lhs != rhs) { // fail on this line
                        wrongSymbolCount += 1;

                        if (wrongSymbolCount > acceptedWrongSymbol) {
                            break;
                        }
                    }
                }
                if (wrongSymbolCount > acceptedWrongSymbol) break;
            }

            if (wrongSymbolCount == acceptedWrongSymbol) return col;
        }
        return -1;
    }

    private int findHorizontalReflectionLine(int[][] mirror, int acceptedWrongSymbol) {
        for (int row = 0; row < mirror.length - 1; row++) {
            int numRowsToCheck = Math.min(row + 1, mirror.length - row - 1);

            int wrongSymbolCount = 0;
            for (int checkedRow = 0; checkedRow < numRowsToCheck; checkedRow++) {
                for (int col = 0; col < mirror[0].length; col++) {
                    int lhs = mirror[row - checkedRow][col];
                    int rhs = mirror[row + 1 + checkedRow][col];

                    if (lhs != rhs) { // fail on this line
                        wrongSymbolCount += 1;

                        if (wrongSymbolCount > acceptedWrongSymbol) {
                            break;
                        }
                    }
                }
                if (wrongSymbolCount > acceptedWrongSymbol) break;
            }

            if (wrongSymbolCount == acceptedWrongSymbol) return row;
        }
        return -1;
    }

    private List<int[][]> parseMirrors(List<String> inputLines) {
        List<int[][]> mirrors = new ArrayList<>();
        int i = 0;

        List<int[]> mirror = new ArrayList<>();;
        while (i <= inputLines.size()) {
            if (i == inputLines.size() || inputLines.get(i).isBlank()){
                int[][] mirrorMat = new int[mirror.size()][mirror.get(0).length];
                for (int k = 0; k < mirrorMat.length; k++) {
                    for (int l = 0; l < mirrorMat[0].length; l++) {
                        mirrorMat[k][l] = mirror.get(k)[l];
                    }
                }
                mirrors.add(mirrorMat);

                mirror = new ArrayList<>();
            }
            else {
                String line = inputLines.get(i);
                int[] row = Arrays.stream(line.split(""))
                    .mapToInt(x -> {
                        if (Objects.equals(x, ".")) return ASH;
                        else return ROCK;
                    }).toArray();
                mirror.add(row);
            }

            i++;
        }

        return mirrors;
    }
}
