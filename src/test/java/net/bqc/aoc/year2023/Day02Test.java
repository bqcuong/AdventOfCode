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

public class Day02Test extends AbstractTest {

    private Day02 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2023/Day02_Input.txt";
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

    @ParameterizedTest
    @MethodSource("sampleDataSource")
    void testCheckGamePossible(String gameConfiguration, long expectedValue) {
        long isPossible = solution.checkGamePossible(gameConfiguration);
        assertEquals(expectedValue, isPossible);
    }

    static Stream<Arguments> inputDataSource() {
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.PART_NUMBER.ONE, inputLines, 2685),
            Arguments.of(Solution.PART_NUMBER.TWO, inputLines, 83707)
        );
    }

    static Stream<Arguments> sampleDataSource() {
        return Stream.of(
            Arguments.of("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green", 1),
            Arguments.of("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue", 2),
            Arguments.of("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red", -1),
            Arguments.of("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red", -1),
            Arguments.of("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green", 5)
        );
    }
}
