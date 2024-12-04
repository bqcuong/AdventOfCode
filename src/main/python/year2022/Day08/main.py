forest = []
with open('input', 'r') as f:
    forest = [[int(num) for num in line.strip()] for line in f]

m = len(forest)
n = len(forest[0])

def check_visible(i, j, mat):
    height = mat[i][j]

    up = [x for x in range(i, -1, -1) if mat[x][j] < height]
    down = [x for x in range(i+1, m) if mat[x][j] < height]
    left = [x for x in range(j, -1, -1) if mat[i][x] < height]
    right = [x for x in range(j+1, n) if mat[i][x] < height]
    return len(up) == i or len(down) == (m-i-1) or len(left) == j or len(right) == (n-j-1)

def calculate_scenic_score(i, j, mat):
    height = mat[i][j]
    scenic_score_l = 0
    scenic_score_r = 0
    scenic_score_u = 0
    scenic_score_d = 0
    for x in range(i-1, -1, -1):
        scenic_score_u += 1
        if mat[x][j] >= height:
            break

    for x in range(i+1, m):
        scenic_score_d += 1
        if mat[x][j] >= height:
            break

    for x in range(j-1, -1, -1):
        scenic_score_l += 1
        if mat[i][x] >= height:
            break

    for x in range(j+1, n):
        scenic_score_r += 1
        if mat[i][x] >= height:
            break
    
    return scenic_score_l * scenic_score_r * scenic_score_u * scenic_score_d

# part 1
visible_trees = [(i, j) for i in range(0, m) for j in range(0, n) if check_visible(i, j, forest)]
print(len(visible_trees))

# part 2
scenic_scores = [calculate_scenic_score(i, j, forest) for i in range(0, m) for j in range(0, n)]
print(scenic_scores)
print(max(scenic_scores))
