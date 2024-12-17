package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;
import net.bqc.aoc.utils.Pos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 extends Solution {

    class Robot {
        Pos p;
        Pos v;

        public Robot(Pos p, Pos v) {
            this.p = p;
            this.v = v;
        }
    }

    private final List<Robot> robots = new ArrayList<>();
    private final int m = 103;
    private final int n = 101;

    @Override
    public long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        readRobots(inputLines);
        return !isPart2() ? countRobots() : findEasterEgg();
    }

    private long findEasterEgg() {
        long elapsedTime = 0;
        while (elapsedTime++ < 10000) {
            moveRobots(1);
            if (isEasterEgg()) {
//                printMap();
                break;
            }
        }
        return elapsedTime;
    }

    private boolean isEasterEgg() {
        boolean[][] map = new boolean[m][n];
        // check if only one robot is on a tile
        for (Robot robot : robots) {
            if (!map[robot.p.x][robot.p.y]) map[robot.p.x][robot.p.y] = true;
            else return false;
        }
        return true;
    }

    private long countRobots() {
        moveRobots(100);
        long[] countByQuadrants = new long[4];
        for (Robot robot : robots) {
            int x = robot.p.x, y = robot.p.y;
            if (x < m / 2 && y < n / 2) countByQuadrants[0]++;
            if (x < m / 2 && y > n / 2) countByQuadrants[1]++;
            if (x > m / 2 && y > n / 2) countByQuadrants[2]++;
            if (x > m / 2 && y < n / 2) countByQuadrants[3]++;
        }
        return Arrays.stream(countByQuadrants).reduce(1L, (a, b) -> a * b);
    }

    private void moveRobots(int seconds) {
        robots.forEach(robot -> {
            int newX = robot.p.x + seconds * robot.v.x;
            int newY = robot.p.y + seconds * robot.v.y;
            if (newX >= m) newX = newX % m;
            if (newY >= n) newY = newY % n;
            if (newX < 0) newX = (newX + seconds * m) % m;
            if (newY < 0) newY = (newY + seconds * n) % n;

            robot.p.x = newX;
            robot.p.y = newY;
        });
    }

    private void printMap() {
        int[][] map = new int[m][n];
        for (Robot robot : robots) {
            map[robot.p.x][robot.p.y]++;
        }
        Array2DUtils.printMatrix(map);
    }

    private void readRobots(List<String> inputLines) {
        for (String line : inputLines) {
            String[] parts = line.split(" ");
            String[] initialPos = parts[0].substring(2).split(",");
            String[] velocity = parts[1].substring(2).split(",");
            robots.add(new Robot(
                new Pos(Integer.parseInt(initialPos[1]), Integer.parseInt(initialPos[0])),
                new Pos(Integer.parseInt(velocity[1]), Integer.parseInt(velocity[0]))));
        }
    }
}
