package main

import (
	"fmt"
	"log"
	"strconv"
	"strings"

	c "github.com/bqcuong/AdventOfCode/common"
)

func isRepeatedTwice(id string) bool {
	return len(id)%2 == 0 && id[:len(id)/2] == id[len(id)/2:]
}

func isRepeated(id string) bool {
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

func main() {
	//lines, err := readTextFile("year2025/Day02/sample_input.txt")
	lines, err := c.ReadTextFile("year2025/Day02/input.txt")
	if err != nil {
		log.Fatal(err)
	}

	idPairs := strings.Split(lines[0], ",")

	sum, sum2 := 0, 0
	for _, pairStr := range idPairs {
		pair := strings.Split(pairStr, "-")
		firstId, _ := strconv.Atoi(pair[0])
		sndId, _ := strconv.Atoi(pair[1])

		for id := firstId; id <= sndId; id++ {
			if isRepeatedTwice(strconv.Itoa(id)) {
				sum += id
			}
			if isRepeated(strconv.Itoa(id)) {
				sum2 += id
			}
		}
	}

	fmt.Println(sum, sum2)
}
