package net.bqc.aoc;

import java.util.List;

public abstract class Solution {

    public enum PART_NUMBER {
        ONE, TWO
    }

    // Part Under Processing
    protected PART_NUMBER pup = PART_NUMBER.ONE;

    public abstract long solve(PART_NUMBER part, List<String> inputLines);
    
    public boolean isPart2() {
        return this.pup == PART_NUMBER.TWO;
    }
}
