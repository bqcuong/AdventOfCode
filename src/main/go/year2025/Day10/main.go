package main

import (
	"container/list"
	"fmt"
	"slices"
	"strconv"
	"strings"

	"github.com/aclements/go-z3/z3"
	c "github.com/bqcuong/AdventOfCode/common"
)

type Day10 struct{}

type Button struct {
	ToggledIndicators []int
}

type Config struct {
	States          []bool
	FinalStates     []bool
	Buttons         []Button
	RequiredJoltage []int
}

type QueueElement struct {
	States []bool
	depth  int
}

func (d Day10) Solve(part c.Part, lines []string) int {
	configs := d.ReadConfigs(lines)
	sum := 0
	for _, config := range configs {
		if part == c.PART1 {
			sum += d.countButtonPressesForStates(config)
		} else {
			sum += d.countButtonPressesForJoltages(config)
		}
	}
	return sum
}

func (d Day10) countButtonPressesForJoltages(config Config) int {
	ctx := z3.NewContext(nil)
	solver := z3.NewSolver(ctx)

	z3FromInt := func(a int) z3.Int {
		return ctx.FromInt(int64(a), ctx.IntSort()).(z3.Int)
	}

	z3ToInt := func(model *z3.Model, a z3.Int) int {
		intVal, _, _ := model.Eval(a, true).(z3.Int).AsInt64()
		return int(intVal)
	}

	z3Zero := z3FromInt(0)
	z3Buttons := make([]z3.Int, len(config.Buttons))
	for i := range len(config.Buttons) {
		z3Buttons[i] = ctx.IntConst(fmt.Sprintf("btn_%d", i))
		solver.Assert(z3Buttons[i].GE(z3Zero))
	}

	for indicatorIdx, joltageGoal := range config.RequiredJoltage {
		var z3Operands = make([]z3.Int, 0)
		for i, btn := range config.Buttons {
			if slices.Contains(btn.ToggledIndicators, indicatorIdx) {
				z3Operands = append(z3Operands, z3Buttons[i])
			}
		}

		z3Sum := z3Operands[0]
		for _, z3Operand := range z3Operands[1:] {
			z3Sum = z3Sum.Add(z3Operand)
		}
		solver.Assert(z3Sum.Eq(z3FromInt(joltageGoal)))
	}

	z3TotalPresses := ctx.IntConst("totalPresses")
	z3BtnPresses := z3Buttons[0]
	for _, z3Btn := range z3Buttons[1:] {
		z3BtnPresses = z3BtnPresses.Add(z3Btn)
	}
	solver.Assert(z3TotalPresses.Eq(z3BtnPresses))

	// try to find the most optimal solution
	minTotalPresses := 0
	for {
		if ok, _ := solver.Check(); !ok {
			break
		} else {
			model := solver.Model()
			minTotalPresses = 0
			for i := range len(config.Buttons) {
				buttonPressCount := z3ToInt(model, z3Buttons[i])
				minTotalPresses += buttonPressCount
			}
			solver.Assert(z3TotalPresses.LT(z3FromInt(minTotalPresses)))
		}
	}
	return minTotalPresses
}

func (d Day10) countButtonPressesForStates(config Config) int {
	queue := list.New()
	queue.PushBack(QueueElement{config.States, 0})

	visitedStates := make(map[string]bool)
	visitedStates[fmt.Sprintf("%v", config.States)] = true

	// implementation of BFS with queue
	for queue.Len() > 0 {
		current := queue.Front().Value.(QueueElement)
		queue.Remove(queue.Front())

		if slices.Equal(current.States, config.FinalStates) {
			return current.depth
		}
		for _, button := range config.Buttons {
			newStates := make([]bool, len(current.States))
			copy(newStates, current.States)
			for _, toggledIndicator := range button.ToggledIndicators {
				newStates[toggledIndicator] = !newStates[toggledIndicator]
			}

			stateStr := fmt.Sprintf("%v", newStates)
			if _, ok := visitedStates[stateStr]; !ok {
				visitedStates[stateStr] = true
				queue.PushBack(QueueElement{newStates, current.depth + 1})
			}
		}
	}
	return -1
}

func (d Day10) ReadConfigs(lines []string) []Config {
	configs := make([]Config, 0)
	for _, line := range lines {
		parts := strings.Fields(line)

		finalStates := make([]bool, 0)
		for _, s := range []rune(parts[0][1 : len(parts[0])-1]) {
			if s == '#' {
				finalStates = append(finalStates, true)
			} else {
				finalStates = append(finalStates, false)
			}
		}
		config := Config{
			States:      make([]bool, len(finalStates)),
			FinalStates: finalStates,
		}

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
