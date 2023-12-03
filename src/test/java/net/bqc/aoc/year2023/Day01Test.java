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

public class Day01Test extends AbstractTest {

    private Day01 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2023/Day01_Input.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day01();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.PART_NUMBER part, List<String> inputLines, int expectedSum) {
        int computedSum = solution.solve(part, inputLines);
        assertEquals(expectedSum, computedSum);
    }

    @ParameterizedTest
    @MethodSource("sampleDataSource")
    void testParseCalibrationValue(String calibrationLine, int expectedCalibrationValue) {
        int parsedCalibrationValue = solution.parseCalibrationValue(calibrationLine);
        assertEquals(expectedCalibrationValue, parsedCalibrationValue);
    }

    static Stream<Arguments> inputDataSource() {
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.PART_NUMBER.ONE, inputLines, 54953),
            Arguments.of(Solution.PART_NUMBER.TWO, inputLines, 53868)
        );
    }

    static Stream<Arguments> sampleDataSource() {
        return Stream.of(
            Arguments.of("9cbncbxclbvkmfzdnldc", 99),


            Arguments.of("1abc2", 12),
            Arguments.of("pqr3stu8vwx", 38),
            Arguments.of("a1b2c3d4e5f", 15),
            Arguments.of("treb7uchet", 77)
        );
    }
}
