package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends Solution {

    @Override
    public long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);

        final String mulRegex = "mul\\(\\d+,\\d+\\)|(don't\\(\\))|(do\\(\\))";
        String line = String.join("", inputLines);
        long sum = 0;
        List<String> instructions = extractValidInstructions(line, mulRegex);
        for (String instruction : instructions) {
            instruction = instruction.replace("mul(", "").replace(")", "");
            long[] arr = Arrays.stream(instruction.split(",")).mapToLong(Long::parseLong).toArray();
            sum += (arr[0] * arr[1]);
        }

        return sum;
    }

    private List<String> extractValidInstructions(String input, String regex) {
        List<String> instructions = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        boolean enableInstruction = true;
        while (matcher.find()) {
            String matched = matcher.group();
            if (matched.equals("don't()")) {
                enableInstruction = false;
                continue;
            }
            else if (matched.equals("do()")) {
                enableInstruction = true;
                continue;
            }
            if (!isPart2() || enableInstruction) instructions.add(matched);
        }
        return instructions;
    }
}
