lines = []
with open('input.txt', 'r') as f:
    lines = f.read().splitlines()

line = lines[0]

# part 1
for i in range(3, len(line)):
    if len(line[i-3:i+1]) == len(set(line[i-3:i+1])):
        print(line[i-3:i+1])
        print(i + 1)
        break

# part 2
for i in range(13, len(line)):
    if len(line[i-13:i+1]) == len(set(line[i-13:i+1])):
        print(line[i-13:i+1])
        print(i + 1)
        break