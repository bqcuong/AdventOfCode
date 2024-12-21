package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;
import net.bqc.aoc.utils.MathUtils;
import net.bqc.aoc.utils.Pos;

import java.util.*;

public class Day20 extends Solution<Long> {

    private enum Direction {
        NORTH("^", -1, 0), EAST(">", 0, 1),
        SOUTH("v", 1, 0), WEST("<", 0, -1);

        final String symbol;
        final int dx, dy;

        Direction(String symbol, int dx, int dy) {
            this.symbol = symbol;
            this.dx = dx;
            this.dy = dy;
        }

        public static Direction[] nextDirection(Direction d) {
            if (d == null) return new Direction[]{NORTH, EAST, SOUTH, WEST};
            return switch (d) {
                case NORTH -> new Direction[]{NORTH, EAST, WEST};
                case SOUTH -> new Direction[]{SOUTH, EAST, WEST};
                case EAST -> new Direction[]{NORTH, SOUTH, EAST};
                case WEST -> new Direction[]{NORTH, SOUTH, WEST};
            };
        }
    }

    private static class PathNode {
        Pos pos;
        long cost;
        Direction direction;
        PathNode prev;

        public PathNode(Pos pos, long cost, Direction direction) {
            this.pos = pos;
            this.cost = cost;
            this.direction = direction;
        }
    }

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        char[][] map = Array2DUtils.readAsMatrix(inputLines);
        return countCheats(map, isPart2() ? 20 : 2);
    }

    private long countCheats(char[][] map, int maxDist) {
        Pos startPos = Array2DUtils.getXPos(map, 'S').get(0);
        Pos endPos = Array2DUtils.getXPos(map, 'E').get(0);

        List<PathNode> initialPath = shortestPath(map, startPos, endPos);
        long initialBestCost = initialPath.get(initialPath.size() - 1).cost;
        long cheatCount = 0;

        for (int i = 0; i < initialPath.size() - 1; i++) {
            PathNode pi = initialPath.get(i);
            for (int j = i + 1; j < initialPath.size(); j++) {
                PathNode pj = initialPath.get(j);
                int dist = MathUtils.manhattanDistance(pi.pos.x, pi.pos.y, pj.pos.x, pj.pos.y);
                if (dist <= maxDist && (j - i - dist) >= 100) cheatCount++;
            }
        }

        return cheatCount;
    }

    private List<PathNode> shortestPath(char[][] map, Pos startPos, Pos endPos) {
        PathNode[][] costMap = new PathNode[map.length][map[0].length];
        costMap[startPos.x][startPos.y] = new PathNode(startPos, 0, Direction.EAST);

        PriorityQueue<PathNode> pq = new PriorityQueue<>((o1, o2) -> (int) (o2.cost - o1.cost));
        pq.add(costMap[startPos.x][startPos.y]);
        while (!pq.isEmpty()) {
            PathNode curr = pq.poll();

            for (Direction d : Direction.nextDirection(curr.direction)) {
                int newX = curr.pos.x + d.dx;
                int newY = curr.pos.y + d.dy;
                long nextCost = curr.cost + 1;

                if (map[newX][newY] == '#') continue;

                if (costMap[newX][newY] == null) {
                    costMap[newX][newY] = new PathNode(new Pos(newX, newY), Long.MAX_VALUE, d);
                }

                if (nextCost < costMap[newX][newY].cost) {
                    costMap[newX][newY].cost = nextCost;
                    costMap[newX][newY].prev = curr;
                    pq.add(costMap[newX][newY]);
                }
            }
        }

        List<PathNode> path = new ArrayList<>();
        PathNode curr = costMap[endPos.x][endPos.y];
        while (curr != null) {
            path.add(curr);
            curr = curr.prev;
        }
        Collections.reverse(path);
        return path;
    }
}
