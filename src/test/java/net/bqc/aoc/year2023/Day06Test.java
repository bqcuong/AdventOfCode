package net.bqc.aoc.year2023;

import net.bqc.aoc.AbstractTest;
import net.bqc.aoc.Solution;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test extends AbstractTest {

    private Day06 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2023/Day06_Input.txt";
        SAMPLE_INPUT_PATH = "year2023/Day06_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day06();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.Part part, List<String> inputLines, BigInteger expectedSum) {
        BigInteger computedSum = solution.solve2(part, inputLines);
        assertEquals(expectedSum, computedSum);
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.Part.ONE, sampleInputLines, new BigInteger("288")),
            Arguments.of(Solution.Part.TWO, sampleInputLines, new BigInteger("71503")),

            Arguments.of(Solution.Part.ONE, inputLines, new BigInteger("1413720")),
            Arguments.of(Solution.Part.TWO, inputLines, new BigInteger("30565288"))
        );
    }
}
