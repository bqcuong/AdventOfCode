package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.MathUtils;

import java.util.*;

public class Day11 extends Solution {

    private final Map<String, Long> cache = new HashMap<>();

    @Override
    public long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        long[] stones = Arrays.stream(inputLines.get(0).split(" ")).mapToLong(Long::parseLong).toArray();
        return Arrays.stream(stones).map(stone -> blink(stone, isPart2() ? 75 : 25)).sum();
    }

    private long blink(long stone, int blinksLeft) {
        if (blinksLeft == 0) return 1;
        String key = stone + "-" + blinksLeft;
        if (cache.containsKey(key)) return cache.get(key);

        if (stone == 0) {
            cache.put(key, blink(1, blinksLeft - 1));
        }
        else {
            int numDigits = MathUtils.countDigits(stone);
            if (numDigits % 2 == 0) {
                long pow = (long) Math.pow(10, (double) numDigits / 2);
                cache.put(key, blink(stone / pow, blinksLeft - 1) + blink(stone % pow, blinksLeft - 1));
            }
            else {
                cache.put(key, blink(stone * 2024, blinksLeft - 1));
            }
        }
        return cache.get(key);
    }
}