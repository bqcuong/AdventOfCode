package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day25 extends Solution<Long> {

    private final List<int[]> locks = new ArrayList<>();
    private final List<int[]> keys = new ArrayList<>();

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        readLocksAndKeys(inputLines);
        return countKeyLockPairs();
    }

    private long countKeyLockPairs() {
        long count = 0;
        for (int[] key : keys) {
            for (int[] lock : locks) {
                count += IntStream.range(0, lock.length).map(i -> key[i] + lock[i]).filter(s -> s > 5).count() > 0 ? 0 : 1;
            }
        }
        return count;
    }

    private void readLocksAndKeys(List<String> inputLines) {
        int i = 0;
        while (i < inputLines.size()) {
            char[][] schematicStr = Array2DUtils.readAsMatrix(inputLines.subList(i, i + 7));
            final int[] schematic = new int[]{-1, -1, -1, -1, -1};
            IntStream.range(0, 5).forEach(
                col -> IntStream.range(0, 7).forEach(row -> schematic[col] += schematicStr[row][col] == '#' ? 1 : 0));
            if (CharBuffer.wrap(schematicStr[0]).chars().filter(c -> c == '#').count() == 0) keys.add(schematic);
            else locks.add(schematic);
            i += 8;
        }
    }
}