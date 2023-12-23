package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.List;

public class Day20 extends Solution {

    enum Pulse {
        LOW(0), HIGH(1);
        final int status;
        Pulse(int status) { this.status = status; }
    }

    class FlipFlop {
        int status = 0;

        Pulse transfer(Pulse pulse) {
            if (pulse == Pulse.LOW && status == 0) {
                status = 1;
                return Pulse.HIGH;
            }
            else if (pulse == Pulse.LOW && status == 1) {
                status = 0;
                return Pulse.LOW;
            }
            return null;
        }
    }

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;
        return 0;
    }
}
