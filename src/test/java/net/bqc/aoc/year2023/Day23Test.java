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

public class Day23Test extends AbstractTest {

    private Day23 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2023/Day23_Input.txt";
        SAMPLE_INPUT_PATH = "year2023/Day23_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day23();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.Part part, List<String> inputLines, long expectedSum) {
        long computedSum = solution.solve(part, inputLines);
        assertEquals(expectedSum, computedSum);
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.Part.ONE, sampleInputLines, 0)
//            Arguments.of(Solution.PART_NUMBER.ONE, inputLines, 0)
            
//            Arguments.of(Solution.PART_NUMBER.TWO, sampleInputLines, 0),
//            Arguments.of(Solution.PART_NUMBER.TWO, inputLines, 0)
        );
    }
}
