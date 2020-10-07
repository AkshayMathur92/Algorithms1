import sys

point_file = sys.argv[1]
n = int(sys.argv[2])

def generatePointsOnPlane():
    f = open(point_file,'w')
    f.write('{}\n'.format(n * n ))
    for i in range(n):
        for j in range(n):
            f.write('{} {}\n'.format(i , j))
    

if __name__ == "__main__":
    generatePointsOnPlane()