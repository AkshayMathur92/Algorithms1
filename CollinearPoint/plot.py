import matplotlib.pyplot as plt
import sys

point_file = sys.argv[1]

def plot():
    fig,ax = plt.subplots()
    x = []
    y = []
    for line in open(point_file, 'r').readlines():
        x_y = line.split()
        # x.append(int(x_y[0]))
        # y.append(int(x_y[1]))
        x = int(x_y[0])
        y = int(x_y[1])
        ax.annotate("{},{}".format(x,y), (x,y),  textcoords="offset points", xytext = (0,10), ha='center')
        ax.scatter(x,y)
    ax.set_xlabel('x-points')
    ax.set_ylabel('y-points')
    ax.set_title('Simple XY point plot')
    plt.show()

if __name__ == "__main__":
    plot()