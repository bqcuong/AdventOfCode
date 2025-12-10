package main

import (
	"strconv"
	"strings"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Day10 struct{}

type Button struct {
	ToggledIndicators []int
}

type Config struct {
	RequiredStates  []bool
	Buttons         []Button
	RequiredJoltage []int
}

func (d Day10) Solve(part c.Part, lines []string) int {
	configs := d.ReadConfigs(lines)
	return len(configs)
}

func (d Day10) ReadConfigs(lines []string) []Config {
	configs := make([]Config, 0)
	for _, line := range lines {
		config := Config{}
		parts := strings.Fields(line)

		states := make([]bool, 0)
		for _, s := range []rune(parts[0][1 : len(parts[0])-1]) {
			if s == '#' {
				states = append(states, true)
			} else {
				states = append(states, false)
			}
		}
		config.RequiredStates = states

		buttons := make([]Button, 0)
		for _, buttonStr := range parts[1 : len(parts)-1] {
			buttonStr = buttonStr[1 : len(buttonStr)-1]
			toggledIndicators := make([]int, 0)
			for _, toggledIndicatorStr := range strings.Split(buttonStr, ",") {
				toggledIndicator, _ := strconv.Atoi(toggledIndicatorStr)
				toggledIndicators = append(toggledIndicators, toggledIndicator)
			}
			buttons = append(buttons, Button{toggledIndicators})
		}
		config.Buttons = buttons

		joltages := make([]int, 0)
		joltagesStr := parts[len(parts)-1]
		joltagesStr = joltagesStr[1 : len(joltagesStr)-1]
		for _, joltageStr := range strings.Split(joltagesStr, ",") {
			joltage, _ := strconv.Atoi(joltageStr)
			joltages = append(joltages, joltage)
		}
		config.RequiredJoltage = joltages
		configs = append(configs, config)
	}
	return configs
}
