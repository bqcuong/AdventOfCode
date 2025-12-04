lines = []
with open('input.txt', 'r') as f:
    lines = f.read().splitlines()

def build_file_tree(lines):
    root_node = {}
    current_node = None
    for line in lines:
        if line == "$ cd /":
            current_node = { 'name': '/', 'children': [], 'parent': None, 'size': -1}
            root_node = current_node
        elif line == "$ cd ..":
            current_node = current_node['parent']
        elif line.startswith("$ cd"):
            new_node = { 'name': line.split()[-1], 'children': [], 'parent': current_node, 'size': -1}
            current_node['children'].append(new_node)
            current_node = new_node
        elif line[0].isdigit():
            new_file_node = { 'name': line.split()[-1], 'children': [], 'parent': current_node, 'size': int(line.split()[0])}
            current_node['children'].append(new_file_node)
    
    return root_node

def calculate_size(node, dir_sizes):
    if node['size'] > -1:
        return node['size']

    size = 0
    for child in node['children']:
        size += calculate_size(child, dir_sizes)

    if node['size'] == -1:
        dir_sizes.append(size)

    return size

root_node = build_file_tree(lines)

dir_sizes = []
root_size = calculate_size(root_node, dir_sizes)

# part 1
print(sum(x for x in dir_sizes if x < 100000))

freeup_space = 30000000 - (70000000 - root_size)
dir_sizes = sorted(dir_sizes)
print(dir_sizes)

# part 2
print(next(s for s in dir_sizes if s >= freeup_space))