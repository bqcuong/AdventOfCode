package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.*;

public class Day07 extends Solution {

    private enum HandType {
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

    private class Card implements Comparable<Card> {
        char label;
        int rank;

        Card(char label) {
            this.label = label;
            switch (this.label) {
                case 'A' -> this.rank = 14;
                case 'K' -> this.rank = 13;
                case 'Q' -> this.rank = 12;
                case 'J' -> {
                    if (!Day07.this.isPart2()) {
                        this.rank = 11;
                    }
                    else {
                        this.rank = 1;
                    }
                }
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

        @Override
        public boolean equals(Object obj) {
            return this.label == ((Card)obj).label;
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }
    }

    private class CardHand implements Comparable<CardHand> {
        HandType type;
        long bid;
        List<Card> cards;

        CardHand(String cardList, long bid) {
            this.cards = Arrays.stream(cardList.split("")).map(x -> new Card(x.charAt(0))).toList();
            this.bid = bid;

            this.type = parseHandType(this.cards);

            if (Day07.this.isPart2()) {
                Card j = new Card('J');
                Set<Card> uniqueCards = new HashSet<>(this.cards);

                if (!this.cards.contains(j)) {
                    return;
                }

                for (Card card : uniqueCards) {
                    List<Card> newCardList = new ArrayList<>();

                    for (Card c : this.cards) {
                        if (j.equals(c)) {
                            newCardList.add(new Card(card.label));
                        }
                        else {
                            newCardList.add(new Card(c.label));
                        }
                    }

                    HandType newType = parseHandType(newCardList);
                    if (newType.value > this.type.value) {
                        this.type = newType;
                    }
                }
            }
        }

        private HandType parseHandType(List<Card> cardList) {
            List<Card> sortedCards = new ArrayList<>(cardList);
            Collections.sort(sortedCards);
            long[] r = sortedCards.stream().mapToLong(x -> x.rank).toArray();

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

        private boolean checkEquals(long...arr) {
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
    public long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);

        List<CardHand> cardHands = new ArrayList<>(inputLines.stream()
            .map(x -> new CardHand(x.split("\s+")[0], Long.parseLong(x.split("\s+")[1])))
            .toList());

        Collections.sort(cardHands);
        long sum = 0;
        for (int i = 0; i < cardHands.size(); i++) {
            sum += (i + 1) * cardHands.get(i).bid;
        }

        return sum;
    }
}
