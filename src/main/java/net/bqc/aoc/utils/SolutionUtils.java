package net.bqc.aoc.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class SolutionUtils {

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

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.println();
        }
    }
}
