package common

type Day interface {
	Solve(part Part, lines []string) int
}
