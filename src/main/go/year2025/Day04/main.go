package main

import (
	"fmt"
	"log"

	u "github.com/bqcuong/AdventOfCode/utils"
)

func isAccessibleRoll(mat [][]rune, i int, j int) bool {
	m, n := len(mat), len(mat[0])
	check := 0
	for di := -1; di <= 1; di++ {
		for dj := -1; dj <= 1; dj++ {
			if di == 0 && dj == 0 {
				continue
			}
			if i+di >= 0 && i+di < m && j+dj >= 0 && j+dj < n && mat[i+di][j+dj] == '@' {
				check++
			}
		}
	}
	return check < 4
}

func getAccessibleRolls(mat [][]rune) []u.Pos {
	m, n := len(mat), len(mat[0])
	positions := make([]u.Pos, 0)
	for i := 0; i < m; i++ {
		for j := 0; j < n; j++ {
			if mat[i][j] != '@' {
				continue
			}
			if isAccessibleRoll(mat, i, j) {
				positions = append(positions, u.Pos{X: i, Y: j})
			}
		}
	}
	return positions
}

func countRemovableRolls(mat [][]rune) int {
	count := 0
	for {
		accessibleRolls := getAccessibleRolls(mat)
		if len(accessibleRolls) == 0 {
			break
		}
		count += len(accessibleRolls)
		for _, roll := range accessibleRolls {
			mat[roll.X][roll.Y] = 'x'
		}
	}
	return count
}

func solve(part u.Part, lines []string) int {
	mat := u.ReadMatrix(lines)
	if part == u.PART1 {
		return len(getAccessibleRolls(mat))
	}
	return countRemovableRolls(mat)
}

func main() {
	//lines, err := readTextFile("year2025/Day04/sample_input.txt")
	lines, err := u.ReadTextFile("year2025/Day04/input.txt")
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println(solve(u.PART1, lines))
	fmt.Println(solve(u.PART2, lines))
}
