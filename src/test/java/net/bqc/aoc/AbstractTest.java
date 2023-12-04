package net.bqc.aoc;

import net.bqc.aoc.utils.ResourcesUtils;

import java.util.List;

public abstract class AbstractTest {

    protected static String INPUT_PATH;
    protected static String SAMPLE_INPUT_PATH;

    protected static List<String> getInput() {
        return List.of(ResourcesUtils.readResourceFile(INPUT_PATH).split("\n"));
    }

    protected static List<String> getSampleInput() {
        return List.of(ResourcesUtils.readResourceFile(SAMPLE_INPUT_PATH).split("\n"));
    }
}
