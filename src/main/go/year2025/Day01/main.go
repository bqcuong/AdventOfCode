package main

import (
	"fmt"
	"log"
	"strconv"

	u "github.com/bqcuong/AdventOfCode/utils"
)

func main() {
	//lines, err := readTextFile("year2025/Day01/sample_input.txt")
	lines, err := u.ReadTextFile("year2025/Day01/input.txt")
	if err != nil {
		log.Fatal(err)
	}

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

	fmt.Println(count, count2)
}
