package net.bqc.aoc.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class MathUtils {
    public static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
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
        return numbers.stream().reduce(1L, (a, b) -> a * b / gcd(a, b));
    }

    public static int countDigits(long num) {
        return (int)Math.floor(Math.log10(num) + 1);
    }
}
