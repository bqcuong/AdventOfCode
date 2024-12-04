mat = []
with open('input', 'r') as f:
    mat = [[c for c in line] for line in f.read().splitlines()]

def find(to_find, mat):
    for i in range(0, len(mat)):
        for j in range(0, len(mat[i])):
            if mat[i][j] == to_find:
                return i, j

def can_move(i, j, m, n, mat):
    if mat[m][n][0] == "-":
        return False

    if mat[i][j] == "S":
        return True

    if mat[m][n] == "E":
        return True

    if mat[m][n] == "S":
        return False

    return ord(mat[i][j]) + 1 >= ord(mat[m][n])

global solutions
def find_way(pos, to_find, mat, step):
    i = pos[0]
    j = pos[1]
    if mat[i][j] == to_find:
        print(pos, len(step), step)
        solutions.append(len(step) + 1)

    #for line in mat:
    #    print(*line)

    # check left
    if j > 0 and can_move(i, j, i, j-1, mat):
        mat[i][j] = "-" + mat[i][j]
        step.append(mat[i][j])
        find_way((i,j-1), to_find, mat, step)
        mat[i][j] = mat[i][j][-1]
        step.pop()

    # check right
    if j + 1 < len(mat[0]) and can_move(i, j, i, j+1, mat):
        mat[i][j] = "-" + mat[i][j]
        step.append(mat[i][j])
        find_way((i,j+1), to_find, mat, step)
        mat[i][j] = mat[i][j][-1]
        step.pop()

    # check up
    if i > 0 and can_move(i, j, i-1, j, mat):
        mat[i][j] = "-" + mat[i][j]
        step.append(mat[i][j])
        find_way((i-1,j), to_find, mat, step)
        mat[i][j] = mat[i][j][-1]
        step.pop()

    # down down
    if i + 1 < len(mat) and can_move(i, j, i+1, j, mat):
        mat[i][j] = "-" + mat[i][j]
        step.append(mat[i][j])
        find_way((i+1,j), to_find, mat, step)
        mat[i][j] = mat[i][j][-1]
        step.pop()


start_pos = find("S", mat)
solutions = []
find_way(start_pos, "z", mat, [])
solutions.sort()
print(solutions)