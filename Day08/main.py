forest = []
with open('input', 'r') as f:
    forest = [[int(num) for num in line.strip()] for line in f]

m = len(forest)
n = len(forest[0])

def check_visible(i, j, mat):
    height = mat[i][j]

    up = [x for x in range(0, i) if mat[x][j] >= height]
    down = [x for x in range(i+1, m) if mat[x][j] >= height]
    left = [x for x in range(0, j) if mat[i][x] >= height]
    right = [x for x in range(j+1, n) if mat[i][x] >= height]
    return len(up) == 0 or len(down) == 0 or len(left) == 0 or len(right) == 0

# part 1
print(len([(i, j) for i in range(0, m) for j in range(0, n) if check_visible(i, j, forest)]))
