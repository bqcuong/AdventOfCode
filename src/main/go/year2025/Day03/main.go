package main

import (
	"fmt"
	"log"
	"os"
	"strings"
)

type Part int

const (
	PART1 Part = iota
	PART2 Part = iota
)

func readTextFile(path string) ([]string, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}
	return strings.Split(string(data), "\n"), nil
}

func solve(part Part, lines []string) int {
	if part == PART1 {
		return part1(lines)
	}
	return 0
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
	//lines, err := readTextFile("sample_input.txt")
	lines, err := readTextFile("input.txt")
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println(solve(PART1, lines))
}
