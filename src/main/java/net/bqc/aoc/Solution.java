package net.bqc.aoc;

import java.util.List;

public abstract class Solution<T> {

    public enum Part {
        ONE, TWO
    }

    // Part Under Processing
    protected Part pup = Part.ONE;

    public T solve(Part part, List<String> inputLines) {
        this.pup = part;
        return null;
    }
    
    public boolean isPart2() {
        return this.pup == Part.TWO;
    }
}
