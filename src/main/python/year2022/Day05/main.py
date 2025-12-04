from curses.ascii import isalpha


lines = []
with open('input.txt', 'r') as f:
    lines = f.read().splitlines()

idx = 0
crate_lines = []
while idx < len(lines):
    if lines[idx].strip()[0] != '[':
        break
    else:
        crate_lines.append(lines[idx])
        idx += 1

num_ships = len(lines[idx].split())

crate_ship_map = {}
crate_lines.reverse()
for i in range(num_ships):
    pos = i * 4 + 1
    ship_num = lines[idx][pos]
    if ship_num not in crate_ship_map:
        crate_ship_map[ship_num] = []

    for line in crate_lines:
        if isalpha(line[pos]):
            crate_ship_map[ship_num].append(line[pos])

while idx < len(lines):
    if lines[idx].strip().startswith("move"):
        quantity = int(lines[idx].split()[1])
        from_ = lines[idx].split()[3]
        to_ = lines[idx].split()[5]
        
        moved_crates = crate_ship_map[from_][-quantity:]
        del crate_ship_map[from_][-quantity:]
        #moved_crates.reverse() # enable for part 1, disable for part 2
        crate_ship_map[to_].extend(moved_crates)
        print(crate_ship_map)

    idx += 1

for i in crate_ship_map:
    print(crate_ship_map[i][-1], end="")