import sys

point_file = sys.argv[1]
step = 0.1
def generatePointsOnPlane():
    f = open(point_file,'w')
    for i in [ x / pow(step, -1) for x in range(0, 5) ]:
        for j in  [x / pow(step, -1) for x in range(0, 5)]:
            f.write('{} {}\n'.format(i , j))
    
if __name__ == "__main__":
    generatePointsOnPlane()