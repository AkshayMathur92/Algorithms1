/******************************************************************************
 *  Compilation:  javac NearestNeighborVisualizer.java
 *  Execution:    java NearestNeighborVisualizer input.txt
 *  Dependencies: PointSET.java KdTree.java
 *
 *  Read points from a file (specified as a command-line argument) and
 *  draw to standard draw. Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class NearestNeighborVisualizer {

    public static void main(String[] args) {

        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            assert (kdtree.contains(p));
            brute.insert(p);
        }

        // process nearest neighbor queries
        StdDraw.setCanvasSize(1024,1024);
        StdDraw.setScale(0, 1);
        StdDraw.enableDoubleBuffering();
        while (true) {
            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.text(x,y,x + " " + y);
            kdtree.draw();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();



            // draw in blue the nearest neighbor (using kd-tree algorithm)
            Point2D k = kdtree.nearest(query);
            // draw in red the nearest neighbor (using brute-force algorithm)
            Point2D p = brute.nearest(query);
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            p.draw();
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLUE);
            k.draw();
            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}