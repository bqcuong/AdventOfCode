package net.bqc.aoc.year2024;

import net.bqc.aoc.AbstractTest;
import net.bqc.aoc.Solution;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Test extends AbstractTest {

    private Day18 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2024/Day18_Input.txt";
        SAMPLE_INPUT_PATH = "year2024/Day18_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day18();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.Part part, List<String> inputLines, String expectedOutput) {
        String computedOutput = solution.solve(part, inputLines);
        assertEquals(expectedOutput, computedOutput);
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.Part.ONE, sampleInputLines, "22"),
            Arguments.of(Solution.Part.ONE, inputLines, "282"),
            
            Arguments.of(Solution.Part.TWO, sampleInputLines, "6,1"),
            Arguments.of(Solution.Part.TWO, inputLines, "64,29")
        );
    }
}
