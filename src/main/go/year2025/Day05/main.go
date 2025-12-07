package main

import (
	"math"
	"sort"
	"strconv"
	"strings"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Range struct {
	start, end int
}

type Day05 struct{}

func (d Day05) Solve(part c.Part, lines []string) int {
	freshIngrds, availableIngrds := d.readIngredient(lines)
	if part == c.PART1 {
		count := 0
		for _, ingrd := range availableIngrds {
			for _, ingrdRange := range freshIngrds {
				if ingrdRange.start <= ingrd && ingrd <= ingrdRange.end {
					count++
					break
				}
			}
		}
		return count
	}
	return d.countAllFreshIngrds(freshIngrds)
}

func (d Day05) countAllFreshIngrds(freshIngrds []Range) int {
	sort.Slice(freshIngrds, func(i, j int) bool {
		return freshIngrds[i].start < freshIngrds[j].start
	})

	mergedRanges := make([]Range, 0)
	for _, ingrd := range freshIngrds {
		if len(mergedRanges) > 0 && mergedRanges[len(mergedRanges)-1].end >= ingrd.start {
			mergedRanges[len(mergedRanges)-1].end = int(math.Max(float64(ingrd.end), float64(mergedRanges[len(mergedRanges)-1].end)))
		} else {
			mergedRanges = append(mergedRanges, ingrd)
		}
	}
	count := 0
	for _, ingrd := range mergedRanges {
		count += ingrd.end - ingrd.start + 1
	}

	return count
}

func (d Day05) readIngredient(lines []string) ([]Range, []int) {
	freshIngrds := make([]Range, 0)

	idx := 0
	for _, line := range lines {
		idx++
		if line == "" {
			break
		}
		parts := strings.Split(line, "-")
		start, _ := strconv.Atoi(parts[0])
		end, _ := strconv.Atoi(parts[1])
		freshIngrds = append(freshIngrds, Range{start: start, end: end})
	}

	availableIngrds := make([]int, 0)
	for i := idx; i < len(lines); i++ {
		ingrd, _ := strconv.Atoi(lines[i])
		availableIngrds = append(availableIngrds, ingrd)
	}
	return freshIngrds, availableIngrds
}
