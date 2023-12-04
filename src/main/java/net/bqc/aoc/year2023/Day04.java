package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.Arrays;
import java.util.List;

public class Day04 extends Solution {

    public static class ScratchCard {
        int gameId;
        int[] slots = new int[100];
        int winningPoints = 0;
        int instances = 1;
        int count = 0;

        @Override
        public String toString() {
            return "ScratchCard{" +
                "gameId=" + gameId +
                ", count=" + count +
                ", winningPoints=" + winningPoints +
                ", instances=" + instances +
                '}';
        }
    }

    @Override
    public int solve(PART_NUMBER part, List<String> inputLines) {
        this.pup = part;

        if (this.pup == PART_NUMBER.ONE) {
            return inputLines.stream().mapToInt(x -> parseCardPoint(x).winningPoints).sum();
        }
        else {
            List<ScratchCard> cards = inputLines.stream().map(this::parseCardPoint).toList();
            for (int i = 0; i < cards.size(); i++) {
                int matchedCount = cards.get(i).count;
                if (matchedCount >= 1) {
                    for (int j = 1; j <= matchedCount && i + j < cards.size(); j++) {
                        cards.get(i + j).instances += cards.get(i).instances;
                    }
                }
            }

            return cards.stream().mapToInt(x -> x.instances).sum();
        }
    }

    public ScratchCard parseCardPoint(String cardInfo) {
        ScratchCard card = new ScratchCard();

        String[] parts = cardInfo.split(":");
        card.gameId = Integer.parseInt(parts[0].split("\s+")[1]);

        List<Integer> winningNumbers = Arrays.stream(parts[1].split("\\|")[0].trim().split("\s+"))
            .map(Integer::parseInt).toList();

        List<Integer> guessingNumbers = Arrays.stream(parts[1].split("\\|")[1].trim().split("\s+"))
            .map(Integer::parseInt).toList();

        winningNumbers.forEach(x -> card.slots[x] = 1);

        int count = (int) guessingNumbers.stream().filter(x -> card.slots[x] > 0).count();

        card.count = count;
        card.winningPoints = count <= 1 ? count : (int)Math.pow(2, count - 1);
        return card;
    }
}
