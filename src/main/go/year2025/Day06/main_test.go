package main

import (
	"log"
	"testing"

	c "github.com/bqcuong/AdventOfCode/common"
)

func TestPart1(t *testing.T) {
	tests := []struct {
		name      string
		inputPath string
		expected  int
	}{
		{
			name:      "Sample Input",
			inputPath: "year2025/Day06/sample_input.txt",
			expected:  4277556,
		},
		{
			name:      "Input",
			inputPath: "year2025/Day06/input.txt",
			expected:  4951502530386,
		},
	}
	var day c.Day = Day06{}
	for _, tc := range tests {
		t.Run(tc.name, func(t *testing.T) {
			lines, err := c.ReadTextFile(tc.inputPath)
			if err != nil {
				log.Fatal(err)
			}
			if got := day.Solve(c.PART1, lines); got != tc.expected {
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
			inputPath: "year2025/Day06/sample_input.txt",
			expected:  3263827,
		},
		{
			name:      "Input",
			inputPath: "year2025/Day06/input.txt",
			expected:  8486156119946,
		},
	}

	var day c.Day = Day06{}
	for _, tc := range tests {
		t.Run(tc.name, func(t *testing.T) {
			lines, err := c.ReadTextFile(tc.inputPath)
			if err != nil {
				log.Fatal(err)
			}
			if got := day.Solve(c.PART2, lines); got != tc.expected {
				t.Errorf("actual = %v, expected %v", got, tc.expected)
			}
		})
	}
}
