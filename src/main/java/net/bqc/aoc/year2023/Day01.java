package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01 extends Solution {

    private static final Map<String, Character> NUM_MAP = new HashMap<>() {{
       put("one", '1');
       put("two", '2');
       put("three", '3');
       put("four", '4');
       put("five", '5');
       put("six", '6');
       put("seven", '7');
       put("eight", '8');
       put("nine", '9');
    }};

    private char checkDigit(String line, int pos) {
        char ch = line.charAt(pos);

        if (ch - '0' >= 0 && ch - '0' <= 9) {
            return ch;
        }

        // Additional computation for Part 2
        if (isPart2()) {
            String subStr = line.substring(pos);
            for (String key : NUM_MAP.keySet()) {
                if (subStr.startsWith(key)) {
                    return NUM_MAP.get(key);
                }
            }
        }

        return 0;
    }

    public long parseCalibrationValue(String calibrationLine) {
        char firstDigit = 0;
        char lastDigit = 0;

        for (int i = 0; i < calibrationLine.length(); i++) {
            char ch = checkDigit(calibrationLine, i);
            if (ch > 0) {
                firstDigit = ch;
                break;
            }
        }

        if (firstDigit == 0) { // not found calibration value
            return 0;
        }

        for (int i = calibrationLine.length() - 1; i >= 0 ; i--) {
            char ch = checkDigit(calibrationLine, i);
            if (ch > 0) {
                lastDigit = ch;
                break;
            }
        }

        return Long.parseLong("" + firstDigit + lastDigit);
    }

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        super.solve(part, inputLines);
        return inputLines.stream().mapToLong(this::parseCalibrationValue).sum();
    }
}
