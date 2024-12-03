package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day03 extends Solution {

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;

        final String mulRegex = "mul\\(\\d+,\\d+\\)";
        long sum = 0;
        for (String line : inputLines) {
            List<String> instructions = extractValidInstructions(line, mulRegex);
            int sum1 = 0;
            for (String instruction : instructions) {
                instruction = instruction.replace("mul(", "").replace(")", "");
                long[] arr = Arrays.stream(instruction.split(",")).mapToLong(Long::parseLong).toArray();
                sum1 += arr[0] * arr[1];
            }
            sum += sum1;

            System.out.println(line);
            System.out.println(sum1);
            System.out.println(solve(line, false));

        }

        return sum;
    }

    private int solve(String input, boolean ignoreConditions) {
        Pattern pattern = Pattern.compile("(mul\\((\\d+),(\\d+)\\))|(do\\(\\))|(don't\\(\\))");
        Matcher matcher = pattern.matcher(input);
        AtomicBoolean isEnabled = new AtomicBoolean(true);

        return matcher.results()
            .mapToInt(result -> {
                if (result.group(1) != null && (ignoreConditions || isEnabled.get())) {
                    return parseInt(result.group(2)) * parseInt(result.group(3));
                } else if (!ignoreConditions) {
                    if (result.group(4) != null) {
                        isEnabled.set(true);
                    } else if (result.group(5) != null) {
                        isEnabled.set(false);
                    }
                }
                return 0;
            })
            .sum();
    }

    private List<String> extractValidInstructions(String input, String regex) {
        if (this.pup == PART_NUMBER.TWO) {
            StringBuilder inputBuilder = new StringBuilder(input);
            int dontIdx;
            while ((dontIdx = inputBuilder.indexOf("don't()")) >= 0) {
                int nextDoIdx = inputBuilder.indexOf("do()", dontIdx);
                if (nextDoIdx == -1) nextDoIdx = inputBuilder.length();
                inputBuilder.replace(dontIdx, nextDoIdx, "");
            }
            input = inputBuilder.toString();
            System.out.println(input);
        }

        List<String> instructions = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            instructions.add(matcher.group());
        }
        return instructions;
    }
}
