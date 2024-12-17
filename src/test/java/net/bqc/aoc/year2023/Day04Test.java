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

public class Day04Test extends AbstractTest {

    private Day04 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2023/Day04_Input.txt";
        SAMPLE_INPUT_PATH = "year2023/Day04_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day04();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.Part part, List<String> inputLines, long expectedOutput) {
        long computedOutput = solution.solve(part, inputLines);
        assertEquals(expectedOutput, computedOutput);
    }

    @ParameterizedTest
    @MethodSource("sampleDataSource")
    void testParseCardPoint(String inputLine, long expectedOutput) {
        long computedOutput = solution.parseCardPoint(inputLine).winningPoints;
        assertEquals(expectedOutput, computedOutput);
    }

    static Stream<Arguments> sampleDataSource() {
        return Stream.of(
            Arguments.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", 8),
            Arguments.of("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", 2),
            Arguments.of("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1", 2),
            Arguments.of("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83", 1),
            Arguments.of("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36", 0),
            Arguments.of("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11", 0)
        );
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.Part.ONE, sampleInputLines, 13),
            Arguments.of(Solution.Part.TWO, sampleInputLines, 30),

            Arguments.of(Solution.Part.ONE, inputLines, 24542),
            Arguments.of(Solution.Part.TWO, inputLines, 8736438)
        );
    }
}
