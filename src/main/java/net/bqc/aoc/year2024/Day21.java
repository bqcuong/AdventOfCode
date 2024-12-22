package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import net.bqc.aoc.utils.GraphUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day21 extends Solution<Long> {

    private final static char[][] NUMPAD = new char[][]{
        {'7', '8', '9'},
        {'4', '5', '6'},
        {'1', '2', '3'},
        {' ', '0', 'A'}
    };

    private final static char[][] DPAD = new char[][]{
        {' ', '^', 'A'},
        {'<', 'v', '>'}
    };

    // Saving <String, String> occupies much more space and might cause heap out of memory
    private final static HashMap<String, Long> memorization = new HashMap<>();

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        return inputLines.stream()
            .map(l -> Long.parseLong(l.substring(0, l.length() - 1)) * getMinCodeLength(NUMPAD, l, isPart2() ? 26 : 3))
            .reduce(0L, Long::sum);
    }

    private long getMinCodeLength(char[][] pad, String code, int depth) {
        String key = code + "-" + depth;
        if (memorization.containsKey(key)) return memorization.get(key);

        long length = 0L;
        char startCh = 'A';
        for (char codeCh : code.toCharArray()) {
            List<String> paths = shortestPaths(pad, startCh, codeCh);
            long minLength = Long.MAX_VALUE;
            if (depth == 1) {
                minLength = paths.get(0).length();
            }
            else {
                for (String path : paths) {
                    minLength = Math.min(minLength, getMinCodeLength(DPAD, path, depth - 1));
                }
            }
            length += minLength;
            startCh = codeCh;
        }
        memorization.put(key, length);
        return length;
    }

    private List<String> shortestPaths(char[][] pad, char start, char end) {
        return GraphUtils.shortestPaths(pad, start, end, ' ').stream()
            .map(p -> p.stream().map(n -> n.direction.symbol).skip(1).collect(Collectors.joining("")) + "A")
            .collect(Collectors.toList());
    }
}