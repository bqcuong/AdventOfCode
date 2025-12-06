package common

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

func IsBlankCol(mat [][]rune, col int) bool {
	for i := 0; i < len(mat); i++ {
		if mat[i][col] != ' ' {
			return false
		}
	}
	return true
}

func ReadStrMatrix(lines []string) [][]string {
	mat := make([][]string, len(lines))
	for i, line := range lines {
		mat[i] = strings.Fields(line)
	}
	return mat
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
	lines := strings.Split(string(data), "\n")
	if lines[len(lines)-1] == "" {
		lines = lines[:len(lines)-1]
	}
	return lines, nil
}
