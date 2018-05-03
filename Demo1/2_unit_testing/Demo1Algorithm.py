import json
import matplotlib.pyplot as plt

'''read the JSON file from folder'''
data = json.load(open('10000Student.json'))

RUID = [item["RUID"] for item in data if "RUID" in item]
CurrentBusStop = [item["CurrentBusStop"]
                  for item in data if "CurrentBusStop" in item]
DestinationBusStop = [item["DestinationBusStop"]
                      for item in data if "DestinationBusStop" in item]
Time = [item["Time"] for item in data if "Time" in item]

'''using sorted function to sort the time that students make the request and put them in the array'''
Time_Sorted = sorted(Time)

Time_Sorted_Split = [i.split(':') for i in Time_Sorted]

'''using get length function to get the number of items in the array so that we can calculate the number
of requests during one hour'''

total_requests = len(Time_Sorted)

'''transfer string into number lists'''
for a in range(total_requests):
    for b in range(2):
        Time_Sorted_Split[a][b] = int(Time_Sorted_Split[a][b])

y = []
x = 0
z = 0
for i in range(23):

    while Time_Sorted_Split[x][0] <= i:
        x = x + 1

    y.append(x - z)

    while Time_Sorted_Split[z][0] <= i:
        z = z + 1

y.append(len(Time_Sorted) - x)
'''This is the core algorithm for this part, we get the number of requests and we will compare it
with the current Rutgers bus capacity (information provided by department of transportation supervisor)'''
A = 0
B = []
for index in range(len(y)):
    if y[index] < 0.3 * 840:
        A = 30
    if 0.3 * 840 <= y[index] < 0.4 * 840:
        A = 15
    if 0.4 * 840 <= y[index] < 0.5 * 840:
        A = 10
    if 0.5 * 840 <= y[index] < 0.6 * 840:
        A = 9
    if 0.6 * 840 <= y[index] < 0.7 * 840:
        A = 8
    if 0.7 * 840 <= y[index] < 0.8 * 840:
        A = 7
    if 0.8 * 840 <= y[index] < 0.9 * 840:
        A = 6
    if 0.9 * 840 <= y[index]:
        A = 5
    B.append(A)

# get the hour range which is 1 hour in this case

    print "Time hour from:", index, ': 00', ' to ', index, ': 59'

# get the bus dispatch rate from the B[index] we defined previously, to get the B[index]
# we use get length function

    print "Arranged bus dispatch rate : ", 'Every', B[index], 'minutes' "\n"

'''plot a bar chart using matplotlibrary'''

C = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
     13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]

plt.bar(C, B, alpha=0.9, width=1, facecolor='lightskyblue',
        edgecolor='white', label='one', lw=1)
'''save the image as png format in the content folder'''
plt.savefig('Requests_Of_10000Students.png')

plt.show()
