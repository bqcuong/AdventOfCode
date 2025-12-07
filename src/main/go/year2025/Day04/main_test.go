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
			inputPath: "sample_input.txt",
			expected:  13,
		},
		{
			name:      "Input",
			inputPath: "input.txt",
			expected:  1553,
		},
	}

	var day c.Day = Day04{}
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
			inputPath: "sample_input.txt",
			expected:  43,
		},
		{
			name:      "Input",
			inputPath: "input.txt",
			expected:  8442,
		},
	}
	var day c.Day = Day04{}
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
