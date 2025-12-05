package main

import (
	"log"
	"testing"

	u "github.com/bqcuong/AdventOfCode/utils"
)

func TestPart1(t *testing.T) {
	tests := []struct {
		name      string
		inputPath string
		expected  int
	}{
		{
			name:      "Sample Input",
			inputPath: "year2025/Day05/sample_input.txt",
			expected:  3,
		},
		{
			name:      "Input",
			inputPath: "year2025/Day05/input.txt",
			expected:  840,
		},
	}
	for _, tc := range tests {
		t.Run(tc.name, func(t *testing.T) {
			lines, err := u.ReadTextFile(tc.inputPath)
			if err != nil {
				log.Fatal(err)
			}
			if got := solve(u.PART1, lines); got != tc.expected {
				t.Errorf("actual = %v, expected %v", got, tc.expected)
			}
		})
	}
}

func TestPart2(t *testing.T) {
	tests := []struct {
		name      string
		inputPath string
		expected  int
	}{
		{
			name:      "Sample Input",
			inputPath: "year2025/Day05/sample_input.txt",
			expected:  14,
		},
		{
			name:      "Input",
			inputPath: "year2025/Day05/input.txt",
			expected:  359913027576322,
		},
	}
	for _, tc := range tests {
		t.Run(tc.name, func(t *testing.T) {
			lines, err := u.ReadTextFile(tc.inputPath)
			if err != nil {
				log.Fatal(err)
			}
			if got := solve(u.PART2, lines); got != tc.expected {
				t.Errorf("actual = %v, expected %v", got, tc.expected)
			}
		})
	}
}
