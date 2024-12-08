package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day07 extends Solution {

    private static class Equation {
        long testValue;
        List<Long> numbers;

        public Equation(long testValue, List<Long> numbers) {
            this.testValue = testValue;
            this.numbers = numbers;
        }
    }

    private List<Equation> equations = new ArrayList<>();

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;
        readEquations(inputLines);
        return equations.stream().filter(e -> this.tryEquation(e, -1, 0)).mapToLong(e -> e.testValue).sum();
    }

    private boolean tryEquation(Equation eq, int idx, long sum) {
        if (sum == eq.testValue) return true;
        else if (sum > eq.testValue) return false;
        if (idx + 1 >= eq.numbers.size()) return false;

        long nextNum = eq.numbers.get(idx + 1);
        if (idx >= 0) {
            if (tryEquation(eq, idx + 1, sum * nextNum)) return true;
            if (this.pup == PART_NUMBER.TWO && tryEquation(eq, idx + 1, Long.parseLong(sum + String.valueOf(nextNum)))) return true;
        }
        return tryEquation(eq, idx + 1, sum + eq.numbers.get(idx + 1));
    }

    private void readEquations(List<String> inputLines) {
        for (String line : inputLines) {
            String[] parts = line.split(": ");
            equations.add(new Equation(Long.parseLong(parts[0]),
                Arrays.stream(parts[1].split(" ")).map(Long::parseLong).toList()));
        }
    }
}
