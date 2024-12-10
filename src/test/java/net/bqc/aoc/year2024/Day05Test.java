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

public class Day05Test extends AbstractTest {

    private Day05 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2024/Day05_Input.txt";
        SAMPLE_INPUT_PATH = "year2024/Day05_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day05();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.Part part, List<String> inputLines, long expectedSum) {
        long computedSum = solution.solve(part, inputLines);
        assertEquals(expectedSum, computedSum);
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.Part.ONE, sampleInputLines, 143),
            Arguments.of(Solution.Part.ONE, inputLines, 4185),
            
            Arguments.of(Solution.Part.TWO, sampleInputLines, 123),
            Arguments.of(Solution.Part.TWO, inputLines, 4480)
        );
    }
}
