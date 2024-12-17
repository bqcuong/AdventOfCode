package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.*;
import java.util.stream.Collectors;

public class Day05 extends Solution<Long> {

    private Map<Integer, Set<Integer>> pageOrderMap = new HashMap<>();
    private List<List<Integer>> printUpdates = new ArrayList<>();

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        readInputs(inputLines);

        List<List<Integer>> chosenUpdates;

        if (!isPart2()) {
            chosenUpdates = this.printUpdates.stream().filter(this::isValidUpdate).toList();
        }
        else {
            chosenUpdates = this.printUpdates.stream().filter(x -> !this.isValidUpdate(x)).toList();
            chosenUpdates.forEach(this::repairUpdate);
        }

        return chosenUpdates.stream().mapToLong(x -> x.get(x.size()/2)).sum();
    }

    private void repairUpdate(List<Integer> printUpdate) {
        printUpdate.sort((l, r) -> checkPageOrder(l, r) ? -1 : 1);
    }

    private boolean isValidUpdate(List<Integer> printUpdate) {
        if (printUpdate.size() <= 1) return true;
        int l = 0;
        int r = 1;
        while (r < printUpdate.size()) {
            if (!checkPageOrder(printUpdate.get(l), printUpdate.get(r))) return false;
            l++; r++;
        }
        return true;
    }

    private boolean checkPageOrder(Integer lPage, Integer rPage) {
        if (pageOrderMap.containsKey(lPage)) return pageOrderMap.get(lPage).contains(rPage);
        if (pageOrderMap.containsKey(rPage)) return !pageOrderMap.get(rPage).contains(lPage);
        return true;
    }

    private void readInputs(List<String> inputLines) {
        for (String line : inputLines) {
            if (line.isBlank()) continue;
            if (line.contains("|")) {
                String[] pageOrder = line.split("\\|");
                Integer key = Integer.valueOf(pageOrder[0]);
                if (!pageOrderMap.containsKey(key)) pageOrderMap.put(key, new HashSet<>());
                pageOrderMap.get(key).add(Integer.parseInt(pageOrder[1]));
            }
            else {
                printUpdates.add(Arrays.stream(line.split(",")).map(Integer::valueOf)
                    .collect(Collectors.toList()));
            }
        }
    }
}
