import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;


public class KdTree {
    private int size;
    private Node root;
    public KdTree(){
    }
    public           boolean isEmpty(){
        return size == 0;
    }                      // is the set empty?
    public               int size() {
        return size;
    }                        // number of points in the set
    public              void insert(Point2D p){
        checkNul(p);
        root = insert(root, p, true);
        size++;
    }              // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p){
        checkNul(p);
        return search(root, p, true);
    }            // does the set contain point p?
    public              void draw(){
        draw(root, true, -10.0, -10.0, 10, 10);
    }                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect){
        checkNul(rect);
        LinkedList<Point2D> list = new LinkedList<>();
        range(root, rect, list, true);
        return list;
    }             // all points that are inside the rectangle (or on the boundary)

    public           Point2D nearest(Point2D p){
        checkNul(p);
        if(root == null) return null;
//        StdDraw.setPenColor(StdDraw.GREEN);
//        StdDraw.setPenRadius(0.05);
        return nearest(root,p, root, true,  0,  0,  1,  1).p;
    }

    private Node nearest(Node root, Point2D p, Node nearest, boolean onX, double xMin, double yMin, double xMax, double yMax) {
        if(root == null) return nearest;
//        root.p.draw();
        Node newNear = (root.p.distanceSquaredTo(p) < nearest.p.distanceSquaredTo(p)) ? root : nearest;
        if(onX) {
           if(p.x() < root.p.x()) {
               Node nearOnLeft = nearest(root.left, p, newNear, !onX, xMin, yMin, root.p.x(), yMax);
               newNear = (newNear.p.distanceSquaredTo(p) < nearOnLeft.p.distanceSquaredTo(p))? newNear : nearOnLeft;
               double shortestDistanceInOneSubTree = newNear.p.distanceSquaredTo(p);
//               double distanceToRight = distanceFromPoint(p, root.p.x(), p.y());
               double distanceToRight = new RectHV(root.p.x(), yMin, xMax, yMax).distanceSquaredTo(p);
               if(shortestDistanceInOneSubTree > distanceToRight)
                   newNear = nearest(root.right,p,newNear, !onX, root.p.x(), yMin, xMax, yMax);

           }else{
               Node nearOnRight = nearest(root.right, p, newNear, !onX, root.p.x(), yMin, xMax, yMax);
               newNear = (newNear.p.distanceSquaredTo(p) < nearOnRight.p.distanceSquaredTo(p))? newNear : nearOnRight;
               double shortestDistanceInOneSubTree = newNear.p.distanceSquaredTo(p);
//               double distanceToLeft =distanceFromPoint(p, root.p.x(), p.y());
               double distanceToLeft = new RectHV(xMin,yMin,root.p.x(), yMax).distanceSquaredTo(p);
               if(shortestDistanceInOneSubTree > distanceToLeft)
                   newNear = nearest(root.left,p,newNear, !onX, xMin, yMin, root.p.x(), yMax);
           }
        }else{
            if(p.y() < root.p.y()) {
                Node nearOnLeft = nearest(root.left, p, newNear, !onX, xMin, yMin, xMax, root.p.y());
                newNear = (newNear.p.distanceSquaredTo(p) < nearOnLeft.p.distanceSquaredTo(p))? newNear : nearOnLeft;
                double shortestDistanceInOneSubTree = newNear.p.distanceSquaredTo(p);
//                double distanceToRight = distanceFromPoint(p, p.x(), root.p.y());
                double distanceToRight = new RectHV(xMin, root.p.y(), xMax, yMax).distanceSquaredTo(p);
                if(shortestDistanceInOneSubTree > distanceToRight)
                    newNear = nearest(root.right,p,newNear, !onX, xMin, root.p.y(),xMax,yMax);

            }else{
                Node nearOnRight = nearest(root.right, p, newNear, !onX, xMin, root.p.y(), xMax, yMax);
                newNear = (newNear.p.distanceSquaredTo(p) < nearOnRight.p.distanceSquaredTo(p))? newNear : nearOnRight;
                double shortestDistanceInOneSubTree = newNear.p.distanceSquaredTo(p);
//                double distanceToLeft =distanceFromPoint(p, p.x(), root.p.y());
                double distanceToLeft = new RectHV(xMin, yMin, xMax, root.p.y()).distanceSquaredTo(p);
                if(shortestDistanceInOneSubTree > distanceToLeft)
                    newNear = nearest(root.left,p,newNear, !onX, xMin, yMin,xMax, root.p.y());
            }

        }
        return newNear;
    }

    private void range(Node root, RectHV rect, LinkedList<Point2D> list, boolean onX) {
        if(root == null) return ;
        if(rect.contains(root.p)){
            list.add(root.p);
            range(root.left,rect, list, !onX);
            range(root.right, rect, list, !onX);
        }else if(onX){
            if(rect.xmax() >= root.p.x() && rect.xmin() <= root.p.x()){
                range(root.left,rect, list, !onX);
                range(root.right, rect, list, !onX);
            }
           else if(rect.xmax() < root.p.x()){
               range(root.left,rect,list,!onX);
           }else{
               range(root.right, rect, list, !onX);
           }
        } else{
            if(rect.ymax() >= root.p.y() && rect.ymin() <= root.p.y()){
                range(root.left,rect, list, !onX);
                range(root.right, rect, list, !onX);
            }
            else if(rect.ymax() < root.p.y()){
                range(root.left,rect,list,!onX);
            }else{
                range(root.right, rect, list, !onX);
            }
        }
    }

    private void checkNul(Object rect) {
        if(rect  == null ) throw new IllegalArgumentException();
    }

    private Node draw(Node root, boolean onX, double xMin, double yMin, double xMax, double yMax){
        StdDraw.setPenRadius(0.001);
       if(root == null) return null;
       if(onX){
           StdDraw.setPenColor(StdDraw.RED);
           StdDraw.line(root.p.x(), yMin, root.p.x(), yMax);
           draw(root.left, !onX, xMin, yMin, root.p.x(), yMax);
           draw(root.right, !onX, root.p.x(), yMin, xMax, yMax);
       }else {
           StdDraw.setPenColor(StdDraw.BLUE);
           StdDraw.line(xMin, root.p.y(), xMax, root.p.y());
           draw(root.left, !onX, xMin,yMin, xMax, root.p.y());
           draw(root.right, !onX, xMin, root.p.y(), xMax, yMax);
       }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        root.p.draw();
        StdDraw.textLeft(root.p.x(),root.p.y(), root.p.toString());
        return root;
    }

    private boolean search(Node root, Point2D p, boolean onX){
       if(root == null) return false;
       if(root.p.equals(p))return true;
       if(onX){
           if(p.x() < root.p.x()) return search(root.left, p, !onX);
           else return search(root.right, p, !onX);
       }else{
           if(p.y() < root.p.y()) return search(root.left, p, !onX);
           else return search(root.right, p , !onX);
       }
    }
    private Node insert(Node root, Point2D point2D, boolean onX){
        if(root == null) return new Node(point2D);
        if(root.p.equals(point2D)) {
            size -- ;
            return root;
        }
        if(onX){
            if(point2D.x() < root.p.x()){
                root.left = insert(root.left, point2D, !onX);
            }else{
                root.right = insert(root.right, point2D, !onX);
            }
        }else{
            if(point2D.y() < root.p.y()){
                root.left = insert(root.left, point2D, !onX);
            }else{
                root.right = insert(root.right, point2D, !onX);
            }
        }
        return root;
    }

    private static class Node {
        Node left;
        Node right;
        Point2D p;
        Node(Point2D p){
            this.p = p;
        }
    }

    private double distanceFromPoint(Point2D point2D, double x, double y){
        double dx = point2D.x() - x;
        double dy = point2D.y() - y;
        return dx*dx + dy*dy;
    }

    public static void main(String... s){
        String filename = s[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        PointSET pointSET = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            pointSET.insert(p);
        }
        StdDraw.setCanvasSize(1024,1024);
        kdtree.draw();
        RectHV rect = new RectHV(0.1,0.1,0.3, 0.3);
        for(Point2D p : kdtree.range(rect)){
            System.out.println(p);
        }
        System.out.println();
        for(Point2D p : pointSET.range(rect)){
            System.out.println(p);
        }
    }
}
