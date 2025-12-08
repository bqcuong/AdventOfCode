package main

import (
	"slices"
	"strconv"
	"strings"

	c "github.com/bqcuong/AdventOfCode/common"
)

type Day08 struct{}

type Connection struct {
	p1       c.Pos3D
	p2       c.Pos3D
	distance float64
}

type CircuitUnionFind struct {
	parent map[c.Pos3D]c.Pos3D
}

func NewCircuitUnionFind() *CircuitUnionFind {
	return &CircuitUnionFind{parent: make(map[c.Pos3D]c.Pos3D)}
}

func (cuf *CircuitUnionFind) Find(p c.Pos3D) c.Pos3D {
	if _, ok := cuf.parent[p]; !ok { // not initialized
		cuf.parent[p] = p // set representative as itself
		return p
	}
	current := p
	// keep looking until the representative of current is itself
	for cuf.parent[current] != current {
		current = cuf.parent[current]
	}
	return current
}

func (cuf *CircuitUnionFind) Union(p1, p2 c.Pos3D) {
	rep1 := cuf.Find(p1)
	rep2 := cuf.Find(p2)
	if rep1 != rep2 {
		cuf.parent[rep1] = rep2
	}
}

func (cuf *CircuitUnionFind) IsConnected(p1, p2 c.Pos3D) bool {
	return cuf.Find(p1) == cuf.Find(p2)
}

func (cuf *CircuitUnionFind) GetCircuitSizes() map[c.Pos3D]int {
	sizes := make(map[c.Pos3D]int)
	for pos := range cuf.parent {
		root := cuf.Find(pos)
		sizes[root]++
	}
	return sizes
}

func (d Day08) Solve(part c.Part, lines []string) int {
	boxes := d.ReadJunctionBoxPositions(lines)
	connections := d.GetConnectionsSortedByDistance(boxes)
	return d.ConnectBoxes(boxes, connections)
}

func (d Day08) ConnectBoxes(boxes []c.Pos3D, connections []Connection) int {
	maxPairs := 1000
	if len(boxes) <= 20 { // for sample input
		maxPairs = 10
	}
	connections = connections[:maxPairs]
	cuf := NewCircuitUnionFind()
	for _, con := range connections {
		p1 := con.p1
		p2 := con.p2
		cuf.Union(p1, p2)
	}
	setSizes := cuf.GetCircuitSizes()
	sizes := make([]int, 0, len(setSizes))
	for _, size := range setSizes {
		sizes = append(sizes, size)
	}
	slices.SortFunc(sizes, func(a, b int) int {
		return b - a
	})
	return sizes[0] * sizes[1] * sizes[2]
}

func (d Day08) GetConnectionsSortedByDistance(boxes []c.Pos3D) []Connection {
	connections := make([]Connection, 0)
	for i := 0; i < len(boxes); i++ {
		for j := i + 1; j < len(boxes); j++ {
			connections = append(connections, Connection{
				p1:       boxes[i],
				p2:       boxes[j],
				distance: c.EuclideanDistance(boxes[i], boxes[j]),
			})
		}
	}
	slices.SortFunc(connections, func(c1, c2 Connection) int {
		diff := c1.distance - c2.distance
		if diff > 0 {
			return 1
		} else if diff < 0 {
			return -1
		}
		return 0
	})
	return connections
}

func (d Day08) ReadJunctionBoxPositions(lines []string) []c.Pos3D {
	boxes := make([]c.Pos3D, 0)
	for _, line := range lines {
		parts := strings.Split(line, ",")
		x, _ := strconv.Atoi(parts[0])
		y, _ := strconv.Atoi(parts[1])
		z, _ := strconv.Atoi(parts[2])
		boxes = append(boxes, c.Pos3D{X: x, Y: y, Z: z})
	}
	return boxes
}
