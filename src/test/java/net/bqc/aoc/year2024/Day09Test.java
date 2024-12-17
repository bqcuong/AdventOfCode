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

public class Day09Test extends AbstractTest {

    private Day09 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2024/Day09_Input.txt";
        SAMPLE_INPUT_PATH = "year2024/Day09_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day09();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.Part part, List<String> inputLines, long expectedOutput) {
        long computedOutput = solution.solve(part, inputLines);
        assertEquals(expectedOutput, computedOutput);
    }

    @ParameterizedTest
    @MethodSource("sampleDataSource")
    void testSolver_2(Solution.Part part, List<String> sampleInputLines, long expectedOutput) {
        long computedOutput = solution.solve(part, sampleInputLines);
        assertEquals(expectedOutput, computedOutput);
    }

    static Stream<Arguments> sampleDataSource() {
        return Stream.of(
            Arguments.of(Solution.Part.ONE, List.of("12345"), 60)
        );
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.Part.ONE, sampleInputLines, 1928),
            Arguments.of(Solution.Part.ONE, inputLines, 6367087064415L),
            
            Arguments.of(Solution.Part.TWO, sampleInputLines, 2858),
            Arguments.of(Solution.Part.TWO, inputLines, 6390781891880L)
        );
    }
}
