package net.bqc.aoc.utils;

import java.math.BigDecimal;
import java.math.MathContext;

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
}
