package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;

import java.util.List;

public class Day08 extends Solution {

    private char[][] antennas;

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        super.solve(part, inputLines);

        this.antennas = Array2DUtils.readAsMatrix(inputLines);
        int[][] antinodes = generateAntinodes();

        if (isPart2()) {
            // add antenna themselves as antinodes
            for (int i = 0; i < antennas.length; i++) {
                for (int j = 0; j < antennas[0].length; j++) {
                    if (antennas[i][j] != '.') antinodes[i][j] = 1;
                }
            }
        }
        return Array2DUtils.sum(antinodes);
    }

    private int[][] generateAntinodes() {
        int m = antennas.length;
        int n = antennas[0].length;
        int[][] antinotes = Array2DUtils.generateMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (antennas[i][j] == '.') continue;

                for (int k = i; k < m; k++) {
                    for (int l = k == i ? j + 1 : 0; l < n; l++) {
                        if (antennas[k][l] == antennas[i][j]) {
                            addAntinode(m, n, antinotes, i, j, k, l);
                            addAntinode(m, n, antinotes, k, l, i, j);
                        }
                    }
                }
            }
        }
        return antinotes;
    }

    private void addAntinode(int m, int n, int[][] antinotes, int i, int j, int k, int l) {
        int dc = 2; // distance coefficient
        while (i * dc - k >= 0 && i * dc - k < m && j * dc - l >= 0 && j * dc - l < n) {
            antinotes[i * dc - k][j * dc - l] = 1;
            if (!isPart2()) break;

            int nextI = i * dc - k;
            int nextJ = j * dc - l;
            k = i;
            l = j;
            i = nextI;
            j = nextJ;
        }
    }
}
