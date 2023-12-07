package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day07 extends Solution {

    enum HandType {
        HIGH_CARD(0),
        ONE_PAIR(1),
        TWO_PAIRS(2),
        THREE_OF_A_KIND(3),
        FULL_HOUSE(4),
        FOUR_OF_A_KIND(5),
        FIVE_OF_A_KIND(6);

        public final int value;

        HandType(int value) {
            this.value = value;
        }
    }

    static class Card implements Comparable<Card> {
        char label;
        int rank;

        Card(char label) {
            this.label = label;
            switch (this.label) {
                case 'A' -> this.rank = 14;
                case 'K' -> this.rank = 13;
                case 'Q' -> this.rank = 12;
                case 'J' -> this.rank = 11;
                case 'T' -> this.rank = 10;
                default -> this.rank = this.label - '0';
            }
        }

        @Override
        public String toString() {
            return this.label + "";
        }

        @Override
        public int compareTo(Card o) {
            return this.rank - o.rank;
        }
    }

    static class CardHand implements Comparable<CardHand> {
        HandType type;
        int bid;
        List<Card> cards;

        CardHand(String cardList, int bid) {
            this.cards = Arrays.stream(cardList.split("")).map(x -> new Card(x.charAt(0))).toList();
            this.bid = bid;
            this.type = parseHandType();
        }

        private HandType parseHandType() {
            List<Card> sortedCards = new ArrayList<>(this.cards);
            Collections.sort(sortedCards);
            int[] r = sortedCards.stream().mapToInt(x -> x.rank).toArray();

            if (checkEquals(r[0], r[1], r[2], r[3], r[4])) {
                return HandType.FIVE_OF_A_KIND;
            }

            if (checkEquals(r[0], r[1], r[2], r[3])
            || checkEquals(r[1], r[2], r[3], r[4])) {
                return HandType.FOUR_OF_A_KIND;
            }

            if (checkEquals(r[0], r[1], r[2]) && checkEquals(r[3], r[4])
            || checkEquals(r[2], r[3], r[4]) && checkEquals(r[0], r[1])) {
                return HandType.FULL_HOUSE;
            }

            if (checkEquals(r[0], r[1], r[2])
                || checkEquals(r[1], r[2], r[3])
                || checkEquals(r[2], r[3], r[4])) {
                return HandType.THREE_OF_A_KIND;
            }

            if (r[0] == r[1] && r[2] == r[3]
                || r[0] == r[1] && r[3] == r[4]
                || r[1] == r[2] && r[3] == r[4]) {
                return HandType.TWO_PAIRS;
            }

            if (r[0] == r[1] || r[1] == r[2] || r[2] == r[3] || r[3] == r[4]) {
                return HandType.ONE_PAIR;
            }

            return HandType.HIGH_CARD;
        }

        private boolean checkEquals(int...arr) {
            if (arr.length <= 1) {
                return true;
            }
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] != arr[i+1]) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int compareTo(CardHand o) {
            int rankDiff = this.type.value - o.type.value;

            if (rankDiff != 0) {
                return rankDiff;
            }

            for (int i = 0; i < this.cards.size(); i++) {
                rankDiff = this.cards.get(i).compareTo(o.cards.get(i));
                if (rankDiff != 0) {
                    return rankDiff;
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            return this.cards + " " + this.type;
        }
    }

    @Override
    public int solve(PART_NUMBER part, List<String> inputLines) {
        List<CardHand> cardHands = new ArrayList<>(inputLines.stream()
            .map(x -> new CardHand(x.split("\s+")[0], Integer.parseInt(x.split("\s+")[1])))
            .toList());

        Collections.sort(cardHands);
        int sum = 0;
        for (int i = 0; i < cardHands.size(); i++) {
            sum += (i + 1) * cardHands.get(i).bid;
        }

        return sum;
    }
}
