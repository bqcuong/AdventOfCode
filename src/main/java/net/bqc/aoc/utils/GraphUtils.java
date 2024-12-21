package net.bqc.aoc.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class GraphUtils {
    public static enum CDirection {
        NORTH("^", -1, 0), EAST(">", 0, 1),
        SOUTH("v", 1, 0), WEST("<", 0, -1);

        public final String symbol;
        public final int dx, dy;

        CDirection(String symbol, int dx, int dy) {
            this.symbol = symbol;
            this.dx = dx;
            this.dy = dy;
        }

        public static CDirection[] nextDirection(CDirection d) {
            if (d == null) return new CDirection[]{NORTH, EAST, SOUTH, WEST};
            return switch (d) {
                case NORTH -> new CDirection[]{NORTH, EAST, WEST};
                case SOUTH -> new CDirection[]{SOUTH, EAST, WEST};
                case EAST -> new CDirection[]{NORTH, SOUTH, EAST};
                case WEST -> new CDirection[]{NORTH, SOUTH, WEST};
            };
        }
    }

    public static class PathNode {
        public Pos pos;
        public long cost;
        public CDirection direction;
        public PathNode prev;

        public PathNode(Pos pos, long cost, CDirection direction) {
            this.pos = pos;
            this.cost = cost;
            this.direction = direction;
        }
    }

    public static List<PathNode> shortestPath(char[][] map, Pos startPos, Pos endPos, char blockingTile) {
        // Implements Dijkstra's shortest path algorithm
        PathNode[][] costMap = new PathNode[map.length][map[0].length];
        costMap[startPos.x][startPos.y] = new PathNode(startPos, 0, CDirection.EAST);

        PriorityQueue<PathNode> pq = new PriorityQueue<>((o1, o2) -> (int) (o2.cost - o1.cost));
        pq.add(costMap[startPos.x][startPos.y]);
        while (!pq.isEmpty()) {
            PathNode curr = pq.poll();

            for (CDirection d : CDirection.nextDirection(curr.direction)) {
                int newX = curr.pos.x + d.dx;
                int newY = curr.pos.y + d.dy;
                long nextCost = curr.cost + 1;

                if (map[newX][newY] == blockingTile) continue;

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
