package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18 extends Solution {

    enum Direction {
        NORTH("U", -1, 0), EAST("R", 0, 1),
        SOUTH("D", 1, 0), WEST("L", 0, -1);

        final String symbol;
        final int dx;
        final int dy;

        Direction(String symbol, int dx, int dy) {
            this.symbol = symbol;
            this.dx = dx;
            this.dy = dy;
        }

        static Direction findBySymbol(String symbol) {
            return Arrays.stream(values()).filter(x -> x.symbol.equals(symbol)).findFirst().orElse(null);
        }

        static Direction findByIndex(String idx) {
            return switch (idx) {
                case "0" -> EAST;
                case "1" -> SOUTH;
                case "2" -> WEST;
                case "3" -> NORTH;
                default -> null;
            };
        }
    }

    private final List<int[]> visitedPoints = new ArrayList<>();

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;

        long totalVisitedPoints = traversal(inputLines);

        long area = 0;
        for (int i = 0; i < visitedPoints.size(); i++) {
            int[] currentPoint = visitedPoints.get(i);
            int[] nextPoint = visitedPoints.get((i + 1) % visitedPoints.size());
            area += (long) currentPoint[0] * nextPoint[1] - (long) nextPoint[0] * currentPoint[1];
        }
        area = Math.abs(area) / 2;

        return area + 1 + totalVisitedPoints / 2;
    }

    private long traversal(List<String> inputLines) {
        int x = 0;
        int y = 0;

        long totalVisitedPoints = 0;

        for (String line : inputLines) {
            String[] parts = line.split("\s+");
            Direction direction = Direction.findBySymbol(parts[0]);
            int steps = Integer.parseInt(parts[1]);
            String color = parts[2].replaceAll("[()#]", "");

            if (this.pup == PART_NUMBER.TWO) {
                direction = Direction.findByIndex(color.substring(color.length() - 1));
                steps = Integer.parseInt(color.substring(0, color.length() - 1), 16);
            }

            x += direction.dx * steps;
            y += direction.dy * steps;

            totalVisitedPoints += steps;
            visitedPoints.add(new int[] {x, y});

        }

        return totalVisitedPoints;
    }
}
