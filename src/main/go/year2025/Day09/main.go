package main

import (
	"strconv"
	"strings"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Day09 struct{}

func (d Day09) Solve(part c.Part, lines []string) int {
	redTiles := d.readRedTiles(lines)
	return d.maxArea(redTiles, part == c.PART2)
}

func (d Day09) maxArea(redTiles []c.Pos, nonIntersected bool) int {
	maxArea, maxAreaNonIntersected := 0, 0
	for i := 0; i < len(redTiles); i++ {
		for j := i + 1; j < len(redTiles); j++ {
			p1 := redTiles[i]
			p2 := redTiles[j]
			area := (c.AbsInt(p1.X, p2.X) + 1) * (c.AbsInt(p1.Y, p2.Y) + 1)
			if area > maxArea {
				maxArea = area
			}

			// check if any of the green rays run through the rectangle
			invalid := false
			for k := 0; k < len(redTiles); k++ {
				p3 := redTiles[k]
				p4 := redTiles[(k+1)%len(redTiles)]

				xRecBot, xRecTop := c.Sorted(p1.X, p2.X)
				yRecBot, yRecTop := c.Sorted(p1.Y, p2.Y)

				xRayBot, xRayTop := c.Sorted(p3.X, p4.X)
				yRayBot, yRayTop := c.Sorted(p3.Y, p4.Y)

				if (xRecBot < xRayTop && xRecTop > xRayBot) && (yRecBot < yRayTop && yRecTop > yRayBot) {
					invalid = true
					break
				}
			}
			if !invalid && area > maxAreaNonIntersected {
				maxAreaNonIntersected = area
			}
		}
	}
	if nonIntersected {
		return maxAreaNonIntersected
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
