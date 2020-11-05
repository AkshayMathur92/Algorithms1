import matplotlib.pyplot as plt
import matplotlib.patches as patches
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
        x = float(x_y[0])
        y = float(x_y[1])
        ax.annotate("{},{}".format(x,y), (x,y),  textcoords="offset points", xytext = (0,10), ha='center')
        ax.scatter(x,y)
    ax.set_xlabel('x-points')
    ax.set_ylabel('y-points')
    ax.set_title('Simple XY point plot')
    ax.add_patch(patches.Rectangle((0.25,0.75),0.5 - 0.25,1.0 - 0.75,linewidth=1,edgecolor='r',facecolor='none'))
    plt.show()

if __name__ == "__main__":
    plot()