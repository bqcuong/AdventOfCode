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

public class Day22Test extends AbstractTest {

    private Day22 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2024/Day22_Input.txt";
        SAMPLE_INPUT_PATH = "year2024/Day22_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day22();
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
            Arguments.of(Solution.Part.ONE, sampleInputLines, 37327623),
            Arguments.of(Solution.Part.ONE, inputLines, 21147129593L),
            
            Arguments.of(Solution.Part.TWO, sampleInputLines, 24),
            Arguments.of(Solution.Part.TWO, inputLines, 2445)
        );
    }
}
