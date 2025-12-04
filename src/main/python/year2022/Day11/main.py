from math import prod


monkeys = []
with open('input.txt', 'r') as f:
    lines = f.read().splitlines()
    for i in range(0, len(lines) - 7 + 2, 7):
        items = list(map(lambda x: x.strip(), lines[i+1].split(":")[1].split(",")))
        op = lines[i+2].split()[-2]
        op_arg = lines[i+2].split()[-1]
        test = lines[i+3].split()[-1]
        if_true = lines[i+4].split()[-1]
        if_false = lines[i+5].split()[-1]
        monkey = {
            'items': items, 'op': op, 'op_arg': op_arg,
            'test': test, 'if_true': if_true, 'if_false': if_false, 'inspection': 0}
        monkeys.append(monkey)

mod = prod([int(m["test"]) for m in monkeys])
for round in range(1, 10001): # 21 for part 1, 100001 for part 2
    for monkey in monkeys:
        for item in monkey["items"]:
            monkey['inspection'] += 1
            op_arg = monkey['op_arg']
            if not op_arg.isdigit():
                op_arg = item

            item = int(item) % mod
            worriness = eval(f"{item} {monkey['op']} {op_arg}")
            #worriness //= 3 # enable for part 1, disable for part 2s

            test = worriness % int(monkey["test"]) == 0
            if test:
                monkeys[int(monkey["if_true"])]["items"].append(worriness)
            else:
                monkeys[int(monkey["if_false"])]["items"].append(worriness)

        monkey["items"] = []

# part 1 and part 2
inspections = sorted(list(map(lambda x: x["inspection"], monkeys)), reverse=True)
print(inspections)
print(inspections[0] * inspections[1])
