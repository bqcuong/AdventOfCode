package net.bqc.aoc;

import java.util.List;

public abstract class Solution {

    public enum Part {
        ONE, TWO
    }

    // Part Under Processing
    protected Part pup = Part.ONE;

    public long solve(Part part, List<String> inputLines) {
        this.pup = part;
        return 0;
    }
    
    public boolean isPart2() {
        return this.pup == Part.TWO;
    }
}
