lines = []
with open('input', 'r') as f:
    for line in f.read().splitlines():
        if line != "noop":
            lines.append("noop2")
        lines.append(line)

x_values = [1]
for i in range(1, len(lines)):
    if lines[i] == "noop" or lines[i] == "noop2":
        x_values.append(x_values[-1])
    else:
        x_values.append(x_values[-1] + int(lines[i].split()[1]))


for i in range(0, len(lines)):
    print(i, lines[i], x_values[i])

# part 1
# x_values[i] points to the begining of the i-th phase
print(sum([i * x_values[i-2] for i in [20, 60, 100, 140, 180, 220]]))

# part 2
crt_line = "#"
for pixel in range(1, 40 * 6):
    if pixel % 40 == 0:
        print(crt_line)
        crt_line = ""
        
    x = x_values[pixel - 1]
    crt_line += "#" if abs(x - (pixel % 40)) <= 1 else "."
print(crt_line)