package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.*;

public class Day17 extends Solution {

    enum Direction {
        NORTH(-1, 0), EAST(0, 1), SOUTH(1, 0), WEST(0, -1);

        final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        List<Direction> nextTurn() {
            return switch (this) {
                case NORTH -> List.of(WEST, NORTH, EAST);
                case EAST -> List.of(NORTH, EAST, SOUTH);
                case SOUTH -> List.of(EAST, SOUTH, WEST);
                case WEST -> List.of(SOUTH, WEST, NORTH);
            };
        }
    }

    static class Node implements Comparator<Node> {

        int row;

        int col;

        int weight;

        int cost = Integer.MAX_VALUE;

        Direction visitedDirection;

        int straightMoves = 1;

        public Node() {
        }

        public Node(int row, int col, int cost, Direction visitedDirection, int straightMoves) {
            this.cost = cost;
            this.visitedDirection = visitedDirection;
            this.straightMoves = straightMoves;

            this.row = row;
            this.col = col;
        }

        @Override
        public int compare(Node o1, Node o2) {
            return o1.cost - o2.cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return row == node.row && col == node.col && weight == node.weight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col, weight);
        }
    }

    static class Graph {
        int[][] nodes;

        public int minCost(int minStraightMoves, int maxStraightMoves) {
            PriorityQueue<Node> queue = new PriorityQueue<>(
                this.nodes.length * this.nodes[0].length, new Node());
            Map<String, Integer> costHistory = new HashMap<>();

            Node newSource = new Node(0, 0, 0, Direction.EAST, 0);
            queue.add(newSource);

            while (!queue.isEmpty()) {
                Node evaluatedNode = queue.remove();
                int currentCost = evaluatedNode.cost;
                Direction currentDirection = evaluatedNode.visitedDirection;
                int currentStraightMoves = evaluatedNode.straightMoves;

                String key = evaluatedNode.row + "," + evaluatedNode.col + "_" + currentDirection + "_" + currentStraightMoves;

                if (costHistory.containsKey(key)) {
                    if (costHistory.get(key) <= currentCost) {
                        continue;
                    }
                }

                costHistory.put(key, currentCost);

                for (Direction nextDirection : currentDirection.nextTurn()) {
                    boolean sameDirection = nextDirection == currentDirection;

                    if (minStraightMoves == 0 && currentStraightMoves >= maxStraightMoves && sameDirection) {
                        continue;
                    }

                    if (minStraightMoves != 0) {
                        if (currentStraightMoves < minStraightMoves && !sameDirection) {
                            continue;
                        }

                        if (currentStraightMoves >= maxStraightMoves && sameDirection) {
                            continue;
                        }
                    }

                    int nextX = evaluatedNode.row + nextDirection.dx;
                    int nextY = evaluatedNode.col + nextDirection.dy;

                    if (nextX < 0 || nextX >= this.nodes.length || nextY < 0 || nextY >= this.nodes[0].length) {
                        continue;
                    }

                    int nextWeight = this.nodes[nextX][nextY]; // node from the input data

                    int nextCost = currentCost + nextWeight;
                    int nextStraightMoves = sameDirection ? currentStraightMoves + 1 : 1;
                    queue.add(new Node(nextX, nextY, nextCost, nextDirection, nextStraightMoves));
                }
            }

            int len = this.nodes.length;
            int minCost = Integer.MAX_VALUE;
            for (String key : costHistory.keySet()) {
                if (key.startsWith((len - 1) + "," + (len - 1))) {
                    minCost = Math.min(minCost, costHistory.get(key));
                }
            }
            return minCost;
        }
    }

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        super.solve(part, inputLines);

        Graph graph = constructGraph(inputLines);
        return !isPart2() ? graph.minCost(0, 3) : graph.minCost(4, 10);
    }

    private Graph constructGraph(List<String> inputLines) {
        Graph graph = new Graph();

        graph.nodes = new int[inputLines.size()][inputLines.get(0).length()];
        for (int i = 0; i < inputLines.size(); i++) {
            String line = inputLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                int nodeWeight = line.charAt(j) - '0';
                graph.nodes[i][j] = nodeWeight;
            }
        }

        return graph;
    }
}
