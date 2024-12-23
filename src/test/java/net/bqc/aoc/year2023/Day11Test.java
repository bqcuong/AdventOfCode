package net.bqc.aoc.year2023;

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

public class Day11Test extends AbstractTest {

    private Day11 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2023/Day11_Input.txt";
        SAMPLE_INPUT_PATH = "year2023/Day11_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day11();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.Part part, List<String> inputLines, long expectedOutput) {
        long computedOutput = solution.solve(part, inputLines);
        assertEquals(expectedOutput, computedOutput);
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.Part.ONE, sampleInputLines, 374),
            Arguments.of(Solution.Part.ONE, inputLines, 9734203),

            Arguments.of(Solution.Part.TWO, sampleInputLines, 82000210),
            Arguments.of(Solution.Part.TWO, inputLines, 568914596391L)
        );
    }
}
