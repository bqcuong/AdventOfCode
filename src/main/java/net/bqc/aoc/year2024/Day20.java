package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;
import net.bqc.aoc.utils.MathUtils;
import net.bqc.aoc.utils.Pos;

import static net.bqc.aoc.utils.GraphUtils.*;

import java.util.*;

public class Day20 extends Solution<Long> {

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        char[][] map = Array2DUtils.readAsMatrix(inputLines);
        return countCheats(map, isPart2() ? 20 : 2);
    }

    private long countCheats(char[][] map, int maxDist) {
        Pos startPos = Array2DUtils.getXPos(map, 'S').get(0);
        Pos endPos = Array2DUtils.getXPos(map, 'E').get(0);

        List<PathNode> initialPath = shortestPaths(map, startPos, endPos, '#').get(0);
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
}
