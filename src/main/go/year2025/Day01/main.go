package main

import (
	"strconv"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Day01 struct{}

func (d Day01) Solve(part c.Part, lines []string) int {
	points := 50
	points2 := 50
	count := 0
	count2 := 0
	for _, line := range lines {
		rotation := line[0]
		step, _ := strconv.Atoi(line[1:])
		if rotation == 'L' {
			points -= step
		} else {
			points += step
		}
		points = (points + 100) % 100
		if points == 0 {
			count++
		}

		for range step {
			if rotation == 'L' {
				points2--
			} else {
				points2++
			}
			if points2%100 == 0 {
				count2++
			}
		}
	}
	if part == c.PART1 {
		return count
	}
	return count2
}
