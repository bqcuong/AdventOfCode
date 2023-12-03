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

public class Day03Test extends AbstractTest {

    private Day03 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2023/Day03_Input.txt";
        PART1_SAMPLE_INPUT_PATH = "year2023/Day03_Part01_SampleInput.txt";
        PART2_SAMPLE_INPUT_PATH = "year2023/Day03_Part02_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day03();
    }

    @ParameterizedTest
    @MethodSource("sampleDataSource")
    void testSolverWithSample(Solution.PART_NUMBER part, List<String> inputLines, int expectedSum) {
        int computedSum = solution.solve(part, inputLines);
        assertEquals(expectedSum, computedSum);
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.PART_NUMBER part, List<String> inputLines, int expectedSum) {
        int computedSum = solution.solve(part, inputLines);
        assertEquals(expectedSum, computedSum);
    }

    static Stream<Arguments> sampleDataSource() {
        List<String> inputLines = getPart1SampleInput();
        return Stream.of(
            Arguments.of(Solution.PART_NUMBER.ONE, inputLines, 4361),
            Arguments.of(Solution.PART_NUMBER.TWO, inputLines, 467835)
        );
    }

    static Stream<Arguments> inputDataSource() {
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.PART_NUMBER.ONE, inputLines, 539590),
            Arguments.of(Solution.PART_NUMBER.TWO, inputLines, 80703636)
        );
    }
}
