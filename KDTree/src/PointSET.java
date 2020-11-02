import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> pointset;
    public         PointSET(){
        pointset = new TreeSet<>();
    }                              // construct an empty set of points
    public           boolean isEmpty(){
        return pointset.isEmpty();
    }                      // is the set empty?
    public               int size(){
        return pointset.size();
    }                         // number of points in the set
    public              void insert(Point2D p){
        checkNul(p);
        pointset.add(p);
    }              // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p){
        return pointset.contains(p);
    }           // does the set contain point p?
    public              void draw(){
        for(Point2D p : pointset)
            p.draw();
    }                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect){
        checkNul(rect);
        List<Point2D> pointsContained = new LinkedList<>();
        for(Point2D p : pointset) {
            if ((rect.contains(p))) {
                pointsContained.add(p);
            }
        }
        return pointsContained;
    }             // all points that are inside the rectangle (or on the boundary)

    private void checkNul(Object rect) {
        if(rect == null) throw new IllegalArgumentException();
    }

    public           Point2D nearest(Point2D p){
        checkNul(p);
        Point2D nearest = null;
        double distanceToNearestFound = Double.MAX_VALUE;
        for(Point2D i: pointset){
            double newDistance  = i.distanceSquaredTo(p);
            if(distanceToNearestFound > newDistance){
                nearest = i;
                distanceToNearestFound = newDistance;
            }
        }
        return nearest;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args){
        PointSET pSet = new PointSET();
        assert (pSet.isEmpty());
        pSet.insert(new Point2D(0.6,0.5));
        pSet.insert(new Point2D(0.8,0.6));
        pSet.insert(new Point2D(0.4,0.6));
        pSet.insert(new Point2D(0.1,0.4));
        pSet.insert(new Point2D(0.4,0.3));
        pSet.insert(new Point2D(0.8,0.3));
        assert (!pSet.isEmpty());
        assert (pSet.size() == 6);
        pSet.draw();
        assert (pSet.nearest(new Point2D(0.0,0.0)).equals(new Point2D(0.1,0.4)));
    }                  // unit testing of the methods (optional)
}