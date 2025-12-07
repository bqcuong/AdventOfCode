package common

import (
	"fmt"
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

func PrintMatrix(mat [][]rune) {
	for _, row := range mat {
		for _, c := range row {
			fmt.Print(string(c))
		}
		fmt.Println()
	}
}

func Search(mat [][]rune, toSearch rune) *Pos {
	for i := range len(mat) {
		for j := range len(mat[i]) {
			if mat[i][j] == toSearch {
				return &Pos{j, i}
			}
		}
	}
	return nil
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
