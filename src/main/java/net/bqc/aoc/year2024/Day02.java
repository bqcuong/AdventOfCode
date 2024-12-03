package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day02 extends Solution {

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;

        if (part == PART_NUMBER.ONE) return inputLines.stream().filter(this::isSafe).count();
        return inputLines.stream().filter(this::isSafeWithDampener).count();
    }

    public boolean isSafeWithDampener(String line) {
        if (isSafe(line)) return true;
        List<String> levelStrs = Arrays.asList(line.split("\s+"));
        for (int removedIdx = 0; removedIdx < levelStrs.size(); removedIdx++) {
            int finalRemovedIdx = removedIdx;
            String newLine = IntStream.range(0, levelStrs.size()).filter(idx -> idx != finalRemovedIdx)
                .mapToObj(levelStrs::get).collect(Collectors.joining(" "));
            if (isSafe(newLine)) return true;
        }
        return false;
    }

    public boolean isSafe(String line) {
        int[] levels = Arrays.stream(line.split("\s+")).mapToInt(Integer::parseInt).toArray();
        Boolean increasing = null;
        for (int i = 1; i < levels.length; i++) {
            int diff = levels[i] - levels[i-1];
            if (increasing == null) {
                increasing = diff > 0;
            }
            boolean unsafeSign = increasing && diff <= 0 || !increasing && diff >= 0;
            boolean unsafeDistance = increasing && diff > 3 || !increasing && diff < -3; // cannot be fixed to be safe
            if (unsafeSign || unsafeDistance) return false;
        }
        return true;
    }
}
