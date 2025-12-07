package main

import (
	c "github.com/bqcuong/AdventOfCode/common"
)

type Day04 struct{}

func (d Day04) isAccessibleRoll(mat [][]rune, i int, j int) bool {
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

func (d Day04) getAccessibleRolls(mat [][]rune) []c.Pos {
	m, n := len(mat), len(mat[0])
	positions := make([]c.Pos, 0)
	for i := 0; i < m; i++ {
		for j := 0; j < n; j++ {
			if mat[i][j] != '@' {
				continue
			}
			if d.isAccessibleRoll(mat, i, j) {
				positions = append(positions, c.Pos{X: j, Y: i})
			}
		}
	}
	return positions
}

func (d Day04) countRemovableRolls(mat [][]rune) int {
	count := 0
	for {
		accessibleRolls := d.getAccessibleRolls(mat)
		if len(accessibleRolls) == 0 {
			break
		}
		count += len(accessibleRolls)
		for _, roll := range accessibleRolls {
			mat[roll.Y][roll.X] = 'x'
		}
	}
	return count
}

func (d Day04) Solve(part c.Part, lines []string) int {
	mat := c.ReadMatrix(lines)
	if part == c.PART1 {
		return len(d.getAccessibleRolls(mat))
	}
	return d.countRemovableRolls(mat)
}
