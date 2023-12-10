package net.bqc.aoc.year2023;

import net.bqc.aoc.AbstractTest;
import net.bqc.aoc.Solution;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test extends AbstractTest {

    private Day09 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2023/Day09_Input.txt";
        SAMPLE_INPUT_PATH = "year2023/Day09_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day09();
    }

    @ParameterizedTest
    @MethodSource("sampleDataSource")
    void testPredictMetric(List<Long> history, long expectedValue) {
        long predictValue = solution.predict(history);
        assertEquals(expectedValue, predictValue);
    }

    @ParameterizedTest
    @MethodSource("sampleDataSource2")
    void testPredictMetric2(List<Long> history, long expectedValue) {
        long predictValue = solution.predictBackward(history);
        assertEquals(expectedValue, predictValue);
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
            Arguments.of(Solution.PART_NUMBER.ONE, sampleInputLines, 114),
            Arguments.of(Solution.PART_NUMBER.ONE, inputLines, 1868368343),

            Arguments.of(Solution.PART_NUMBER.TWO, sampleInputLines, 2),
            Arguments.of(Solution.PART_NUMBER.TWO, inputLines, 1022)
        );
    }

    static Stream<Arguments> sampleDataSource() {
        return Stream.of(
            Arguments.of(Arrays.stream("0 3 6 9 12 15".split("\s")).map(Long::parseLong).toList(), 18),
            Arguments.of(Arrays.stream("1 3 6 10 15 21".split("\s")).map(Long::parseLong).toList(), 28),
            Arguments.of(Arrays.stream("10 13 16 21 30 45".split("\s")).map(Long::parseLong).toList(), 68)
        );
    }

    static Stream<Arguments> sampleDataSource2() {
        return Stream.of(
            Arguments.of(Arrays.stream("10 13 16 21 30 45".split("\s")).map(Long::parseLong).toList(), 5)
        );
    }
}
