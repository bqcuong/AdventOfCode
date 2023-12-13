package net.bqc.aoc;

import net.bqc.aoc.utils.IOUtils;

import java.util.List;

public abstract class AbstractTest {

    protected static String INPUT_PATH;
    protected static String SAMPLE_INPUT_PATH;

    protected static List<String> getInput() {
        return List.of(IOUtils.readResourceFile(INPUT_PATH).split("\n"));
    }

    protected static List<String> getSampleInput() {
        return List.of(IOUtils.readResourceFile(SAMPLE_INPUT_PATH).split("\n"));
    }
}
