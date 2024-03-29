import random
list1=[]
list2=[]
list3=[]
list4=[]
max_speed=[]
account=0
count=0
def getsuiji(max):
    list1=[]
    count=0
    i=0
    while count<max:
        num=random.randint(1,max)
        while num not in list1:
            count+=1
            list1.append(num)
    return list1
list1=getsuiji(64)
for i in range(8):
    list2.append(list1[i * 8:i * 8 + 8])
    list3.append(list2[i][list2[i].index(max(list2[i]))])
    account += 1
print("------------------------------------------------")
print(f"各车{list1}")
print("------------------------------------------------")
print(f"分组{list2}")
print("------------------------------------------------")
print(f"各组第一{list3}")
max_speed.append(max(list3))
print("------------------------------------------------")
print(f"各组第一{list3}\n速度最快的:{max_speed}")
print("------------------------------------------------")
list3=sorted(list3)
account+=1
for i in range(4):
    list3.remove(min(list3))
print(f"各组第一比赛后前四名:{list3}")
for i in range(4):
    for j in range(8):
        if list3[i] in list2[j]:
            list4.append(j)
            list2[j]=sorted(list2[j])
    for j in range(i):
        list3.append(list2[list4[i]][7-j-1])
print("------------------------------------------------")
print(f"加入可能的前四名后{list3}")
print(f"输出小组赛前四名的组{list4}")
list5=list3[:len(list3)-1]
list5=sorted(list5)
list5.pop(len(list5)-1)
print("------------------------------------------------")
print(f"去掉最快的车所在小组的第四名后:{list5}")
count=0
for i in range(3):
    if list5[len(list5)-i-1] in list2[list4[3]]:
        count+=1
if count<2:
    account+=1
    for i in range(3):
        max_speed.append(list5[len(list5)-i-1])
else:
    account+=2
    for i in range(2):
        max_speed.append(list5[len(list5)-1])
        list5.pop(len(list5)-1)
    list5.append(list3[len(list3)-1])
    list5=sorted(list5)
    print(list5)
    max_speed.append(list5[len(list5)-1])
print(f"一共比赛了{account}轮")
for i in range(4):
    print("------------------------------------------------")
    print(f"速度第{i+1}快的是第{list1.index(max_speed[i])+1}辆车,速度为{max_speed[i]}")