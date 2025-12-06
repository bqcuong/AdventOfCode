package main

import (
	"fmt"
	"log"
	"math"
	"strconv"

	c "github.com/bqcuong/AdventOfCode/common"
)

func solve(part c.Part, lines []string) int {
	if part == c.PART1 {
		return part1(lines)
	}
	return part2(lines)
}

func maxJoltage(bank string, n int, memorization map[string]int) int {
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

	fst := int(float64(bank[0]-'0')*math.Pow(10.0, float64(n-1))) + maxJoltage(bank[1:], n-1, memorization)
	snd := maxJoltage(bank[1:], n, memorization)

	maxVal := int(math.Max(float64(fst), float64(snd)))
	memorization[key] = maxVal
	return maxVal
}

func part2(lines []string) int {
	res := 0
	memorization := make(map[string]int)
	for _, bank := range lines {
		res += maxJoltage(bank, 12, memorization)
	}
	return res
}

func part1(lines []string) int {
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

func main() {
	//lines, err := readTextFile("year2025/Day03/sample_input.txt")
	lines, err := c.ReadTextFile("year2025/Day03/input.txt")
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println(solve(c.PART1, lines))
	fmt.Println(solve(c.PART2, lines))
}
