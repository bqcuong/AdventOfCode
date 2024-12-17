package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;
import net.bqc.aoc.utils.Pos;

import java.util.*;

public class Day16 extends Solution<Long> {

    private enum Direction {
        NORTH(-1, 0), EAST(0, 1), SOUTH(1, 0), WEST(0, -1);

        final int dx, dy;

        Direction(int dx, int dy) {
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

        public int id() {
            return switch (this) {
                case NORTH -> 0;
                case SOUTH -> 1;
                case EAST -> 2;
                case WEST -> 3;
            };
        }
    }

    private static class PathNode {
        Pos pos;
        long cost;
        Direction direction;
        List<PathNode> prevs = new ArrayList<>();

        public PathNode(Pos pos, long cost, Direction direction) {
            this.pos = pos;
            this.cost = cost;
            this.direction = direction;
        }
    }

    private char[][] map;

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);

        this.map = Array2DUtils.readAsMatrix(inputLines);
        Pos startPos = Array2DUtils.getXPos(map, 'S').get(0);
        Pos endPos = Array2DUtils.getXPos(map, 'E').get(0);
        return computeBestCostDijkstra(startPos, endPos);
    }

    private long computeBestCostDijkstra(Pos startPos, Pos endPos) {
        PathNode[][][] costMap = new PathNode[map.length][map[0].length][4];
        costMap[startPos.x][startPos.y][Direction.EAST.id()] = new PathNode(startPos, 0, Direction.EAST);

        PriorityQueue<PathNode> pq = new PriorityQueue<>((o1, o2) -> (int) (o2.cost - o1.cost));
        pq.add(costMap[startPos.x][startPos.y][Direction.EAST.id()]);

        while (!pq.isEmpty()) {
            PathNode curr = pq.poll();

            for (Direction d : Direction.nextDirection(curr.direction)) {
                // go straight
                int newX = curr.pos.x + d.dx;
                int newY = curr.pos.y + d.dy;
                long nextCost = curr.cost + 1;

                if (d != curr.direction) { // turn 90-degree
                    newX = curr.pos.x;
                    newY = curr.pos.y;
                    nextCost = curr.cost + 1000;
                }

                if (map[newX][newY] == '#') continue;

                if (costMap[newX][newY][d.id()] == null) {
                    costMap[newX][newY][d.id()] = new PathNode(new Pos(newX, newY), Long.MAX_VALUE, d);
                }

                if (nextCost < costMap[newX][newY][d.id()].cost) {
                    costMap[newX][newY][d.id()].cost = nextCost;
                    costMap[newX][newY][d.id()].prevs.clear();
                    costMap[newX][newY][d.id()].prevs.add(curr);

                    pq.add(costMap[newX][newY][d.id()]);
                }
                else if (nextCost == costMap[newX][newY][d.id()].cost) {
                    costMap[newX][newY][d.id()].prevs.add(curr);
                }
            }
        }

        long bestCost = Long.MAX_VALUE;
        PathNode bestEndNode = costMap[endPos.x][endPos.y][0];
        for (PathNode curr : costMap[endPos.x][endPos.y]) {
            if (curr.cost < bestCost) {
                bestCost = curr.cost;
                bestEndNode = curr;
            }
        }

        Set<Pos> goodPos = new HashSet<>();
        Queue<PathNode> goodNodes = new LinkedList<>();
        goodNodes.add(bestEndNode);
        while (!goodNodes.isEmpty()) {
            PathNode curr = goodNodes.poll();
            goodPos.add(curr.pos);
            goodNodes.addAll(curr.prevs);
        }
        return isPart2() ? goodPos.size() : bestEndNode.cost;
    }
}
