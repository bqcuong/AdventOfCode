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

public class Day02Test extends AbstractTest {

    private Day02 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2024/Day02_Input.txt";
        SAMPLE_INPUT_PATH = "year2024/Day02_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day02();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.PART_NUMBER part, List<String> inputLines, long expectedSum) {
        long computedSum = solution.solve(part, inputLines);
        assertEquals(expectedSum, computedSum);
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.PART_NUMBER.ONE, sampleInputLines, 2),
            Arguments.of(Solution.PART_NUMBER.ONE, inputLines, 242),
            
            Arguments.of(Solution.PART_NUMBER.TWO, sampleInputLines, 4),
            Arguments.of(Solution.PART_NUMBER.TWO, inputLines, 311)
        );
    }
}
