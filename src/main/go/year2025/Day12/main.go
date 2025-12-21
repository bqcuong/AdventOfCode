package main

import (
	"strconv"
	"strings"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Day12 struct{}

func (d Day12) Solve(part c.Part, lines []string) int {
	if part == c.PART2 {
		return 0
	}
	return d.countValidRegions(lines)
}

func (d Day12) countValidRegions(lines []string) int {
	shapeIdx := 0
	shapeAreas := make(map[int]int)
	count := 0

	for _, line := range lines {
		if strings.HasSuffix(line, ":") {
			shapeIdx, _ = strconv.Atoi(line[:len(line)-1])
			shapeAreas[shapeIdx] = 0
		} else if strings.Contains(line, "#") {
			shapeAreas[shapeIdx] += strings.Count(line, "#")
		} else if strings.Contains(line, "x") {
			sizeStr := strings.Split(line, ":")[0]
			quantityStr := strings.TrimSpace(strings.Split(line, ":")[1])
			w, _ := strconv.Atoi(strings.Split(sizeStr, "x")[0])
			h, _ := strconv.Atoi(strings.Split(sizeStr, "x")[1])
			quantities := strings.Fields(quantityStr)

			requiredArea := 0
			for i, quant := range quantities {
				q, _ := strconv.Atoi(quant)
				requiredArea += q * shapeAreas[i]
			}
			if requiredArea <= w*h {
				count++
			}
		}
	}
	return count
}
