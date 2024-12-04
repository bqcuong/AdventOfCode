lines = []
with open('input', 'r') as f:
    lines = f.read().splitlines()

my_score = 0
my_score_new = 0
for line in lines:
    weapons = line.split()
    weapons[0] = ord(weapons[0]) - ord("A") + 1
    weapons[1] = ord(weapons[1]) - ord("X") + 1
    
    # part 1
    my_score += weapons[1]
    if weapons[0] != weapons[1]:
        if weapons[1] - weapons[0] == 1 or weapons[1] - weapons[0] == -2:
            my_score += 6
    else:
        my_score += 3

    # part 2
    if weapons[1] == 1: # i lose
        my_score_new +=  weapons[0] - 1 if weapons[0] > 1 else 3
    elif weapons[1] == 2: # we draw
        my_score_new += 3 + weapons[0]
    elif weapons[1] == 3: # i win
        print(line)
        my_score_new += 6 + (weapons[0] + 1 if weapons[0] < 3 else 1)

# part 1
print(my_score)

# part 2
print(my_score_new)