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
			expected:  21,
		},
		{
			name:      "Input",
			inputPath: "input.txt",
			expected:  1622,
		},
	}
	var day c.Day = Day07{}
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
			expected:  40,
		},
		{
			name:      "Input",
			inputPath: "input.txt",
			expected:  10357305916520,
		},
	}

	var day c.Day = Day07{}
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
