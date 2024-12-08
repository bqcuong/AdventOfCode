package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.SolutionUtils;

import java.util.HashMap;
import java.util.List;

public class Day08 extends Solution {

    class Problem {
        String instructions;
        HashMap<String, String> maps = new HashMap<>();
    }

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;

        Problem p = parseProblem(inputLines);

        if (!isPart2()) {
            return travelInMaps(p, "AAA");
        }
        else {
            List<String> startLocs = p.maps.keySet().stream()
                .filter(x -> x.endsWith("AL"))
                .map(x -> x.substring(0, 3))
                .toList();

            List<Long> steps = startLocs.stream()
                .map(l -> travelInMaps(p, l))
                .toList();

            return SolutionUtils.lcm(steps);
        }
    }

    private boolean checkEndNode(String node) {
        if (!isPart2() && node.equals("ZZZ")) {
            return true;
        }

        return isPart2() && node.endsWith("Z");
    }

    public long travelInMaps(Problem p, String startLoc) {
        String currentLoc = startLoc;
        int step = 0;

        while (true) {
            if (checkEndNode(currentLoc)) {
                break;
            }

            char instruction = p.instructions.charAt(step % p.instructions.length());
            currentLoc = p.maps.get(currentLoc + instruction);
            step += 1;
        }
        return step;
    }

    public Problem parseProblem(List<String> inputLines) {
        Problem p = new Problem();
        p.instructions = inputLines.get(0).trim();

        for (int i = 2; i < inputLines.size(); i++) {
            String[] parts = inputLines.get(i).split("=");
            String key = parts[0].trim();

            parts = parts[1].trim().replaceAll("[\\(\\)\s]", "").split(",");
            String left = parts[0];
            String right = parts[1];

            p.maps.put(key + "L", left);
            p.maps.put(key + "R", right);
        }
        return p;
    }
}
