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
			name:      "Input",
			inputPath: "year2025/Day04/input.txt",
			expected:  1553,
		},
	}
	for _, tc := range tests {
		t.Run(tc.name, func(t *testing.T) {
			lines, err := c.ReadTextFile(tc.inputPath)
			if err != nil {
				log.Fatal(err)
			}
			if got := solve(c.PART1, lines); got != tc.expected {
				t.Errorf("actual = %v, expected %v", got, tc.expected)
			}
		})
	}
}
