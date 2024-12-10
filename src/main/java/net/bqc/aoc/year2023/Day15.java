package net.bqc.aoc.year2023;

import net.bqc.aoc.Solution;

import java.util.*;

public class Day15 extends Solution {

    class Lens {
        String label;
        int focalLength;

        public Lens(String label, int focalLength) {
            this.label = label;
            this.focalLength = focalLength;
        }
    }

    @Override
    public long solve(Part part, List<String> inputLines) {
        super.solve(part, inputLines);

        String[] texts = inputLines.get(0).split(",");

        if (!isPart2()) {
            return Arrays.stream(texts).mapToLong(this::hash).sum();
        }
        else {
            Map<Integer, List<Lens>> map = buildMapOfLens(texts);
            return computeFocusingPower(map);
        }
    }

    private long computeFocusingPower(Map<Integer, List<Lens>> map) {
        long sum = 0;
        for (int key : map.keySet()) {
            List<Lens> box = map.get(key);
            for (int i = 0; i < box.size(); i++) {
                Lens lens = box.get(i);
                sum += (long) (key + 1) * (i + 1) * lens.focalLength;
            }
        }
        return sum;
    }

    private Map<Integer, List<Lens>> buildMapOfLens(String[] texts) {
        Map<Integer, List<Lens>> map = new HashMap<>();

        for (String text : texts) {
            if (text.contains("=")) { // insert new node
                String label = text.split("=")[0];
                int focalLength = Integer.parseInt(text.split("=")[1]);
                addLens(map, new Lens(label, focalLength));
            }
            else { // remove node
                String label = text.substring(0, text.length() - 1);
                removeLens(map, label);
            }
        }

        return map;
    }

    private void removeLens(Map<Integer, List<Lens>> map, String label) {
        int index = (int)hash(label);

        if (map.containsKey(index)) {
            List<Lens> box = map.get(index);
            for (int i = 0; i < box.size(); i++) {
                if (box.get(i).label.equals(label)) {
                    box.remove(i);
                    return;
                }
            }
        }
    }

    private void addLens(Map<Integer, List<Lens>> map, Lens lens) {
        int index = (int)hash(lens.label);

        if (!map.containsKey(index)) { // index box does not exist
            List<Lens> box = new ArrayList<>();
            box.add(lens);
            map.put(index, box);
        }
        else {
            List<Lens> box = map.get(index);
            for (Lens currentLens : box) {
                if (lens.label.equals(currentLens.label)) { // replace with new focal length if the lens exists
                    currentLens.focalLength = lens.focalLength;
                    return;
                }
            }

            box.add(lens); // add new lens to the list
        }
    }

    private long hash(String text) {
        long hash = 0;
        for (int i = 0; i < text.length(); i++) {
            int asciiCode = text.charAt(i);
            hash += asciiCode;
            hash = hash * 17 % 256;
        }
        return hash;
    }
}
