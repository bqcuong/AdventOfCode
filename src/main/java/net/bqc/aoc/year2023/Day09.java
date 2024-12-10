package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day09 extends Solution {

    @Override
    public long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        List<List<Long>> histories = inputLines.stream()
            .map(l -> Arrays.stream(l.split("\s")).map(Long::parseLong).toList())
            .toList();

        if (!isPart2()) {
            return histories.stream().mapToLong(this::predict).sum();
        }
        else {
            return histories.stream().mapToLong(this::predictBackward).sum();
        }
    }

    public long predict(List<Long> history) {
        List<Long> diffs = new ArrayList<>();

        if (Objects.equals(history.get(history.size() - 1), history.get(history.size() - 2))) {
            return history.get(history.size() - 1);
        }

        for (int i = history.size() - 1; i > 0 ; i--) {
            long diff = history.get(i) - history.get(i - 1);
            diffs.add(0, diff);
        }
        return predict(diffs) + history.get(history.size() - 1);
    }

    public long predictBackward(List<Long> history) {
        List<Long> diffs = new ArrayList<>();

        if (Objects.equals(history.get(history.size() - 1), history.get(history.size() - 2))) {
            return history.get(0);
        }

        for (int i = 0; i < history.size() - 1; i++) {
            long diff = history.get(i + 1) - history.get(i);
            diffs.add(diff);
        }
        return history.get(0) - predictBackward(diffs);
    }
}
