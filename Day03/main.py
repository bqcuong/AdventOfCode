lines = []
with open('input', 'r') as f:
    lines = f.read().splitlines()

def get_duplicates(list1, list2):
    return list(set(list1).intersection(list2))

def to_priority(input):
    return list(map(lambda c: ord(c) - 97 + 1 if ord(c) >= 97 else ord(c) - 65 + 27, input))

duplicated_items = []
duplicated_items_2 = []
for i in range (0, len(lines)):
    line = lines[i]
    nums = to_priority(line)

    duplicates = get_duplicates(nums[:len(nums) // 2], nums[len(nums) // 2:])
    duplicated_items.extend(duplicates)

    if i % 3 == 0 and i <= len(lines) - 3:
        duplicates = get_duplicates(get_duplicates(nums, to_priority(lines[i+1])), to_priority(lines[i+2]))
        duplicated_items_2.extend(duplicates)

# part 1
print(sum(duplicated_items))

# part 2
print(sum(duplicated_items_2))
