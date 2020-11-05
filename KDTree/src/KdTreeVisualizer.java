/******************************************************************************
 *  Compilation:  javac KdTreeVisualizer.java
 *  Execution:    java KdTreeVisualizer
 *  Dependencies: KdTree.java
 *
 *  Add the points that the user clicks in the standard draw window
 *  to a kd-tree and draw the resulting kd-tree.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.*;

public class KdTreeVisualizer {
    public static void main(String[] args) {
        StdDraw.setScale(0,1);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        String filename = args[0];
        In in = new In(filename);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            p.draw();
            kdtree.insert(p);
            assert (kdtree.contains(p));
        }
        StdDraw.setPenRadius(0.01);
        while (true) {
            kdtree.draw();
            StdDraw.show();
            StdDraw.pause(200);
        }
    }
}
