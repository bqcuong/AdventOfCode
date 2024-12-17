package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;
import net.bqc.aoc.utils.Pos;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Day12 extends Solution<Long> {

    private enum Direction {
        NORTH(-1, 0), EAST(0, 1), SOUTH(1, 0), WEST(0, -1);

        final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    private char[][] plants;
    private int[][] fences;

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);

        this.plants = Array2DUtils.readAsMatrix(inputLines);
        this.fences = computeIndividualPlantFence();

        long price = 0;
        for (int i = 0; i < plants.length; i++) {
            for (int j = 0; j < fences[i].length; j++) {
                if (fences[i][j] != 0) {
                    price += bfsAndComputePlantRegionPrice(new Pos(i, j));
                }
            }
        }
        return price;
    }

    private long bfsAndComputePlantRegionPrice(Pos startPos) {
        List<Pos> visited = new ArrayList<>();
        Queue<Pos> queue = new LinkedList<>();
        queue.add(startPos);
        visited.add(startPos);

        while (!queue.isEmpty()) {
            Pos pos = queue.remove();
            for (Direction d : EnumSet.allOf(Direction.class)) {
                int newX = pos.x + d.dx;
                int newY = pos.y + d.dy;
                Pos newPos = new Pos(newX, newY);

                if (newX < 0 || newX >= plants.length || newY < 0 || newY >= plants[0].length) continue;
                if (plants[newX][newY] != plants[pos.x][pos.y] || visited.contains(newPos)) continue;

                queue.add(newPos);
                visited.add(newPos);
            }
        }
        return (isPart2() ? getSides(visited) : getPerimeter(visited)) * visited.size();
    }

    private long getSides(List<Pos> visited) {
        // the number of sizes is the number of corners of a region
        visited.sort((o1, o2) -> o1.x * 10000 + o1.y - o2.x * 10000 - o2.y); // assume the input has maximum of 10000 columns

        long corners = 0;
        int m = plants.length;
        int n = plants[0].length;
        for (Pos pos : visited) {
            int x = pos.x, y = pos.y;

            // convex corners
            if (fences[x][y] == 4) corners += 4; // single-slot plant 4 corners
            else if (fences[x][y] == 3) corners += 2; // 2 corners
            else if (fences[x][y] == 2) {
                if (topFence(x, y) && rightFence(x, y) // NE
                || bottomFence(x, y) && rightFence(x, y) // SE
                || bottomFence(x, y) && leftFence(x, y) // SW
                || topFence(x, y) && leftFence(x, y)) { // NW
                    corners++;
                }
            }

            // concave corners
            if (x - 1 >= 0 && y - 1 >= 0 && plants[x-1][y] == plants[x][y] && plants[x][y-1] == plants[x][y] && plants[x-1][y-1] != plants[x][y]) corners++; // NW
            if (x - 1 >= 0 && y + 1 < n && plants[x-1][y] == plants[x][y] && plants[x][y+1] == plants[x][y] && plants[x-1][y+1] != plants[x][y]) corners++; // NE
            if (x + 1 < m && y + 1 < n && plants[x+1][y] == plants[x][y] && plants[x][y+1] == plants[x][y] && plants[x+1][y+1] != plants[x][y]) corners++; // SE
            if (x + 1 < m && y - 1 >= 0 && plants[x+1][y] == plants[x][y] && plants[x][y-1] == plants[x][y] && plants[x+1][y-1] != plants[x][y]) corners++; // SW
        }
        visited.forEach(pos -> fences[pos.x][pos.y] = 0);
        return corners;
    }

    private long getPerimeter(List<Pos> visited) {
        AtomicLong perimeter = new AtomicLong();
        visited.forEach(pos -> {
            perimeter.addAndGet(fences[pos.x][pos.y]);
            fences[pos.x][pos.y] = 0;
        });
        return perimeter.get();
    }

    private int[][] computeIndividualPlantFence() {
        int[][] fences = new int[plants.length][plants[0].length];
        for (int i = 0; i < plants.length; i++) {
            for (int j = 0; j < plants[0].length; j++) {
                fences[i][j] += topFence(i, j) ? 1 : 0;
                fences[i][j] += bottomFence(i, j) ? 1 : 0;
                fences[i][j] += leftFence(i, j) ? 1 : 0;
                fences[i][j] += rightFence(i, j) ? 1 : 0;
            }
        }
        return fences;
    }

    private boolean topFence(int i, int j) {
        return i == 0 || plants[i - 1][j] != plants[i][j];
    }

    private boolean bottomFence(int i, int j) {
        return i == plants.length - 1 || plants[i + 1][j] != plants[i][j];
    }

    private boolean leftFence(int i, int j) {
        return j == 0 || plants[i][j - 1] != plants[i][j];
    }

    private boolean rightFence(int i, int j) {
        return j == plants[0].length - 1 || plants[i][j + 1] != plants[i][j];
    }
}