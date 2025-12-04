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
			inputPath: "year2025/Day04/input.txt",
			expected:  1553,
		},
	}
	for _, tc := range tests {
		t.Run(tc.name, func(t *testing.T) {
			lines, err := u.ReadTextFile(tc.inputPath)
			if err != nil {
				log.Fatal(err)
			}
			if got := solve(u.PART1, lines); got != tc.expected {
				t.Errorf("part1 = %v, expected %v", got, tc.expected)
			}
		})
	}
}
