package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;
import net.bqc.aoc.utils.Array2DUtils;
import net.bqc.aoc.utils.Pos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day15 extends Solution<Long> {

    private enum Direction {
        NORTH("^", -1, 0), EAST(">", 0, 1),
        SOUTH("v", 1, 0), WEST("<", 0, -1);

        public final String symbol;
        public final int dx;
        public final int dy;

        Direction(String symbol, int dx, int dy) {
            this.symbol = symbol;
            this.dx = dx;
            this.dy = dy;
        }

        public static Direction findBySymbol(String symbol) {
            return Arrays.stream(values()).filter(x -> x.symbol.equals(symbol)).findFirst().orElse(null);
        }
    }

    private char[][] map;
    private Pos startPos;
    private List<Direction> moves = new ArrayList<>();

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);
        readMapAndMoves(inputLines);
        moves.forEach(m -> move(startPos.x, startPos.y, m));
        return Array2DUtils.getXPos(map, isPart2() ? '[' : 'O').stream().mapToLong(p -> 100L * p.x + p.y).sum();
    }

    private void move(int x, int y, Direction direction) {
        List<Pos> movedBlocks = new ArrayList<>();
        if (canMove(x, y, direction, movedBlocks)) {
            movedBlocks.forEach(p -> {
                int newX = p.x + direction.dx;
                int newY = p.y + direction.dy;

                if (map[p.x][p.y] == '@') {
                    startPos = new Pos(newX, newY);
                }
                map[newX][newY] = map[p.x][p.y];
                map[p.x][p.y] = '.';
            });
        }
    }

    private boolean canMove(int x, int y, Direction direction, List<Pos> movedBlocks) {
        if (movedBlocks.contains(new Pos(x, y))) return true;

        int newX = x + direction.dx;
        int newY = y + direction.dy;
        if (newX < 0 || newX >= map.length || newY < 0 || newY >= map[0].length || map[newX][newY] == '#') return false;
        if (map[newX][newY] == 'O' && !canMove(newX, newY, direction, movedBlocks)) return false;

        if (isPart2()) { // handle the bigger boxes in scaled map
            if (direction == Direction.WEST || direction == Direction.EAST) {
                if ((map[newX][newY] == '[' || map[newX][newY] == ']') && !canMove(newX, newY, direction, movedBlocks))
                    return false;
            }
            else {
                if (map[newX][newY] == '[' && (!canMove(newX, newY, direction, movedBlocks)
                    || !canMove(newX, newY + 1, direction, movedBlocks))) return false;
                if (map[newX][newY] == ']' && (!canMove(newX, newY, direction, movedBlocks)
                    || !canMove(newX , newY - 1, direction, movedBlocks))) return false;
            }
        }

        movedBlocks.add(new Pos(x, y));
        return true;
    }

    private void readMapAndMoves(List<String> inputLines) {
        List<String> mapLines = new ArrayList<>(inputLines.stream().filter(l -> l.startsWith("#")).toList());
        if (isPart2()) { // scale the map
            for (int i = 0; i < mapLines.size(); i++) {
                StringBuilder scaledLine = new StringBuilder();
                for (char c : mapLines.get(i).toCharArray()) {
                    if (c == '.') scaledLine.append("..");
                    else if (c == '#') scaledLine.append("##");
                    else if (c == 'O') scaledLine.append("[]");
                    else if (c == '@') scaledLine.append("@.");
                }
                mapLines.set(i, scaledLine.toString());
            }
        }
        this.map = Array2DUtils.readAsMatrix(mapLines);
        this.startPos = Array2DUtils.getXPos(this.map, '@').get(0);
        char[] moves = inputLines.stream().filter(l -> !l.isBlank() && "<>^v".contains(l.charAt(0) + ""))
            .reduce((a, b) -> a + b).get().toCharArray();
        for (char move : moves) this.moves.add(Direction.findBySymbol(String.valueOf(move)));
    }
}