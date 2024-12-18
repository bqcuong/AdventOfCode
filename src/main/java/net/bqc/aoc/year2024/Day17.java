package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 extends Solution<String> {

    private static class Program {
        long a;
        long b;
        long c;
        int[] inputs;
    }

    @Override
    public String solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        Program p = readPrograms(inputLines);
        if (isPart2()) {
            int toGuessDigits = p.inputs.length;
            long minA = (long) Math.pow(8, toGuessDigits - 1);
            long maxA = (long) Math.pow(8, toGuessDigits);
            List<Long> res = guessProgram(p, minA, maxA, toGuessDigits);
            return res.stream().min(Long::compareTo).get().toString();
        }
        else {
            return runProgram(p).stream().map(Object::toString).collect(Collectors.joining(","));
        }
    }

    private List<Long> guessProgram(Program p, long minA, long maxA, int toGuessDigits) {
        if (toGuessDigits < 0) return Collections.emptyList();
        List<Long> res = new ArrayList<>();

        boolean found = false;
        long nextMinA = minA;
        long lastInput = p.inputs[toGuessDigits-1];
        for (p.a = minA; p.a <= maxA; p.a += (long) Math.pow(8, toGuessDigits - 1)) {
            List<Long> outputs = runProgram(p);
            long lastOutput = outputs.get(toGuessDigits-1);
            if (found) {
                if (lastOutput != lastInput) {
                    found = false;
                    res.addAll(guessProgram(p, nextMinA, p.a, toGuessDigits - 1));
                }
            }

            if (lastOutput == lastInput) {
                if (toGuessDigits == 1) {
                    res.add(p.a);
                    break;
                }
                if (!found) {
                    nextMinA = p.a;
                    found = true;
                }
            }
        }
        return res;
    }

    private List<Long> runProgram(Program p) {
        List<Long> outputs = new ArrayList<>();
        long a = p.a, b = p.b, c = p.c;
        for (int i = 1; i <= p.inputs.length; i += 2) {
            long op = p.inputs[i - 1];
            long opr = p.inputs[i];
            switch ((int) op) {
                case 0 -> a >>= comboOperand(opr, a, b, c);
                case 1 -> b ^= opr;
                case 2 -> b = comboOperand(opr, a, b, c) % 8;
                case 3 -> { if (a != 0) i = (int) (opr - 1); }
                case 4 -> b ^= c;
                case 5 -> outputs.add(comboOperand(opr, a, b, c) % 8);
                case 6 -> b = a >> comboOperand(opr, a, b, c);
                case 7 -> c = a >> comboOperand(opr, a, b, c);
            }
        }
        return outputs;
    }

    private long comboOperand(long opr, long a, long b, long c) {
        return switch ((int) opr) {
            case 0, 1, 2, 3 -> opr;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> throw new IllegalStateException("Unexpected value: " + opr);
        };
    }

    private Program readPrograms(List<String> inputLines) {
        Program p = new Program();
        p.a = Long.parseLong(inputLines.get(0).split(": ")[1]);
        p.b = Long.parseLong(inputLines.get(1).split(": ")[1]);
        p.c = Long.parseLong(inputLines.get(2).split(": ")[1]);
        p.inputs = Arrays.stream(inputLines.get(4).split(": ")[1].split(","))
            .mapToInt(Integer::parseInt).toArray();
        return p;
    }
}
