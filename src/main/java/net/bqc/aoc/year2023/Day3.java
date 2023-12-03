package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day3 extends Solution {

    @Override
    public int solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;

        int m = inputLines.size();
        int n = inputLines.get(0).length();

        char[][] matrix = new char[m][n];

        for (int row = 0; row < m; row++) {
            String line = inputLines.get(row);
            matrix[row] = new char[n];
            for (int col = 0; col < n; col++) {
                matrix[row][col] = line.charAt(col);
            }
        }

        int sum = 0;
        for (int row = 0; row < m; row++) {
            String line = inputLines.get(row);
            for (int col = 0; col < n; col++) {
                char ch = line.charAt(col);

                if (ch != '.' && !Character.isDigit(ch)) {
                    List<Integer> partNumbers = findNearbyNumbers(matrix, m, n, row, col);
                    if (this.pup == PART_NUMBER.ONE) {
                        sum += partNumbers.stream().mapToInt(Integer::intValue).sum();
                    }
                    else if (this.pup == PART_NUMBER.TWO && ch == '*' && partNumbers.size() == 2) {
                        sum += partNumbers.get(0) * partNumbers.get(1);
                    }
                }

            }
        }

        return sum;
    }

    private List<Integer> findNearbyNumbers(char[][] matrix, int m, int n, int row, int col) {
        List<Integer> nearbyNumbers = new ArrayList<>();

        // Find nearby numbers in 8 directions
        int up = findNumberAt(matrix, m, n, row - 1, col); // up
        nearbyNumbers.add(up);
        if (up == -1) {
            int upLeft = findNumberAt(matrix, m, n, row - 1, col - 1); // up-left
            int upRight = findNumberAt(matrix, m, n, row - 1, col + 1); // up-right
            nearbyNumbers.add(upLeft);
            nearbyNumbers.add(upRight);
        }

        int down = findNumberAt(matrix, m, n, row + 1, col); // down
        nearbyNumbers.add(down);
        if (down == -1) {
            int downLeft = findNumberAt(matrix, m, n, row + 1, col - 1); // down-left
            int downRight = findNumberAt(matrix, m, n, row + 1, col + 1); // down-right
            nearbyNumbers.add(downLeft);
            nearbyNumbers.add(downRight);
        }

        int left = findNumberAt(matrix, m, n, row, col - 1); // left
        int right = findNumberAt(matrix, m, n, row, col + 1); // right
        nearbyNumbers.add(left);
        nearbyNumbers.add(right);

        return nearbyNumbers.stream().filter(x -> x > 0).toList();
    }

    private int findNumberAt(char[][] matrix, int m, int n, int row, int col) {
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

        return Integer.parseInt(num.toString());
    }
}