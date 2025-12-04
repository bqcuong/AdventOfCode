import operator
from math import sqrt

lines = []
with open('input.txt', 'r') as f:
    lines = f.read().splitlines()

h_moves = []
for line in lines:
    parts = line.split()
    if parts[0] == "L":
        h_moves.extend(int(parts[1]) * [[0, -1]])
    elif parts[0] == "R":
        h_moves.extend(int(parts[1]) * [[0, 1]])
    elif parts[0] == "U":
        h_moves.extend(int(parts[1]) * [[1, 0]])
    elif parts[0] == "D":
        h_moves.extend(int(parts[1]) * [[-1, 0]])

def distance(pos_1, pos_2):
    return sqrt((pos_1[0]-pos_2[0])**2 + (pos_1[1]-pos_2[1])**2)

def move(pos, step):
    return tuple(map(operator.add, pos, step))

def update_knot_by_prev(prev, knot_pos):
    if distance(prev, knot_pos) > 1.5:
        t_i = knot_pos[0] + (1 if prev[0] > knot_pos[0] else -1 if prev[0] < knot_pos[0] else 0)
        t_j = knot_pos[1] + (1 if prev[1] > knot_pos[1] else -1 if prev[1] < knot_pos[1] else 0)
        return (t_i, t_j)
        
    return knot_pos

def move_knots(num_of_knots, h_moves):
    knot_pos = num_of_knots * [(0, 0)]    
    t_visited_pos = [knot_pos[-1]]

    for h_move in h_moves:
        knot_pos[0] = move(knot_pos[0], h_move)
        for i in range(1, num_of_knots):
            knot_pos[i] = update_knot_by_prev(knot_pos[i-1], knot_pos[i])
        t_visited_pos.append(knot_pos[-1])

    return t_visited_pos

# part 1
print(len(set(move_knots(2, h_moves))))

# part 2
print(len(set(move_knots(10, h_moves))))