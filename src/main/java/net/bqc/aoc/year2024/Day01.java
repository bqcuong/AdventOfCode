package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01 extends Solution<Long> {

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        int[] list1 = new int[inputLines.size()];
        int[] list2 = new int[inputLines.size()];
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < inputLines.size(); i++) {
            String[] parts = inputLines.get(i).split("\s+");
            list1[i] = Integer.parseInt(parts[0]);
            list2[i] = Integer.parseInt(parts[1]);
            map.put(list2[i], map.getOrDefault(list2[i] , 0) + 1);
        }

        Arrays.sort(list1);
        Arrays.sort(list2);

        int sum = 0;
        for (int i = 0; i < list1.length; i++) {
            sum += !isPart2() ? Math.abs(list2[i] - list1[i]) : list1[i] * map.getOrDefault(list1[i], 0);
        }
        return (long) sum;
    }
}
