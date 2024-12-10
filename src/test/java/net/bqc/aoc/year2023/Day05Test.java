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

public class Day05Test extends AbstractTest {

    private Day05 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2023/Day05_Input.txt";
        SAMPLE_INPUT_PATH = "year2023/Day05_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day05();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.Part part, List<String> inputLines, BigInteger expectedSum) {
        BigInteger computedSum = solution.solve2(part, inputLines);
        assertEquals(expectedSum, computedSum);
    }

    @ParameterizedTest
    @MethodSource("sampleDataSource")
    void testParseProblem(List<String> sampleInputLines, BigInteger seed, BigInteger expectedLocation) {
        Day05.Problem p = this.solution.parseProblem(sampleInputLines);
        BigInteger calculatedLocation = p.getMappedValueInAllMaps(seed);
        assertEquals(expectedLocation, calculatedLocation);
    }

    static Stream<Arguments> sampleDataSource() {
        List<String> sampleInputLines = getSampleInput();
        return Stream.of(
            Arguments.of(sampleInputLines, new BigInteger("79"), new BigInteger("82")),
            Arguments.of(sampleInputLines, new BigInteger("14"), new BigInteger("43")),
            Arguments.of(sampleInputLines, new BigInteger("55"), new BigInteger("86")),
            Arguments.of(sampleInputLines, new BigInteger("13"), new BigInteger("35"))
        );
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.Part.ONE, sampleInputLines, new BigInteger("35")),
//            Arguments.of(Solution.PART_NUMBER.TWO, sampleInputLines, new BigInteger("46"))
//
            Arguments.of(Solution.Part.ONE, inputLines, new BigInteger("825516882"))
//            Arguments.of(Solution.PART_NUMBER.TWO, inputLines, new BigInteger("35"))
        );
    }
}
