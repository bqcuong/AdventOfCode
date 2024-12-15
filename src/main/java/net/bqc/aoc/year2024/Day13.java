package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 extends Solution {

    private class Pos {
        public long x;
        public long y;

        public Pos(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private class ClawMachine {
        Pos A;
        Pos B;
        Pos prize;

        public ClawMachine(Pos A, Pos B, Pos prize) {
            this.A = A;
            this.B = B;
            this.prize = prize;
        }
    }

    private List<ClawMachine> clawMachines = new ArrayList<>();

    @Override
    public long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        readClawMachines(inputLines);

        long offset = isPart2() ? 10000000000000L : 0;
        clawMachines.forEach(c -> {
            c.prize.x += offset;
            c.prize.y += offset;
        });
        return clawMachines.stream().mapToLong(this::numOfTokens).sum();
    }

    private long numOfTokens(ClawMachine clawMachine) {
        // this problem can be transformed longo a system of linear equations
        // aX * i  + bX * j = prizeX
        // aY * i  + bY * j = prizeY

        Pos A = clawMachine.A, B = clawMachine.B, prize = clawMachine.prize;
        long D = A.x * B.y - A.y * B.x;
        // long Dx = (long) prize.x * B.y - (long) prize.y * B.x;
        long Dy = A.x * prize.y - A.y * prize.x;

        long j = Dy / D;
        long i = (prize.x - B.x * j) / A.x;
        return A.y * i + B.y * j == prize.y && A.x * i + B.x * j == prize.x ? 3 * i + j : 0;
    }

    private void readClawMachines(List<String> inputLines) {
        Pattern REGEX_A = Pattern.compile("Button A: X\\+(\\d+), Y\\+(\\d+)");
        Pattern REGEX_B = Pattern.compile("Button B: X\\+(\\d+), Y\\+(\\d+)");
        Pattern REGEX_PRIZE = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

        Pos A = null, B = null, prize;
        for (String line : inputLines) {
            Matcher matcherA = REGEX_A.matcher(line);
            Matcher matcherB = REGEX_B.matcher(line);
            Matcher matcherPrize = REGEX_PRIZE.matcher(line);

            if (matcherA.matches()) {
                A = new Pos(Long.parseLong(matcherA.group(1)), Long.parseLong(matcherA.group(2)));
            }
            else if (matcherB.matches()) {
                B = new Pos(Long.parseLong(matcherB.group(1)), Long.parseLong(matcherB.group(2)));
            }
            else if (matcherPrize.matches()) {
                prize = new Pos(Long.parseLong(matcherPrize.group(1)), Long.parseLong(matcherPrize.group(2)));
                this.clawMachines.add(new ClawMachine(A, B, prize));
            }
        }
    }
}
