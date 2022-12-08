lines = []
with open('input', 'r') as f:
    lines = f.readlines()

elves = []
sum_star = 0
max_star = 0
for line in lines:
    if line == "\n":
        elves.append(sum_star)
        sum_star = 0
    else:
        sum_star = sum_star + int(line.strip())

elves = sorted(elves, reverse=True)

# part 1
print(elves[0])

# part 2
print(sum(elves[0:3]))
    