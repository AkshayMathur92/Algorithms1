import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private int nSegments;
    private LineSegment[] segments;

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
        //check if points are less than 4
        if(points.length < 4){
            nSegments = 0;
            segments = new LineSegment[0];
        }
        final int MAXSEGEMENTS = (points.length * points.length) ;
        Mlinesegment[] lineSegments = new Mlinesegment[MAXSEGEMENTS];
        Point[] sortedPoints = points.clone();
        Point[] aux = new Point[sortedPoints.length];
        Point[] auxn = new Point[sortedPoints.length];
        //check repeated points
        Arrays.sort(sortedPoints, Comparator.naturalOrder());
        for(int i = 0; i < sortedPoints.length; i ++) {
            if (i > 0 && sortedPoints[i].compareTo(sortedPoints[i - 1]) == 0)
                throw new IllegalArgumentException();
        }
        //for each point p
        for(int p = 0; p < sortedPoints.length - 2; p++){
            System.arraycopy(sortedPoints,p + 1, auxn, p + 1, sortedPoints.length - (p + 1));
            Point origin = sortedPoints[p];
            lineSegments = mergeSortAndGet3PointsCollinear(auxn, p + 1, auxn.length - 1,origin.slopeOrder(),aux, sortedPoints.length - (p + 1), lineSegments, nSegments, origin);
            Arrays.sort(lineSegments,0,nSegments ,Comparator.naturalOrder());
        }
        segments = new LineSegment[nSegments];
        int i = 0;
        while(i < nSegments){
            segments[i ] = new LineSegment(lineSegments[i].a, lineSegments[i].b);
            i++;
        }
    }

    public int numberOfSegments(){
        return nSegments;
    }

    public LineSegment[] segments(){
        return segments.clone();
    }

    private boolean binarySearch(Mlinesegment[] lineSegments, int first, int last, Point aux) {
        if(first > last) return false;
        if(first == last) return lineSegments[first].b == aux;
        while(first <= last){
            int m = first + (last - first) / 2;
            if(lineSegments[m].b == aux) return true;
            else if (lineSegments[m].b.compareTo(aux) < 0){
                first = m + 1;
            }else{
                last = m - 1;
            }
        }
       return false;
    }

    private int uppperBound(Mlinesegment[] arr, int first, int last, double key) {
        if(first >= last) return last;
        int count = last - first ;
        int step =0, it = 0;
        while (count>0)
        {
            it = first; step=count/2;
            it = it + step;
            if (arr[it].a.slopeTo(arr[it].b) <= key) {                 // or: if (comp(*it,val)), for version (2)
                first=++it;
                count-=step+1;
            }
            else count=step;
        }
        return first;
    }

    private int lowerBound(Mlinesegment[] arr, int first, int last, double key){
        if(first >= last) return -1;
        int count = last - first ;
        int step =0, it = 0;
        while (count>0) {
            it = first;
            step = count / 2;
            it = it + step;
            if (arr[it].a.slopeTo(arr[it].b) < key) {                 // or: if (comp(*it,val)), for version (2)
                first = ++it;
                count -= step + 1;
            } else count = step;
        }
        if(first == last || arr[first] == null || arr[first].a.slopeTo(arr[first].b) != key)
            return -1;
        return first;
    }

    private static class Mlinesegment implements Comparable<Mlinesegment>{

        Point a, b;
        public Mlinesegment(Point a, Point b){
            this.a = a;
            this.b = b;
        }
        public int compareTo(Mlinesegment mlinesegment) {
            double currentslope = a.slopeTo(b);
            double newslope = mlinesegment.a.slopeTo(mlinesegment.b);
            if(currentslope < newslope) return - 1;
            if(currentslope > newslope) return + 1;
            return b.compareTo(mlinesegment.b);
        }

        @Override
        public boolean equals(Object obj) {
            if(! (obj instanceof Mlinesegment)) return false;
            Mlinesegment mlinesegment = (Mlinesegment)obj;
            if(a.slopeTo(b) == mlinesegment.a.slopeTo(mlinesegment.b)){
                return a == mlinesegment.a || b == mlinesegment.b || a == mlinesegment.b || b == mlinesegment.a;
            }
            return false;
        }
    }
    private Mlinesegment[] addSegment(Mlinesegment[] lineSegments, Point point1, Point origin, int currentNumOfSegments) {
        Point point = point1;
        int lowerBoundSlope = lowerBound(lineSegments, 0, currentNumOfSegments, origin.slopeTo(point));
        if(lowerBoundSlope >= 0){
            int upperBoundSlope = uppperBound(lineSegments, lowerBoundSlope, currentNumOfSegments ,origin.slopeTo(point));
            //between same slope binary search endOfSegmentPoint
            boolean found = binarySearch(lineSegments, lowerBoundSlope, upperBoundSlope - 1, point);
            if(!found){
                lineSegments  = addNewSegment(lineSegments, origin, point);
            }
        }
        else{
            lineSegments = addNewSegment(lineSegments, origin, point);
        }
        return lineSegments;
    }

    private Mlinesegment[] addNewSegment(Mlinesegment[] lineSegments, Point origin, Point point) {
        if(nSegments >= lineSegments.length){
            Mlinesegment[] newIncreasedLength = new Mlinesegment[lineSegments.length * 2];
            System.arraycopy(lineSegments,0,newIncreasedLength,0,lineSegments.length);
            lineSegments = newIncreasedLength;
        }
        lineSegments[nSegments] = new Mlinesegment(origin, point);
        nSegments++;
        return lineSegments;
    }

    private Mlinesegment[] mergeSortAndGet3PointsCollinear(Point[] sortedPoints, int p, int length, Comparator<Point> slopeOrder, Point[] aux, int total, Mlinesegment[] mlinesegments, int totalSegments, Point origin) {
        if(p >= length) return null;
        int mid = (length -p) / 2 + p;
        mergeSortAndGet3PointsCollinear(sortedPoints,p,mid,slopeOrder,aux,  total,mlinesegments, totalSegments, origin);
        mergeSortAndGet3PointsCollinear(sortedPoints,mid + 1,length,slopeOrder,aux,  total, mlinesegments, totalSegments, origin);
        return mergeAndSquash(p,mid,length, sortedPoints, aux,slopeOrder, total, mlinesegments, totalSegments, origin);
    }

    private Mlinesegment[] mergeAndSquash(int p, int mid, int length, Point[] sortedPoints, Point[] aux, Comparator<Point> slopeOrder,  int total , Mlinesegment[] mlinesegments, int totalSegments, Point origin){
        int totalMergePoints = length - p  + 1;
       if(totalMergePoints != total){
           int writePtr = p;
           int itr1 = p;
           int itr2 = mid + 1;
           while(itr1 <= mid && itr2 <= length){
               if(slopeOrder.compare(sortedPoints[itr1], sortedPoints[itr2]) <= 0){
                   aux[writePtr] = sortedPoints[itr1];
                   itr1++;
               }else{
                   aux[writePtr] = sortedPoints[itr2];
                   itr2++;
               }
               writePtr++;
           }
           while(itr1 <= mid){
               aux[writePtr] = sortedPoints[itr1];
               itr1++;
               writePtr++;
           }
           while(itr2 <= length){
               aux[writePtr] = sortedPoints[itr2];
               itr2++;
               writePtr++;
           }
           System.arraycopy(aux,p,sortedPoints,p,totalMergePoints);
       }
       else {
           int writePtr = p;
           int itr1 = p;
           int itr2 = mid + 1;
           int squashthreshold = 2;
           Point last_point = null;
           int lastRepeatedCount = 0;
           int slopeCompared = slopeOrder.compare(sortedPoints[itr1], sortedPoints[itr2]);
           if (slopeCompared == 0) {
               last_point = (sortedPoints[itr1].compareTo(sortedPoints[itr2]) <= 0) ? sortedPoints[itr1++] : sortedPoints[itr2++];
           } else {
               last_point = (slopeCompared < 0) ? sortedPoints[itr1++] : sortedPoints[itr2++];
           }
           aux[writePtr] = last_point;
           writePtr++;
           while (itr1 <= mid && itr2 <= length) {
               if (slopeOrder.compare(sortedPoints[itr1], sortedPoints[itr2]) <= 0) {
                   if (slopeOrder.compare(last_point, sortedPoints[itr1]) == 0) {
                       //bingo last point in temp and this current select point are collinear
                       lastRepeatedCount++;
                       last_point = sortedPoints[itr1];
                   } else {
                       //not a collinear point reset lastRepeatedCount and set temp if lastRepeaseted count if more than threshold
                       if (lastRepeatedCount >= squashthreshold) {
                           mlinesegments = addSegment(mlinesegments, last_point, origin, totalSegments);
                       }
                       lastRepeatedCount = 0;
                       last_point = sortedPoints[itr1];
                   }
                   aux[writePtr] = sortedPoints[itr1];
                   itr1++;
               } else {
                   if (slopeOrder.compare(last_point, sortedPoints[itr2]) == 0) {
                       //bingo last point in temp and this current select point are collinear
                       lastRepeatedCount++;
                       last_point = sortedPoints[itr2];
                   } else {
                       //not a collinear point reset lastRepeatedCount and set temp if lastRepeaseted count if more than threshold
                       if (lastRepeatedCount >= squashthreshold) {
                           mlinesegments = addSegment(mlinesegments, last_point, origin, totalSegments);
                       }
                       lastRepeatedCount = 0;
                       last_point = sortedPoints[itr2];
                   }
                   aux[writePtr] = sortedPoints[itr2];
                   itr2++;
               }
               writePtr++;
           }
           while (itr1 <= mid) {
               if (slopeOrder.compare(last_point, sortedPoints[itr1]) == 0) {
                   //bingo last point in temp and this current select point are collinear
                   lastRepeatedCount++;
                   last_point = sortedPoints[itr1];
               } else {
                   //not a collinear point reset lastRepeatedCount and set temp if lastRepeaseted count if more than threshold
                   if (lastRepeatedCount >= squashthreshold) {
                       mlinesegments = addSegment(mlinesegments, last_point, origin, totalSegments);
                   }
                   lastRepeatedCount = 0;
                   last_point = sortedPoints[itr1];
               }
               aux[writePtr] = sortedPoints[itr1];
               itr1++;
               writePtr++;
           }
           while (itr2 <= length) {
               if (slopeOrder.compare(last_point, sortedPoints[itr2]) == 0) {
                   //bingo last point in temp and this current select point are collinear
                   lastRepeatedCount++;
                   last_point = sortedPoints[itr2];
               } else {
                   //not a collinear point reset lastRepeatedCount and set temp if lastRepeaseted count if more than threshold
                   if (lastRepeatedCount >= squashthreshold) {
                       mlinesegments = addSegment(mlinesegments, last_point, origin, totalSegments);
                   }
                   lastRepeatedCount = 0;
                   last_point = sortedPoints[itr2];
               }
               aux[writePtr] = sortedPoints[itr2];
               itr2++;
               writePtr++;
           }
           if(lastRepeatedCount >= squashthreshold) {
               mlinesegments = addSegment(mlinesegments,last_point,origin, totalSegments);
           }
      }
        return mlinesegments;
   }
}
