package net.bqc.aoc.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Array2DUtils {

    public static int[][] readAsIntMatrix(List<String> inputLines) {
        int m = inputLines.size();
        int n = inputLines.get(0).length();

        int[][] matrix = new int[m][n];

        for (int row = 0; row < m; row++) {
            String line = inputLines.get(row);
            matrix[row] = new int[n];
            for (int col = 0; col < n; col++) {
                matrix[row][col] = line.charAt(col) - '0';
            }
        }
        return matrix;
    }

    public static char[][] readAsMatrix(List<String> inputLines) {
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
        return matrix;
    }

    public static Set<Character> getUniqueElements(char[][] matrix) {
        Set<Character> uniqueElements = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                uniqueElements.add(matrix[i][j]);
            }
        }
        return uniqueElements;
    }

    public static long sum(int[][] matrix) {
        long sum = 0;
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                sum += anInt;
            }
        }
        return sum;
    }

    public static int[][] generateMatrix(int m, int n) {
        int[][] matrix = new int[m][n];
        for (int row = 0; row < m; row++) {
            matrix[row] = new int[n];
        }
        return matrix;
    }

    public static List<Pos> getXPos(int[][] matrix, int x) {
        List<Pos> posList = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            int[] row = matrix[i];
            for (int j = 0; j < row.length; j++) {
                if (row[j] == x) {
                    posList.add(new Pos(i, j));
                }
            }
        }
        return posList;
    }

    public static List<Pos> getXPos(char[][] matrix, char x) {
        List<Pos> posList = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            char[] row = matrix[i];
            for (int j = 0; j < row.length; j++) {
                if (row[j] == x) {
                    posList.add(new Pos(i, j));
                }
            }
        }
        return posList;
    }

    public static boolean isConsecutiveArray(long[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] + 1 != arr[i+1]) {
                return false;
            }
        }
        return true;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void printMatrix(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%c ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static char[][] copyMatrix(char[][] matrix) {
        char [][] copiedMatrix = new char[matrix.length][];
        for (int i = 0; i < matrix.length; i++)
            copiedMatrix[i] = matrix[i].clone();
        return copiedMatrix;
    }

    public static int countInMatrix(char[][] matrix, char toCount) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == toCount) count++;
            }
        }
        return count;
    }

    public static void swapElements(int[][] matrix, int row1, int col1, int row2, int col2) {
        int temp = matrix[row1][col1];
        matrix[row1][col1] = matrix[row2][col2];
        matrix[row2][col2] = temp;
    }
}
