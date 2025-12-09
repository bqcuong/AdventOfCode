package main

import (
	"strconv"
	"strings"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Day09 struct{}

func (d Day09) Solve(part c.Part, lines []string) int {
	redTiles := d.readRedTiles(lines)
	return d.maxArea(redTiles)
}

func (d Day09) maxArea(redTiles []c.Pos) int {
	maxArea := 0
	for i := 0; i < len(redTiles); i++ {
		for j := i + 1; j < len(redTiles); j++ {
			area := (c.AbsInt(redTiles[i].X, redTiles[j].X) + 1) * (c.AbsInt(redTiles[i].Y, redTiles[j].Y) + 1)
			if area > maxArea {
				maxArea = area
			}
		}
	}
	return maxArea
}

func (d Day09) readRedTiles(lines []string) []c.Pos {
	redTiles := make([]c.Pos, 0)
	for _, line := range lines {
		parts := strings.Split(line, ",")
		x, _ := strconv.Atoi(parts[0])
		y, _ := strconv.Atoi(parts[1])
		redTiles = append(redTiles, c.Pos{X: x, Y: y})
	}
	return redTiles
}
