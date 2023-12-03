package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02 extends Solution {

    private static class Requirements {
        int gameId;
        Map<String, Integer> requirements = new HashMap<>();
    }

    private final Map<String, Integer> providedCubes = new HashMap<>() {{
        put("red", 12);
        put("green", 13);
        put("blue", 14);
    }};

    @Override
    public int solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;

        if (this.pup == PART_NUMBER.ONE) {
            return inputLines.stream().mapToInt(this::checkGamePossible).filter(x -> x > 0).sum();
        }
        else {
            return inputLines.stream()
                .mapToInt(
                    x -> getRequirements(x).requirements.values().stream()
                        .reduce(1, (a, b) -> a * b)
                ).sum();
        }
    }

    public int checkGamePossible(String gameConfiguration) {
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
        r.gameId = Integer.parseInt(parts[0].split("\s")[1]);

        String[] gameRounds = parts[1].split(";");
        for (String round : gameRounds) {
            List<String> cubes = Arrays.stream(round.trim().split(",")).map(String::trim).toList();
            for (String cube : cubes) {
                String cubeColor = cube.split("\s")[1];
                int requiredQuantity = Integer.parseInt(cube.split("\s")[0]);

                if (!r.requirements.containsKey(cubeColor) || r.requirements.get(cubeColor) < requiredQuantity) {
                    r.requirements.put(cubeColor, requiredQuantity);
                }
            }
        }
        return r;
    }
}
