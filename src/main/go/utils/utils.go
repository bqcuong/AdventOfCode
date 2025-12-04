package utils

import (
	"os"
	"strings"
)

type Part int

const (
	PART1 Part = iota
	PART2 Part = iota
)

type Pos struct {
	X int
	Y int
}

func ReadMatrix(lines []string) [][]rune {
	mat := make([][]rune, len(lines))
	for i, line := range lines {
		mat[i] = []rune(line)
	}
	return mat
}

func ReadTextFile(path string) ([]string, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}
	return strings.Split(strings.TrimSpace(string(data)), "\n"), nil
}
