package net.bqc.aoc.year2024;

import net.bqc.aoc.AbstractTest;
import net.bqc.aoc.Solution;
import net.bqc.aoc.year2023.Day05;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.Arrays;
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
    void testSolver(Solution.PART_NUMBER part, List<String> inputLines, long expectedSum) {
        long computedSum = solution.solve(part, inputLines);
        assertEquals(expectedSum, computedSum);
    }

    @ParameterizedTest
    @MethodSource("sampleDataSource")
    void testSolver_2(Solution.PART_NUMBER part, List<String> sampleInputLines, long expected) {
        long computedSum = solution.solve(part, sampleInputLines);
        assertEquals(expected, computedSum);
    }

    static Stream<Arguments> sampleDataSource() {
        return Stream.of(
            Arguments.of(Solution.PART_NUMBER.ONE, List.of("12345"), 60)
        );
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.PART_NUMBER.ONE, sampleInputLines, 1928),
            Arguments.of(Solution.PART_NUMBER.ONE, inputLines, 6367087064415L)
            
//            Arguments.of(Solution.PART_NUMBER.TWO, sampleInputLines, 2858),
//            Arguments.of(Solution.PART_NUMBER.TWO, inputLines, 0)
        );
    }
}
