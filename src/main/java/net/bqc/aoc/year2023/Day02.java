package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02 extends Solution {

    private static class Requirements {
        long gameId;
        Map<String, Long> requirements = new HashMap<>();
    }

    private final Map<String, Long> providedCubes = new HashMap<>() {{
        put("red", 12L);
        put("green", 13L);
        put("blue", 14L);
    }};

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;

        if (!isPart2()) {
            return inputLines.stream().mapToLong(this::checkGamePossible).filter(x -> x > 0).sum();
        }
        else {
            return inputLines.stream()
                .mapToLong(
                    x -> getRequirements(x).requirements.values().stream()
                        .reduce(1L, (a, b) -> a * b)
                ).sum();
        }
    }

    public long checkGamePossible(String gameConfiguration) {
        Requirements r = getRequirements(gameConfiguration);
        for (String key : providedCubes.keySet()) {
            if (providedCubes.get(key) < r.requirements.get(key)) {
                return -1;
            }
        }
        return r.gameId;
    }

    private Requirements getRequirements(String gameConfiguration) {
         Requirements r = new Requirements();

        String[] parts = gameConfiguration.split(":");
        r.gameId = Long.parseLong(parts[0].split("\s")[1]);

        String[] gameRounds = parts[1].split(";");
        for (String round : gameRounds) {
            List<String> cubes = Arrays.stream(round.trim().split(",")).map(String::trim).toList();
            for (String cube : cubes) {
                String cubeColor = cube.split("\s")[1];
                long requiredQuantity = Long.parseLong(cube.split("\s")[0]);

                if (!r.requirements.containsKey(cubeColor) || r.requirements.get(cubeColor) < requiredQuantity) {
                    r.requirements.put(cubeColor, requiredQuantity);
                }
            }
        }
        return r;
    }
}
