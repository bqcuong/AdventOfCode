package main

import (
	"fmt"
	"slices"
	"strings"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Day11 struct{}

type Device map[string][]string

func (d Day11) readDevice(lines []string) Device {
	device := Device{}
	for _, line := range lines {
		ios := strings.Split(line, ": ")
		device[ios[0]] = strings.Fields(ios[1])
	}
	return device
}

func (d Day11) Solve(part c.Part, lines []string) int {
	device := d.readDevice(lines)
	if part == c.PART1 {
		return dfs(device, "you")
	}
	return dfs2(device, "svr", false, false, make(map[string]int))
}

func dfs2(device Device, input string, seenDac bool, seenFft bool, memo map[string]int) int {
	key := fmt.Sprintf("%s_%t_%t", input, seenDac, seenFft)
	if cnt, ok := memo[key]; ok {
		return cnt
	}

	outputs, _ := device[input]
	if slices.Contains(outputs, "out") {
		cnt := 0
		if seenDac && seenFft {
			cnt = 1
		}
		memo[key] = cnt
		return cnt
	}

	count := 0
	for _, output := range outputs {
		if output == "dac" {
			count += dfs2(device, output, true, seenFft, memo)
		} else if output == "fft" {
			count += dfs2(device, output, seenDac, true, memo)
		} else {
			count += dfs2(device, output, seenDac, seenFft, memo)
		}
	}
	memo[key] = count
	return count
}

func dfs(device Device, input string) int {
	outputs, _ := device[input]
	if slices.Contains(outputs, "out") {
		cnt := 1
		return cnt
	}

	count := 0
	for _, output := range outputs {
		count += dfs(device, output)
	}
	return count
}
