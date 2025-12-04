package main

import (
	"fmt"
	"log"
	"os"
	"strings"
)

type Part int

const (
	PART1 Part = iota
	PART2 Part = iota
)

func readTextFile(path string) ([]string, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}
	return strings.Split(strings.TrimSpace(string(data)), "\n"), nil

}

func readMatrix(lines []string) [][]rune {
	mat := make([][]rune, len(lines))
	for i, line := range lines {
		mat[i] = []rune(line)
	}
	return mat
}


func part1(mat [][]rune) int {
	m, n := len(mat), len(mat[0])
	count := 0
	for i := 0; i < m; i++ {
		for j := 0; j < n; j++ {
            if mat[i][j] != '@' { continue }
			check := 0
            for di := -1; di <= 1; di++ {
                for dj := -1; dj <= 1; dj++ {
                    if di == 0 && dj == 0 { continue }
                    if i+di >= 0 && i+di < m && j+dj >= 0 && j+dj < n && mat[i+di][j+dj] == '@' {
                        check++
                    }
                }
            }
			if check < 4 {
				count++
			}
		}
	}
	return count
}

func solve(part Part, lines []string) int {
    mat := readMatrix(lines)
	if part == PART1 {
        return part1(mat)
	}
	return 0
}

func main() {
	//lines, err := readTextFile("sample_input.txt")
	lines, err := readTextFile("input.txt")
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println(solve(PART1, lines))
}
