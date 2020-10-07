import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {

    private LineSegment[] segments;
    private final int nSegments;

    public BruteCollinearPoints(final Point[] points){
        if(points == null) throw new IllegalArgumentException();
        for(Point check : points) {
            if (check == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints, Comparator.naturalOrder());
        for(int i = 0; i < sortedPoints.length; i ++){
            if(sortedPoints[i] == null) throw new IllegalArgumentException();
            if(i > 0 && sortedPoints[i].compareTo(sortedPoints[i-1]) == 0 )
                throw new IllegalArgumentException();
        }
        segments = new LineSegment[sortedPoints.length];
        int nSegments = 0;
        for(int p = 0; p < sortedPoints.length - 3; p++){
            for(int q = p + 1; q < sortedPoints.length - 2; q++){
                for(int r = q  + 1; r < sortedPoints.length - 1; r++){
                    for(int s = r + 1; s < sortedPoints.length; s++){
                        double slopepq =sortedPoints[p].slopeTo(sortedPoints[q]);
                        double slopeqr = sortedPoints[q].slopeTo(sortedPoints[r]);
                        double slopers = sortedPoints[r].slopeTo(sortedPoints[s]);
                        if(slopepq == Double.POSITIVE_INFINITY || slopeqr == Double.POSITIVE_INFINITY || slopers == Double.POSITIVE_INFINITY){
                           if(slopepq == Double.POSITIVE_INFINITY && slopeqr == Double.POSITIVE_INFINITY && slopers == Double.POSITIVE_INFINITY){
                               segments[nSegments++] = new LineSegment(sortedPoints[p], sortedPoints[s]);
                           }else
                               continue;
                        }
                        if(Math.abs(slopepq -  slopeqr) <= Math.ulp(0.0)
                                && Math.abs(slopeqr  - slopers) <= Math.ulp(0.0)
                        && sortedPoints[p].compareTo(sortedPoints[q]) < 0 &&
                                sortedPoints[q].compareTo(sortedPoints[r]) < 0 &&
                                sortedPoints[r].compareTo(sortedPoints[s]) < 0){
                            segments[nSegments++] = new LineSegment(sortedPoints[p], sortedPoints[s]);
//                            System.out.format("%s, %s, %s, %s \n", points[p],points[q],points[r],points[s]);
                        }
                    }
                }
            }
        }
        LineSegment[] newSegments = new LineSegment[nSegments];
        System.arraycopy(segments,0,newSegments,0,nSegments);
        segments = newSegments;
        this.nSegments = nSegments;

    }
    public int numberOfSegments(){
        return nSegments;
    }
    public LineSegment[] segments(){
        return segments.clone();
    }
}
