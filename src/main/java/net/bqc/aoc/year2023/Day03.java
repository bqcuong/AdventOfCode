package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.SolutionUtils;

import java.util.ArrayList;
import java.util.List;

public class Day03 extends Solution {

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        super.solve(part, inputLines);

        int m = inputLines.size();
        int n = inputLines.get(0).length();

        char[][] matrix = SolutionUtils.readAsMatrix(inputLines);

        long sum = 0;
        for (int row = 0; row < m; row++) {
            String line = inputLines.get(row);
            for (int col = 0; col < n; col++) {
                char ch = line.charAt(col);

                if (ch != '.' && !Character.isDigit(ch)) {
                    List<Long> partNumbers = findNearbyNumbers(matrix, m, n, row, col);
                    if (!isPart2()) {
                        sum += partNumbers.stream().mapToLong(Long::intValue).sum();
                    }
                    else if (isPart2() && ch == '*' && partNumbers.size() == 2) {
                        sum += partNumbers.get(0) * partNumbers.get(1);
                    }
                }

            }
        }

        return sum;
    }

    private List<Long> findNearbyNumbers(char[][] matrix, int m, int n, int row, int col) {
        List<Long> nearbyNumbers = new ArrayList<>();

        // Find nearby numbers in 8 directions
        long up = findNumberAt(matrix, m, n, row - 1, col); // up
        nearbyNumbers.add(up);
        if (up == -1) {
            long upLeft = findNumberAt(matrix, m, n, row - 1, col - 1); // up-left
            long upRight = findNumberAt(matrix, m, n, row - 1, col + 1); // up-right
            nearbyNumbers.add(upLeft);
            nearbyNumbers.add(upRight);
        }

        long down = findNumberAt(matrix, m, n, row + 1, col); // down
        nearbyNumbers.add(down);
        if (down == -1) {
            long downLeft = findNumberAt(matrix, m, n, row + 1, col - 1); // down-left
            long downRight = findNumberAt(matrix, m, n, row + 1, col + 1); // down-right
            nearbyNumbers.add(downLeft);
            nearbyNumbers.add(downRight);
        }

        long left = findNumberAt(matrix, m, n, row, col - 1); // left
        long right = findNumberAt(matrix, m, n, row, col + 1); // right
        nearbyNumbers.add(left);
        nearbyNumbers.add(right);

        return nearbyNumbers.stream().filter(x -> x > 0).toList();
    }

    private long findNumberAt(char[][] matrix, int m, int n, int row, int col) {
        if (row < 0 || row >= m || col < 0 || col >= n) {
            return -1;
        }

        if (!Character.isDigit(matrix[row][col])) {
            return -1;
        }

        StringBuilder num = new StringBuilder();
        num.append(matrix[row][col]);

        int p = col;
        while (p++ < n - 1 && Character.isDigit(matrix[row][p])) {
            num.append(matrix[row][p]);
        }

        p = col;
        while (p-- > 0 && Character.isDigit(matrix[row][p])) {
            num.insert(0, matrix[row][p]);
        }

        return Long.parseLong(num.toString());
    }
}