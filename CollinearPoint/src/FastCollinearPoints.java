import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> segmentList = new LinkedList<>();
    public FastCollinearPoints( Point[] points) {
        //check for null array
        if(points == null) {
            throw new IllegalArgumentException();
        }
        //check for null points
        for(Point check : points) {
            if (check == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] sortedPoints = points.clone();
        Point[] aux = new Point[sortedPoints.length - 1];
        //check repeated points
        Arrays.sort(sortedPoints, Comparator.naturalOrder());
        for(int i = 1; i < sortedPoints.length; i ++) {
            if (sortedPoints[i].compareTo(sortedPoints[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        //check if points are less than 4
        if(points.length < 4){
            return;
        }
        //for each point p
        for(int p = 0; p < sortedPoints.length; p++){
            Point origin = sortedPoints[p];
            System.arraycopy(sortedPoints,0,aux,0,p);
            System.arraycopy(sortedPoints,p+1,aux,p,aux.length - p);
            Arrays.sort(aux, origin.slopeOrder());
            for(int q= 0; q < aux.length - 2; q++){
                double slopeTosearch = origin.slopeTo(aux[q]);
                if(aux[q].slopeTo(aux[q + 2]) == slopeTosearch){
                    int upper_bound = uppperBound(aux,q + 1, aux.length, slopeTosearch, origin);
                    //found a segment
                    //check if subsegment
                    if(origin.compareTo(aux[q]) < 0 && upper_bound - q >=3){
                        segmentList.add(new LineSegment(origin, aux[upper_bound - 1]));
                    }
                    q = upper_bound - 1;
                }
            }
        }
    }

    private int uppperBound(Point[] arr, int first, int last, double slopeTosearch, Point origin){
        if(first >= last) return last;
        int count = last - first ;
        int step =0, it = 0;
        while (count>0)
        {
            it = first; step=count/2;
            it = it + step;
            if (origin.slopeTo(arr[it]) <= slopeTosearch) {                 // or: if (comp(*it,val)), for version (2)
                first=++it;
                count-=step+1;
            }
            else count=step;
        }
        return first;
    }

    public int numberOfSegments(){
        return segmentList.size();
    }

    public LineSegment[] segments(){
        LineSegment[] returnData = new LineSegment[segmentList.size()];
        segmentList.toArray(returnData);
        return returnData;
    }
}
