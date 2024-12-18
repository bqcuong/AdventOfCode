package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;
import net.bqc.aoc.utils.Pos;

import java.util.*;

public class Day18 extends Solution<String> {
    private enum Direction {
        NORTH(-1, 0), EAST(0, 1), SOUTH(1, 0), WEST(0, -1);

        final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    private static class PathNode {
        Pos pos;
        int cost;

        public PathNode(Pos pos, int cost) {
            this.pos = pos;
            this.cost = cost;
        }
    }

    @Override
    public String solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        List<Pos> fallingBytes = readFallingBytes(inputLines);

        int gridSize = inputLines.size() <= 25 ? 7 : 71;
        char[][] memory = new char[gridSize][gridSize];
        for (char[] row : memory) Arrays.fill(row, '.');

        if (!isPart2()) {
            placeFallingBytes(memory, fallingBytes, fallingBytes.size() <= 25 ? 12 : 1024);
            return String.valueOf(computeShortestPathDijkstra(memory));
        }
        else {
            return findFallingBytes(memory, fallingBytes);
        }
    }

    private String findFallingBytes(char[][] memory, List<Pos> fallingBytes) {
        int l = 0, r = fallingBytes.size() - 1;
        // binary search for the first falling byte that cause unreachable state
        while (l < r) {
            int mid = (l + r) / 2;
            char[][] memoryCopy = Array2DUtils.copyMatrix(memory);
            placeFallingBytes(memoryCopy, fallingBytes, mid);
            if (computeShortestPathDijkstra(memoryCopy) < Integer.MAX_VALUE) {
                l = mid + 1;
            }
            else r = mid;
        }
        Pos found = fallingBytes.get(r - 1);
        return found.y + "," + found.x;
    }

    private void placeFallingBytes(char[][] memory, List<Pos> fallingBytes, int numBytes) {
        fallingBytes.stream().limit(numBytes).forEach(p -> memory[p.x][p.y] = '#');
    }

    private int computeShortestPathDijkstra(char[][] memory) {
        int[][] costMap = new int[memory.length][memory[0].length];
        for (int[] row : costMap) Arrays.fill(row, Integer.MAX_VALUE);

        Pos startPos = new Pos(0, 0);
        Pos endPos = new Pos(memory.length - 1, memory[0].length - 1);
        PriorityQueue<PathNode> pq = new PriorityQueue<>((o1, o2) -> o2.cost - o1.cost);
        pq.add(new PathNode(startPos, 0));

        while (!pq.isEmpty()) {
            PathNode node = pq.poll();
            for (Direction d : Direction.values()) {
                int newX = node.pos.x + d.dx;
                int newY = node.pos.y + d.dy;

                if (newX < 0 || newX >= memory.length || newY < 0 || newY >= memory[0].length
                    || memory[newX][newY] == '#') continue;

                int nextCost = node.cost + 1;
                if (nextCost < costMap[newX][newY]) {
                    costMap[newX][newY] = nextCost;
                    PathNode nextNode = new PathNode(new Pos(newX, newY), nextCost);
                    pq.add(nextNode);
                }
            }
        }
        return costMap[endPos.x][endPos.y];
    }

    private List<Pos> readFallingBytes(List<String> inputLines) {
        List<Pos> fallingBytes = new ArrayList<>();
        for (String line : inputLines) {
            int x = Integer.parseInt(line.split(",")[1]);
            int y = Integer.parseInt(line.split(",")[0]);
            fallingBytes.add(new Pos(x, y));
        }
        return fallingBytes;
    }
}