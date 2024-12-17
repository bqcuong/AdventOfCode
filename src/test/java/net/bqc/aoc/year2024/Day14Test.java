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

public class Day14Test extends AbstractTest {

    private Day14 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2024/Day14_Input.txt";
        SAMPLE_INPUT_PATH = "year2024/Day14_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day14();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.Part part, List<String> inputLines, long expectedOutput) {
        long computedOutput = solution.solve(part, inputLines);
        assertEquals(expectedOutput, computedOutput);
    }

    static Stream<Arguments> inputDataSource() {
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.Part.ONE, inputLines, 233709840L),
            Arguments.of(Solution.Part.TWO, inputLines, 6620)
        );
    }
}
