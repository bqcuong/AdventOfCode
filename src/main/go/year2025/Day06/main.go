package main

import (
	"strconv"

	c "github.com/bqcuong/AdventOfCode/common"
)

type equation struct {
	operator rune
	operands []int
}

type Day06 struct{}

func (d Day06) Solve(part c.Part, lines []string) int {
	var equations []*equation
	if part == c.PART1 {
		equations = d.readEquations(lines)
	} else {
		equations = d.readEquationsRtl(lines)
	}
	return d.evaluate(equations)
}

func (d Day06) evaluate(eqs []*equation) int {
	sum := 0
	for _, eq := range eqs {
		res := 0
		if eq.operator == '*' {
			res = 1
		}
		for _, o := range eq.operands {
			if eq.operator == '+' {
				res += o
			} else if eq.operator == '*' {
				res *= o
			}
		}
		sum += res
	}
	return sum
}

func (d Day06) readEquationsRtl(lines []string) []*equation {
	mat := c.ReadMatrix(lines)
	m, n := len(mat), len(mat[0])

	equations := make([]*equation, 0)
	operator := ' '
	var operands []int
	for col := 0; col < n; col++ {
		if mat[m-1][col] != ' ' {
			operator = mat[m-1][col]
			operands = make([]int, 0)
		}

		if c.IsBlankCol(mat, col) {
			equations = append(equations, &equation{
				operator: operator,
				operands: operands,
			})
		} else {
			operandStr := ""
			for row := 0; row < m-1; row++ {
				if mat[row][col] != ' ' {
					operandStr += string(mat[row][col])
				}
			}
			operand, _ := strconv.Atoi(operandStr)
			operands = append(operands, operand)
		}
	}
	equations = append(equations, &equation{
		operator: operator,
		operands: operands,
	})
	return equations
}

func (d Day06) readEquations(lines []string) []*equation {
	mat := c.ReadStrMatrix(lines)
	operators := mat[len(mat)-1]
	equations := make([]*equation, 0)

	for col := 0; col < len(mat[0]); col++ {
		eq := &equation{
			operator: []rune(operators[col])[0],
			operands: make([]int, 0),
		}
		for row := 0; row < len(mat)-1; row++ {
			operandStr := mat[row][col]
			operand, _ := strconv.Atoi(operandStr)
			eq.operands = append(eq.operands, operand)
		}
		equations = append(equations, eq)
	}
	return equations
}
