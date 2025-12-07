package main

import (
	"fmt"
	"math"
	"strconv"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Day03 struct{}

func (d Day03) Solve(part c.Part, lines []string) int {
	if part == c.PART1 {
		return d.part1(lines)
	}
	return d.part2(lines)
}

func (d Day03) maxJoltage(bank string, n int, memorization map[string]int) int {
	key := fmt.Sprintf("%s_%d", bank, n)
	if v, ok := memorization[key]; ok {
		return v
	}

	if n == 0 {
		return 0
	}

	if len(bank) == n {
		v, _ := strconv.Atoi(bank)
		return v
	}

	fst := int(float64(bank[0]-'0')*math.Pow(10.0, float64(n-1))) + d.maxJoltage(bank[1:], n-1, memorization)
	snd := d.maxJoltage(bank[1:], n, memorization)

	maxVal := int(math.Max(float64(fst), float64(snd)))
	memorization[key] = maxVal
	return maxVal
}

func (d Day03) part2(lines []string) int {
	res := 0
	memorization := make(map[string]int)
	for _, bank := range lines {
		res += d.maxJoltage(bank, 12, memorization)
	}
	return res
}

func (d Day03) part1(lines []string) int {
	res := 0
	for _, bank := range lines {
		best, maxRight := -1, -1
		for i := len(bank) - 1; i >= 0; i-- {
			d := int(bank[i] - '0')
			if maxRight != -1 && d*10+maxRight > best {
				best = d*10 + maxRight
			}
			if d > maxRight {
				maxRight = d
			}
		}
		res += best
	}
	return res
}
