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

        public CDirection[] nextDirection() {
            return new CDirection[]{ EAST, WEST, SOUTH, NORTH };
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

        @Override
        public String toString() {
            return String.format("%s (%d, %d)", direction.symbol, pos.x, pos.y);
        }
    }

    public static List<List<PathNode>> shortestPaths(char[][] map, char startChar, char endChar, char blockingTile) {
        Pos startPos = Array2DUtils.getXPos(map, startChar).get(0);
        Pos endPos = Array2DUtils.getXPos(map, endChar).get(0);
        return shortestPaths(map, startPos, endPos, blockingTile);
    }

    public static List<List<PathNode>> shortestPaths(char[][] map, Pos startPos, Pos endPos, char blockingTile) {
        // Implements Dijkstra's algorithm to find all shortest paths
        PathNode[][] costMap = new PathNode[map.length][map[0].length];
        costMap[startPos.x][startPos.y] = new PathNode(startPos, 0, CDirection.EAST);

        PriorityQueue<PathNode> pq = new PriorityQueue<>((o1, o2) -> (int) (o2.cost - o1.cost));
        pq.add(costMap[startPos.x][startPos.y]);

        List<List<PathNode>> paths = new ArrayList<>();
        long bestCost = Long.MAX_VALUE;

        while (!pq.isEmpty()) {
            PathNode curr = pq.poll();

            if (curr.pos.x == endPos.x && curr.pos.y == endPos.y) {
                if (curr.cost < bestCost) {
                    bestCost = curr.cost;
                    paths.clear();
                }
                if (curr.cost == bestCost) {
                    List<PathNode> path = new ArrayList<>();
                    PathNode c = costMap[endPos.x][endPos.y];
                    while (c != null) {
                        PathNode clone = new PathNode(c.pos, c.cost, c.direction);
                        path.add(clone);
                        c = c.prev;
                    }
                    Collections.reverse(path);
                    paths.add(path);
                }
                continue;
            }

            for (CDirection d : curr.direction.nextDirection()) {
                int newX = curr.pos.x + d.dx;
                int newY = curr.pos.y + d.dy;
                long nextCost = curr.cost + 1;

                if (newX < 0 || newX >= map.length || newY < 0 || newY >= map[0].length) continue;
                if (map[newX][newY] == blockingTile) continue;

                if (costMap[newX][newY] == null) {
                    costMap[newX][newY] = new PathNode(new Pos(newX, newY), Long.MAX_VALUE, d);
                }

                if (nextCost <= costMap[newX][newY].cost) {
                    costMap[newX][newY].cost = nextCost;
                    costMap[newX][newY].prev = curr;
                    costMap[newX][newY].direction = d;
                    pq.add(costMap[newX][newY]);
                }
            }
        }

        return paths;
    }
}
