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
    return strings.Split(strings.TrimSpace(string(data)), "\n"), nil

}

func readMatrix(lines []string) [][]rune {
    mat := make([][]rune, len(lines))
    for i, line := range lines {
        mat[i] = []rune(line)
    }
    return mat
}

type Pos struct {
    i int
    j int
}

func isAccessibleRoll(mat [][]rune, i int, j int) bool {
    m, n := len(mat), len(mat[0])
    check := 0
    for di := -1; di <= 1; di++ {
        for dj := -1; dj <= 1; dj++ {
            if di == 0 && dj == 0 { continue }
            if i+di >= 0 && i+di < m && j+dj >= 0 && j+dj < n && mat[i+di][j+dj] == '@' {
                check++
            }
        }
    }
    return check < 4
}

func getAccessibleRolls(mat [][]rune) []Pos {
    m, n := len(mat), len(mat[0])
    positions := make([]Pos, 0)
    for i := 0; i < m; i++ {
        for j := 0; j < n; j++ {
            if mat[i][j] != '@' { continue }
            if isAccessibleRoll(mat, i, j) {
                positions = append(positions, Pos{i: i, j: j})
            }
        }
    }
    return positions
}

func countRemovableRolls(mat [][]rune) int {
    count := 0
    for {
        accessibleRolls := getAccessibleRolls(mat)
        if len(accessibleRolls) == 0 { break }
        count += len(accessibleRolls)
        for _, roll := range accessibleRolls {
            mat[roll.i][roll.j] = 'x'
        }
    }
    return count
}

func solve(part Part, lines []string) int {
    mat := readMatrix(lines)
    if part == PART1 {
        return len(getAccessibleRolls(mat))
    }
    return countRemovableRolls(mat)
}

func main() {
    //lines, err := readTextFile("sample_input.txt")
    lines, err := readTextFile("input.txt")
    if err != nil {
        log.Fatal(err)
    }

    fmt.Println(solve(PART1, lines))
    fmt.Println(solve(PART2, lines))
}
