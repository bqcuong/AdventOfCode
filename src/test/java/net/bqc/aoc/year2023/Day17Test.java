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

public class Day17Test extends AbstractTest {

    private Day17 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2023/Day17_Input.txt";
        SAMPLE_INPUT_PATH = "year2023/Day17_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day17();
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
            Arguments.of(Solution.Part.ONE, sampleInputLines, 102),
            Arguments.of(Solution.Part.ONE, inputLines, 843)

//            Arguments.of(Solution.PART_NUMBER.TWO, sampleInputLines, 71),
//            Arguments.of(Solution.PART_NUMBER.TWO, inputLines, 0)
        );
    }
}
