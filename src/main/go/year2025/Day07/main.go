package main

import (
	"fmt"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Day07 struct{}

func (d Day07) Solve(part c.Part, lines []string) int {
	mat := c.ReadMatrix(lines)
	if part == c.PART1 {
		return d.SplitBeam(mat)
	}
	return d.SplitBeamQuantum(mat, c.Search(mat, 'S'), make(map[string]int))
}
func (d Day07) SplitBeamQuantum(mat [][]rune, currentPos *c.Pos, memorization map[string]int) int {
	if currentPos.Y == len(mat)-1 {
		return 1
	}

	if cnt, ok := memorization[fmt.Sprintf("%d_%d", currentPos.Y, currentPos.X)]; ok {
		return cnt
	}

	count := 0
	if mat[currentPos.Y][currentPos.X] == 'S' {
		count += d.SplitBeamQuantum(mat, &c.Pos{X: currentPos.X, Y: currentPos.Y + 1}, memorization)
	} else if mat[currentPos.Y][currentPos.X] == '^' {
		// try left
		if currentPos.X > 0 {
			mat[currentPos.Y][currentPos.X-1] = '|'
			count += d.SplitBeamQuantum(mat, &c.Pos{X: currentPos.X - 1, Y: currentPos.Y + 1}, memorization)
			mat[currentPos.Y][currentPos.X-1] = '.'
		}

		// try right
		if currentPos.X < len(mat[0])-1 {
			mat[currentPos.Y][currentPos.X+1] = '|'
			count += d.SplitBeamQuantum(mat, &c.Pos{X: currentPos.X + 1, Y: currentPos.Y + 1}, memorization)
			mat[currentPos.Y][currentPos.X+1] = '.'
		}
	} else {
		mat[currentPos.Y][currentPos.X] = '|'
		count += d.SplitBeamQuantum(mat, &c.Pos{X: currentPos.X, Y: currentPos.Y + 1}, memorization)
		mat[currentPos.Y][currentPos.X] = '.'
	}
	memorization[fmt.Sprintf("%d_%d", currentPos.Y, currentPos.X)] = count
	return count
}

func (d Day07) SplitBeam(mat [][]rune) int {
	splitCount := 0
	for i := 1; i < len(mat); i++ {
		for j := 0; j < len(mat[i]); j++ {
			if mat[i][j] == '^' && mat[i-1][j] == '|' {
				if j > 0 {
					mat[i][j-1] = '|'
				}
				if j < len(mat[i])-1 {
					mat[i][j+1] = '|'
				}
				splitCount++
			} else if mat[i-1][j] == 'S' || mat[i-1][j] == '|' {
				mat[i][j] = '|'
			}
		}
	}
	return splitCount
}
