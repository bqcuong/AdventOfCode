package main

import (
    "container/list"
    "fmt"
    "slices"
    "strconv"
    "strings"

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

type QueueElement struct {
    States []bool
    depth  int
}

func (d Day10) countButtonPressesForJoltages(config Config) int {
    return 0
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
