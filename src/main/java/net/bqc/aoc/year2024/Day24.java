package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day24 extends Solution<String> {

    private final Map<String, Integer> wires = new HashMap<>();
    private final Map<String, String> gates = new HashMap<>();

    @Override
    public String solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        readWiresAndGates(inputLines);
        return isPart2() ? inputLines.size() < 50 ? "" : detectFaultyGates()
            : "" + computeWiresSum(gates.keySet().stream().filter(x -> x.startsWith("z")).sorted().toList());
    }

    /**
     * An adder using three 1-bit numbers: X, Y, C (carry)
     * i = 0, C(i) = ∅, Z(i) = X(i) ⊕ Y(i)
     * 0 < i < n, C(i) = X(i-1) & Y(i-1) | C(i-1) & [X(i-1) ⊕ Y(i-1)], Z(i) = X(i) ⊕ Y(i) ⊕ C(i)
     * i = n, C(i) = ~, Z(i) = C(i)
     */
    public String detectFaultyGates() {
        Function<Integer, String> fx = i -> String.format("x%02d", i);
        Function<Integer, String> fy = i -> String.format("y%02d", i);
        Function<Integer, String> fz = i -> String.format("z%02d", i);

        int i = 0;
        String c = "", z = "", xorXY = "", andXY = "";
        long n = gates.keySet().stream().filter(k -> k.charAt(0) == 'z').count();
        List<String> swappedGates = new ArrayList<>();
        while (i < n) {
            if (i == 0) {
                z = findGates(fx.apply(i), "XOR", fy.apply(i));
            }
            else {
                c = i == 1 ? andXY : findGates(andXY, "OR", findGates(c, "AND", xorXY));
                xorXY = findGates(fx.apply(i), "XOR", fy.apply(i));
                z = findGates(xorXY, "XOR", c);
            }

            andXY = findGates(fx.apply(i), "AND", fy.apply(i));

            if (z == null || !fz.apply(i).equals(z)) {
                List<String> faultyGates = z == null ? List.of(andXY, xorXY) : List.of(z, fz.apply(i));
                swapGates(faultyGates.get(0), faultyGates.get(1));
                swappedGates.addAll(faultyGates);
                if (swappedGates.size() >= 8) break;
                c = z = xorXY = andXY = "";
                i = 0;
                continue;
            }
            i++;
        }
        return swappedGates.stream().sorted().collect(Collectors.joining(","));
    }

    private void swapGates(String g1, String g2) {
        String tmp = gates.get(g1);
        gates.put(g1, gates.get(g2));
        gates.put(g2, tmp);
    }

    private String findGates(String op1, String op, String op2) {
        return gates.entrySet().stream()
            .filter(e -> (op1 + " " + op + " " + op2 + "|" + op2 + " " + op + " " + op1).contains(e.getValue()))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);
    }

    public long computeWiresSum(List<String> wires) {
        long sum = 0;
        for (int i = 0; i < wires.size(); i++) {
            sum += (long) (Math.pow(2, i) * computeWire(wires.get(i)));
        }
        return sum;
    }

    public int computeWire(String wire) {
        if (wires.containsKey(wire)) return wires.get(wire);
        String[] ops = gates.get(wire).split(" ");
        switch (ops[1]) {
            case "AND" -> wires.put(wire, computeWire(ops[0]) & computeWire(ops[2]));
            case "OR" -> wires.put(wire, computeWire(ops[0]) | computeWire(ops[2]));
            case "XOR" -> wires.put(wire, computeWire(ops[0]) ^ computeWire(ops[2]));
            default -> throw new IllegalStateException("Unexpected value: " + ops[1]);
        }
        return wires.get(wire);
    }

    private void readWiresAndGates(List<String> inputLines) {
        for (String line : inputLines) {
            if (line.contains(": ")) {
                wires.put(line.split(": ")[0], Integer.parseInt(line.split(": ")[1]));
            }
            else if (line.contains(" -> ")) {
                gates.put(line.split(" -> ")[1], line.split(" -> ")[0]);
            }
        }
    }
}