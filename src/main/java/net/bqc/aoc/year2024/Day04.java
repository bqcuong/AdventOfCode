package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;
import net.bqc.aoc.utils.Pos;

import java.util.List;

public class Day04 extends Solution<Long> {

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);

        char[][] matrix = Array2DUtils.readAsMatrix(inputLines);

        if (!isPart2()) {
            List<Pos> xPos = Array2DUtils.getXPos(matrix, 'X');
            return (long) xPos.stream().map(p -> countXMAS(matrix, p)).reduce(Integer::sum).get();
        }

        List<Pos> aPos = Array2DUtils.getXPos(matrix, 'A');
        return (long) aPos.stream().map(p -> countX_MAS(matrix, p)).reduce(Integer::sum).get();
    }

    public int countX_MAS(char[][] matrix, Pos aPos) {
        int size = matrix.length;
        if (aPos.x + 1 >= size || aPos.x - 1 < 0 || aPos.y + 1 >= size || aPos.y - 1 < 0) {
            return 0;
        }

        return matrix[aPos.x-1][aPos.y-1] == 'M' && matrix[aPos.x+1][aPos.y+1] == 'S' && matrix[aPos.x-1][aPos.y+1] == 'M' && matrix[aPos.x+1][aPos.y-1] == 'S'
            || matrix[aPos.x-1][aPos.y-1] == 'M' && matrix[aPos.x+1][aPos.y+1] == 'S' && matrix[aPos.x-1][aPos.y+1] == 'S' && matrix[aPos.x+1][aPos.y-1] == 'M'
            || matrix[aPos.x-1][aPos.y-1] == 'S' && matrix[aPos.x+1][aPos.y+1] == 'M' && matrix[aPos.x-1][aPos.y+1] == 'M' && matrix[aPos.x+1][aPos.y-1] == 'S'
            || matrix[aPos.x-1][aPos.y-1] == 'S' && matrix[aPos.x+1][aPos.y+1] == 'M' && matrix[aPos.x-1][aPos.y+1] == 'S' && matrix[aPos.x+1][aPos.y-1] == 'M'
            ? 1 : 0;
    }

    public int countXMAS(char[][] matrix, Pos xPos) {
        int size = matrix.length;
        int count = 0;

        // east
        if (xPos.x + 3 < size
            && matrix[xPos.x + 1][xPos.y] == 'M'
            && matrix[xPos.x + 2][xPos.y] == 'A'
            && matrix[xPos.x + 3][xPos.y] == 'S') count++;

        // west
        if (xPos.x - 3 >= 0
            && matrix[xPos.x - 1][xPos.y] == 'M'
            && matrix[xPos.x - 2][xPos.y] == 'A'
            && matrix[xPos.x - 3][xPos.y] == 'S') count++;

        // south
        if (xPos.y + 3 < size
            && matrix[xPos.x][xPos.y + 1] == 'M'
            && matrix[xPos.x][xPos.y + 2] == 'A'
            && matrix[xPos.x][xPos.y + 3] == 'S') count++;

        // north
        if (xPos.y - 3 >= 0
            && matrix[xPos.x][xPos.y - 1] == 'M'
            && matrix[xPos.x][xPos.y - 2] == 'A'
            && matrix[xPos.x][xPos.y - 3] == 'S') count++;

        // north-east
        if (xPos.x + 3 < size && xPos.y - 3 >= 0
            && matrix[xPos.x + 1][xPos.y - 1] == 'M'
            && matrix[xPos.x + 2][xPos.y - 2] == 'A'
            && matrix[xPos.x + 3][xPos.y - 3] == 'S') count++;

        // south-east
        if (xPos.x + 3 < size && xPos.y + 3 < size
            && matrix[xPos.x + 1][xPos.y + 1] == 'M'
            && matrix[xPos.x + 2][xPos.y + 2] == 'A'
            && matrix[xPos.x + 3][xPos.y + 3] == 'S') count++;

        // north-west
        if (xPos.x - 3 >= 0 && xPos.y - 3 >= 0
            && matrix[xPos.x - 1][xPos.y - 1] == 'M'
            && matrix[xPos.x - 2][xPos.y - 2] == 'A'
            && matrix[xPos.x - 3][xPos.y - 3] == 'S') count++;

        // south-west
        if (xPos.x - 3 >= 0 && xPos.y + 3 < size
            && matrix[xPos.x - 1][xPos.y + 1] == 'M'
            && matrix[xPos.x - 2][xPos.y + 2] == 'A'
            && matrix[xPos.x - 3][xPos.y + 3] == 'S') count++;

        return count;
    }
}
