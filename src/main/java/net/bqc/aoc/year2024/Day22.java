package net.bqc.aoc.year2024;

import net.bqc.aoc.Solution;

import java.util.*;

public class Day22 extends Solution<Long> {

    @Override
    public Long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);

        if (!isPart2()) {
            return inputLines.stream().mapToLong(Long::parseLong)
                .map(s -> simulate(s, 2000)).sum();
        }
        return mostBananas(inputLines.stream().mapToLong(Long::parseLong).toArray(), 2000);
    }

    public long mostBananas(long[] secrets, int times) {
        Map<Long, Long> seq2PriceAllBuyers = new HashMap<>();
        for (long secret : secrets) {
            Map<Long, Long> seq2Price = new HashMap<>();
            long a = 0, b = 0, c = 0, d = 0;
            for (int i = 0; i < times; i++) {
                long price = secret % 10;
                secret = cipher(secret);
                a = b; b = c; c = d; d = secret % 10 - price;
                long key = hash(a, b, c, d);
                if (i >= 3 && !seq2Price.containsKey(key)) seq2Price.put(key, secret % 10);
            }
            for (long key : seq2Price.keySet()) {
                seq2PriceAllBuyers.put(key,
                    seq2PriceAllBuyers.getOrDefault(key, 0L) + seq2Price.get(key));
            }
        }
        return seq2PriceAllBuyers.values().stream().max(Long::compare).get();
    }

    public long hash(long a, long b, long c, long d) {
        long hashCode = 1;
        hashCode = hashCode * 31 + a;
        hashCode = hashCode * 31 + b;
        hashCode = hashCode * 31 + c;
        hashCode = hashCode * 31 + d;
        return hashCode;
    }

    public long simulate(long secret, int times) {
        while (times-- > 0) secret = cipher(secret);
        return secret;
    }

    public long cipher(long secret) {
        // Bitwise operations for better performance
        secret = (secret ^ (secret << 6)) & 0xFFFFFF;
        secret = (secret ^ (secret >> 5)) & 0xFFFFFF;
        secret = (secret ^ (secret << 11)) & 0xFFFFFF;
        return secret;
    }
}