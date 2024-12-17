package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;
import net.bqc.aoc.utils.Pos;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 extends Solution<Long> {

    private enum Direction {
        NORTH(-1, 0), EAST(0, 1), SOUTH(1, 0), WEST(0, -1);

        final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    private int[][] topoMap;
    private int score;
    private int ratings;
    private Set<String> visitedHead = new HashSet<>();

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);

        this.topoMap = Array2DUtils.readAsIntMatrix(inputLines);
        List<Pos> trailTails = Array2DUtils.getXPos(topoMap, 0);
        trailTails.forEach(x -> {
            visitedHead.clear();
            exploreTrail(x);
        });

        return isPart2() ? (long) ratings : (long) score;
    }

    private void exploreTrail(Pos pos) {
        if (topoMap[pos.x][pos.y] == 9) {
            if (!visitedHead.contains(pos.x + "-" + pos.y)) {
                score++;
                visitedHead.add(pos.x + "-" + pos.y);
            }
            ratings++;
            return;
        }

        EnumSet<Direction> directions = EnumSet.allOf(Direction.class);
        for (Direction direction : directions) {
            int newX = pos.x + direction.dx;
            int newY = pos.y + direction.dy;
            if (newX < 0 || newX >= topoMap.length || newY < 0 || newY >= topoMap[0].length
                || topoMap[newX][newY] - topoMap[pos.x][pos.y] != 1) {
                continue;
            }
            exploreTrail(new Pos(newX, newY));
        }
    }
}
