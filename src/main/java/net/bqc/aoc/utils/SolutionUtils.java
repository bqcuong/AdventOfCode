package net.bqc.aoc.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class SolutionUtils {

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

    public static BigDecimal[] solveQuadraticEquation(BigDecimal a, BigDecimal b, BigDecimal c) {
        BigDecimal discriminant = b.pow(2).subtract(new BigDecimal("4").multiply(a).multiply(c));

        if (discriminant.compareTo(BigDecimal.ZERO) < 0) {
            return new BigDecimal[]{};
        }

        BigDecimal sqrtDiscriminant = discriminant.sqrt(MathContext.DECIMAL128);

        BigDecimal x1 = b.negate().subtract(sqrtDiscriminant).divide(a.multiply(new BigDecimal("2")), MathContext.DECIMAL128);
        BigDecimal x2 = b.negate().add(sqrtDiscriminant).divide(a.multiply(new BigDecimal("2")), MathContext.DECIMAL128);
        return new BigDecimal[]{x1, x2};
    }

    public static long gcd(long number1, long number2) {
        while (number2 != 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return Math.abs(number1);
    }

    public static long lcm(List<Long> numbers) {
        return numbers.stream().reduce(1L, (a, b) -> a * b / SolutionUtils.gcd(a, b));
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

    private static final MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateSHA256(int[][] matrix) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                res.append(matrix[i][j] + '0');
            }
        }

        byte[] encodedHash = digest.digest(res.toString().getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void swapElements(int[][] matrix, int row1, int col1, int row2, int col2) {
        int temp = matrix[row1][col1];
        matrix[row1][col1] = matrix[row2][col2];
        matrix[row2][col2] = temp;
    }
}
