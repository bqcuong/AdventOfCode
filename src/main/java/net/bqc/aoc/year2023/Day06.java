package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.SolutionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day06 extends Solution {

    public BigInteger solve2(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;
        List<BigDecimal> timeAmounts = Arrays.stream(inputLines.get(0).split(":")[1].trim().split("\s+"))
            .map(BigDecimal::new).toList();

        List<BigDecimal> distanceRecords = Arrays.stream(inputLines.get(1).split(":")[1].trim().split("\s+"))
            .map(BigDecimal::new).toList();

        if (this.pup == PART_NUMBER.ONE) {
            return IntStream.range(0, timeAmounts.size())
                .mapToObj(i -> countStrategies(timeAmounts.get(i), distanceRecords.get(i)))
                .reduce(BigInteger.ONE, BigInteger::multiply);
        }
        else {
            BigDecimal time = new BigDecimal(timeAmounts.stream().map(Object::toString).collect(Collectors.joining("")));
            BigDecimal distance = new BigDecimal(distanceRecords.stream().map(Object::toString).collect(Collectors.joining("")));
            return countStrategies(time, distance);
        }
    }

    public BigInteger countStrategies(BigDecimal time, BigDecimal distance) {
        BigDecimal[] roots = SolutionUtils.solveQuadraticEquation(BigDecimal.ONE, time.negate(), distance);
        BigDecimal start = roots[0].add(BigDecimal.ONE).setScale(0, RoundingMode.FLOOR);
        BigDecimal end = roots[1].subtract(BigDecimal.ONE).setScale(0, RoundingMode.CEILING);
        return end.subtract(start).add(BigDecimal.ONE).toBigInteger();
    }

    @Override
    public int solve(PART_NUMBER part, List<String> inputLines) {
        return 0;
    }
}
