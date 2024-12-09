package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.Arrays;
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

        int[] originalFileBlocks = Arrays.copyOf(fileBlocks, fileBlocks.length);

        int globalId = fileBlocks[0]; // skip the first 0id-blocks
        while (emptyId < emptyBlocks.length && fileId >= 0) {
            int movingSize = Math.min(emptyBlocks[emptyId], fileBlocks[fileId]);
            if (isPart2()) {
                // always check from the most-right file block (was not moved) if one fits the current empty block
                fileId = fileBlocks.length - 1;
                while ((fileBlocks[fileId] <= 0 || fileBlocks[fileId] > emptyBlocks[emptyId]) && fileId > emptyId) fileId--;
                if (fileId <= emptyId) { // not found any file block fitting the current empty block
                    globalId += emptyBlocks[emptyId];
                    emptyId++; // try the next empty block
                    if (emptyId >= emptyBlocks.length) break;

                    globalId = visitFileBlock(emptyId, originalFileBlocks, globalId);
                    movingSize = 0;
                }
                else {
                    movingSize = fileBlocks[fileId];
                }
            }

            while (movingSize > 0) {
                checkSum += (long) fileId * globalId; // adding into the checksum the moved file block
                if (globalId == 11) {
                    System.out.println();
                }
                globalId++;
                movingSize--;
                fileBlocks[fileId]--;
                emptyBlocks[emptyId]--;
            }

            if (emptyBlocks[emptyId] == 0) {
                emptyId++;
                globalId = visitFileBlock(emptyId, originalFileBlocks, globalId);
            }
            if (fileBlocks[fileId] <= 0) fileId--;
        }
    }

    private int visitFileBlock(int emptyId, int[] originalFileBlocks, int globalId) {
        // skip the original location of the file block that was moved
        if (fileBlocks[emptyId] == 0 && originalFileBlocks[emptyId] > 0) globalId += originalFileBlocks[emptyId];

        while (fileBlocks[emptyId] > 0) {
            checkSum += (long) emptyId * globalId; // adding into the checksum the existing file block
            globalId++;
            fileBlocks[emptyId]--;
        }
        return globalId;
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
