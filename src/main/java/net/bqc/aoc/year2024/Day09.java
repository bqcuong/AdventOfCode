package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.List;

public class Day09 extends Solution {

    private int[] fileBlocks;
    private int[] emptyBlocks;
    private long checkSum;

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        super.solve(part, inputLines);
        readBlocks(inputLines);
        moveBlocks();
        return checkSum;
    }

    private void moveBlocks() {
        int emptyId = 0;
        int fileId = fileBlocks.length - 1;

        int globalId = fileBlocks[0]; // skip the first 0id-blocks
        while (emptyId < emptyBlocks.length && fileId >= 0) {
            int movingSize = Math.min(emptyBlocks[emptyId], fileBlocks[fileId]);
            while (movingSize > 0) {
                checkSum += (long) fileId * globalId; // adding into the checksum moved file block
                globalId++;
                movingSize--;
                fileBlocks[fileId]--;
                emptyBlocks[emptyId]--;
            }

            if (emptyBlocks[emptyId] == 0) {
                emptyId++;
                while (fileBlocks[emptyId]-- > 0) {
                    checkSum += (long) emptyId * globalId; // adding into the checksum existing file block
                    globalId++;
                }
            }
            if (fileBlocks[fileId] <= 0) fileId--;
        }
    }

    private void readBlocks(List<String> inputLines) {
        String line = inputLines.get(0);
        emptyBlocks = new int[line.length() / 2];
        fileBlocks = new int[line.length() - emptyBlocks.length];
        for (int i = 0, k = 0, l = 0; i < line.length(); i++) {
            if (i % 2 == 0) fileBlocks[k++] = line.charAt(i) - '0';
            else emptyBlocks[l++] = line.charAt(i) - '0';
        }
    }
}
