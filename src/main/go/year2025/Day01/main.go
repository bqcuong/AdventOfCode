package main

import (
    "fmt"
    "log"
    "os"
    "strconv"
    "strings"
)

func readTextFile(path string) ([]string, error) {
    data, err := os.ReadFile(path)
    if err != nil {
        return nil, err
    }
    return strings.Split(string(data), "\n"), nil
}

func main() {
    //lines, err := readTextFile("sample_input.txt")
    lines, err := readTextFile("input.txt")
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
