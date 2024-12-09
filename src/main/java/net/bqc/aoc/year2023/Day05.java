package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day05 extends Solution {

    static class Problem {
        List<BigInteger> seeds = new ArrayList<>();
        List<PlantMap> maps = new ArrayList<>();

        public BigInteger getMappedValueInAllMaps(BigInteger src) {
            BigInteger mappedValue = src;
            for (PlantMap map : maps) {
                mappedValue = map.getMappedValue(mappedValue);
            }
            return mappedValue;
        }
    }

    static class PlantMap {
        List<BigInteger> rawMap = new ArrayList<>();
        List<Triple<BigInteger>> processedMap = new ArrayList<>();

        public BigInteger getMappedValue(BigInteger src) {
            for (Triple<BigInteger> triple : processedMap) {
                if (src.compareTo(triple.start) >= 0 && src.compareTo(triple.end) <= 0) {
                    return src.add(triple.value);
                }
            }
            return src;
        }
    }

    static class Triple<K> {
        K start;
        K end;

        K value;

        public Triple(K start, K end, K value) {
            this.start = start;
            this.end = end;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Pair{" +
                "start=" + start +
                ", end=" + end +
                ", value=" + value +
                '}';
        }
    }

    @Override
    public long solve(PART_NUMBER part, List<String> inputLines) {
        return 0;
    }

    public BigInteger solve2(PART_NUMBER part, List<String> inputLines) {
        super.solve(part, inputLines);

        Problem p = parseProblem(inputLines);

        if (!isPart2()) {
            return p.seeds.stream().map(p::getMappedValueInAllMaps)
                .min(BigInteger::compareTo)
                .orElse(BigInteger.ZERO);
        }
        else if (isPart2()) {
            BigInteger min = null;
            int i = 0 ;
            while (i < p.seeds.size()) {
                BigInteger seed = p.seeds.get(i);
                BigInteger times = p.seeds.get(i + 1);

                for (BigInteger j = BigInteger.ZERO; j.compareTo(times) < 0; j = j.add(BigInteger.ONE)) {
                    BigInteger mapValue = p.getMappedValueInAllMaps(seed.add(j));
                    min = min == null ? mapValue : min.min(mapValue);
                }

                i += 2;
            }
            return min;
        }
        return BigInteger.ZERO;
    }

    public Problem parseProblem(List<String> inputLines) {
        Problem p = new Problem();
        p.seeds = Arrays.stream(inputLines.get(0).split(":")[1].trim().split("\s+"))
            .map(BigInteger::new).toList();

        int mapCount = 0;
        for (String line : inputLines) {
            if (line.endsWith("map:")) {
                p.maps.add(new PlantMap());
                mapCount++;
            }
            if (line.length() > 0 && Character.isDigit(line.charAt(0))) {
                List<BigInteger> nums = Arrays.stream(line.split("\s+")).map(BigInteger::new).toList();
                p.maps.get(mapCount - 1).rawMap.addAll(nums);
            }
        }

        for (PlantMap map : p.maps) {
            int i = 0;
            while (i < map.rawMap.size()) {
                BigInteger des = map.rawMap.get(i);
                BigInteger src = map.rawMap.get(i + 1);
                BigInteger len = map.rawMap.get(i + 2);

                map.processedMap.add(new Triple<>(src, src.add(len).subtract(new BigInteger("1")), des.subtract(src)));
                i += 3;
            }
            map.processedMap.sort(Comparator.comparing(x -> x.start));
        }

        return p;
    }
}
