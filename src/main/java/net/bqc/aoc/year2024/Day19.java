package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.Arrays;
import java.util.List;

public class Day19 extends Solution<Long> {

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        List<String> towels = Arrays.asList(inputLines.get(0).split(", "));
        List<String> designs = inputLines.subList(2, inputLines.size());
        List<Long> res = designs.stream().map(d -> countPossibleDesign(d, towels)).toList();
        return isPart2() ? res.stream().reduce(0L, Long::sum) : res.stream().filter(r -> r > 0).count();
    }

    private long countPossibleDesign(String design, List<String> towels) {
        long[] dp = new long[design.length()];
        Arrays.fill(dp, -1);
        return dp(design, towels, dp, design.length() - 1);
    }

    private long dp(String design, List<String> towels, long[] dp, int i) {
        if (i < 0) return 1;
        if (dp[i] != -1) return dp[i];

        dp[i] = 0;
        for (String towel : towels) {
            int startPos = i - towel.length() + 1;
            if (startPos >= 0 && design.substring(startPos, i + 1).equals(towel)) {
                long prevDp = dp(design, towels, dp, i - towel.length());
                if (prevDp > 0) dp[i] += prevDp;
            }
        }
        return dp[i];
    }
}