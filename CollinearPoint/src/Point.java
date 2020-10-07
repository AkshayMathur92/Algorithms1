
import java.util.Comparator;

import edu.princeton.cs.algs4.Point2D;


public class Point implements Comparable<Point> {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }                         // constructs the point (x, y)

    public void draw() {
        new Point2D(x,y).draw();
    }                               // draws this point

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        if(that == null) throw new IllegalArgumentException();
        new Point2D(x,y).drawTo(new Point2D(that.x,that.y));
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if(that == null) throw new NullPointerException();
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if(that == null) throw new NullPointerException();
        if(x == that.x && that.y == y){
            return Double.NEGATIVE_INFINITY;
        }
        if (this.x == that.x){
            return Double.POSITIVE_INFINITY;
        }else if(this.y == that.y){
            return 0.0;
        }
        return (that.y - this.y) / (double)(that.x - this.x);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return (t1, t2) -> {
            if(t1 == null || t2 == null)
                throw new NullPointerException();
            if(t1 == t2) return 0;
           double slopet1 = this.slopeTo(t1);
           double slopet2 = this.slopeTo(t2);
           if(slopet1 == slopet2) return 0;
           if(Math.abs(slopet1 - slopet2) <= Math.ulp(0.0)) return 0;
           else if(slopet1 > slopet2) return  1;
           else return -1;
        };
    }
}