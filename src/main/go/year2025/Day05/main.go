package main

import (
	"math"
	"sort"
	"strconv"
	"strings"

	u "github.com/bqcuong/AdventOfCode/utils"
)

type Range struct {
	start, end int
}

func solve(part u.Part, lines []string) int {
	freshIngrds, availableIngrds := readIngredient(lines)
	if part == u.PART1 {
		count := 0
		for _, ingrd := range availableIngrds {
			for _, ingrdRange := range freshIngrds {
				if ingrdRange.start <= ingrd && ingrd <= ingrdRange.end {
					count++
					break
				}
			}
		}
	}
	return countAllFreshIngrds(freshIngrds)
}

func countAllFreshIngrds(freshIngrds []Range) int {
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

func readIngredient(lines []string) ([]Range, []int) {
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
