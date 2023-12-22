package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.*;

public class Day19 extends Solution {

    static class Configuration extends HashMap<String, Integer> {

        public Configuration(List<Integer> cfgValues) {
            this.put("x", cfgValues.get(0));
            this.put("m", cfgValues.get(1));
            this.put("a", cfgValues.get(2));
            this.put("s", cfgValues.get(3));
        }

        long sum() {
            return this.values().stream().mapToLong(x -> x).sum();
        }
    }

    class MachinePart {
        String predicate;
        String output;

        MachinePart(String specification) {
            String[] p = specification.split(":");
            if (p.length < 2) {
                output = specification;
                predicate = null;
            }
            else {
                output = p[1];
                predicate = p[0];
            }
        }

        String check(Configuration cfg) {
            if (predicate == null) {
                return output;
            }

            String cat = predicate.substring(0, 1);
            int cfgValue = cfg.get(cat);

            String operator = predicate.substring(1, 2);
            int value = Integer.parseInt(predicate.substring(2));

            if (">".equals(operator) && cfgValue > value
            || "<".equals(operator) && cfgValue < value) {
                return output;
            }

            return null;
        }
    }

    class Machine {
        List<MachinePart> parts = new ArrayList<>();

        public Machine(String machineSpecification) {
            String[] ps = machineSpecification.split(",");
            for (String p : ps) {
                parts.add(new MachinePart(p));
            }
        }

        String check(Configuration cfg) {
            for (MachinePart part : parts) {
                String output = part.check(cfg);
                if (output != null) {
                    return output;
                }
            }
            return null;
        }
    }

    Map<String, Machine> machineMap = new HashMap<>();
    List<Configuration> configs = new ArrayList<>();

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;

        parseMachineAndConfiguration(inputLines);

        return configs.stream()
            .filter(cfg -> "A".equals(checkCompatibility(cfg)))
            .mapToLong(Configuration::sum)
            .sum();
    }

    private String checkCompatibility(Configuration configuration) {
        Machine currentMachine = machineMap.get("in");
        String output = null;

        while (output == null || !output.equals("A") && !output.equals("R")) {
            output = currentMachine.check(configuration);
            currentMachine = machineMap.get(output);
        }

        return output;
    }

    private void parseMachineAndConfiguration(List<String> inputLines) {
        int lineIdx = 0;
        while (!inputLines.get(lineIdx).isBlank()) {
            String line = inputLines.get(lineIdx);

            String[] p = line.split("\\{");
            machineMap.put(p[0], new Machine(p[1].substring(0, p[1].length() - 1)));
            lineIdx++;
        }

        lineIdx++;
        while (lineIdx < inputLines.size()) {
            String line = inputLines.get(lineIdx);
            List<Integer> cfgValues = Arrays.stream(line.replaceAll("[{}]", "").split(","))
                .map(x -> Integer.parseInt(x.split("=")[1])).toList();

            configs.add(new Configuration(cfgValues));
            lineIdx++;
        }
    }
}
