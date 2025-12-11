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
			expected:  5,
		},
		{
			name:      "Input",
			inputPath: "input.txt",
			expected:  796,
		},
	}
	var day c.Day = Day11{}
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
			inputPath: "sample_input2.txt",
			expected:  2,
		},
		{
			name:      "Input",
			inputPath: "input.txt",
			expected:  294053029111296,
		},
	}

	var day c.Day = Day11{}
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
