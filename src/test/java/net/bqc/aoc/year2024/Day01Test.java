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

public class Day01Test extends AbstractTest {

    private Day01 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2024/Day01_Input.txt";
        SAMPLE_INPUT_PATH = "year2024/Day01_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day01();
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
            Arguments.of(Solution.Part.ONE, sampleInputLines, 11),
            Arguments.of(Solution.Part.ONE, inputLines, 1590491),
            
            Arguments.of(Solution.Part.TWO, sampleInputLines, 31),
            Arguments.of(Solution.Part.TWO, inputLines, 22588371)
        );
    }
}
