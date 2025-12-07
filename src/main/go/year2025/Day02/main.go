package main

import (
	"strconv"
	"strings"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Day02 struct{}

func (d Day02) isRepeatedTwice(id string) bool {
	return len(id)%2 == 0 && id[:len(id)/2] == id[len(id)/2:]
}

func (d Day02) isRepeated(id string) bool {
	for subLen := 1; subLen <= len(id)/2; subLen++ {
		if len(id)%subLen != 0 {
			continue
		}

		i := 0
		for ; i <= len(id)-subLen; i += subLen {
			if id[i:i+subLen] != id[:subLen] {
				break
			}
		}
		if i == len(id) {
			return true
		}
	}
	return false
}

func (d Day02) Solve(part c.Part, lines []string) int {
	idPairs := strings.Split(lines[0], ",")

	sum, sum2 := 0, 0
	for _, pairStr := range idPairs {
		pair := strings.Split(pairStr, "-")
		firstId, _ := strconv.Atoi(pair[0])
		sndId, _ := strconv.Atoi(pair[1])

		for id := firstId; id <= sndId; id++ {
			if d.isRepeatedTwice(strconv.Itoa(id)) {
				sum += id
			}
			if d.isRepeated(strconv.Itoa(id)) {
				sum2 += id
			}
		}
	}
	if part == c.PART1 {
		return sum
	}
	return sum2
}
