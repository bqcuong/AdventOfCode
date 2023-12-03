package net.bqc.aoc;

import net.bqc.aoc.utils.ResourcesUtils;

import java.util.List;

public abstract class AbstractTest {

    protected static String INPUT_PATH;
    protected static String PART1_SAMPLE_INPUT_PATH;

    protected static String PART2_SAMPLE_INPUT_PATH;

    protected static List<String> getInput() {
        return List.of(ResourcesUtils.readResourceFile(INPUT_PATH).split("\n"));
    }

    protected static List<String> getPart1SampleInput() {
        return List.of(ResourcesUtils.readResourceFile(PART1_SAMPLE_INPUT_PATH).split("\n"));
    }

    protected static List<String> getPart2SampleInput() {
        return List.of(ResourcesUtils.readResourceFile(PART2_SAMPLE_INPUT_PATH).split("\n"));
    }
}
