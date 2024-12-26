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

public class Day23Test extends AbstractTest {

    private Day23 solution;

    @BeforeAll
    static void setUp() {
        INPUT_PATH = "year2024/Day23_Input.txt";
        SAMPLE_INPUT_PATH = "year2024/Day23_SampleInput.txt";
    }

    @BeforeEach
    void init() {
        solution = new Day23();
    }

    @ParameterizedTest
    @MethodSource("inputDataSource")
    void testSolver(Solution.Part part, List<String> inputLines, String expectedOutput) {
        String computedOutput = solution.solve(part, inputLines);
        assertEquals(expectedOutput, computedOutput);
    }

    static Stream<Arguments> inputDataSource() {
        List<String> sampleInputLines = getSampleInput();
        List<String> inputLines = getInput();
        return Stream.of(
            Arguments.of(Solution.Part.ONE, sampleInputLines, "7"),
            Arguments.of(Solution.Part.ONE, inputLines, "1108"),
            
            Arguments.of(Solution.Part.TWO, sampleInputLines, "co,de,ka,ta"),
            Arguments.of(Solution.Part.TWO, inputLines, "ab,cp,ep,fj,fl,ij,in,ng,pl,qr,rx,va,vf")
        );
    }
}
