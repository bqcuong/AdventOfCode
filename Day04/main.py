lines = []
with open('input', 'r') as f:
    lines = f.read().splitlines()

def is_fully_contained(seg_a, seg_b):
    return seg_a[0] <= seg_b[0] and seg_a[1] >= seg_b[1]

def is_overlapped(seg_a, seg_b):
    return seg_a[0] <= seg_b[1] and seg_a[0] >= seg_b[0]

count = 0
count_2 = 0
for line in lines:
    segs = line.split(',')
    segs_1 = segs[0].split('-')
    segs_2 = segs[1].split('-')
    seg_1 = int(segs_1[0]), int(segs_1[1])
    seg_2 = int(segs_2[0]), int(segs_2[1])
    if is_fully_contained(seg_1, seg_2) or is_fully_contained(seg_2, seg_1):
        count += 1

    if is_overlapped(seg_1, seg_2) or is_overlapped(seg_2, seg_1):
        count_2 += 1

# part 1
print(count)

# part 2
print(count_2)