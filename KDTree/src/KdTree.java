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
        draw(root, true);
    }                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect){
        checkNul(rect);
        LinkedList<Point2D> list = new LinkedList();
        range(root, rect, list, true);
        return list;
    }             // all points that are inside the rectangle (or on the boundary)

    public           Point2D nearest(Point2D p){
        checkNul(p);
        if(root == null) throw new IllegalArgumentException();
        return nearest(root,p, root, true).p;
    }

    private Node nearest(Node root, Point2D p, Node nearest, boolean onX) {
        if(root == null) return nearest;
        Node newNear = (root.p.distanceTo(p) < nearest.p.distanceTo(p)) ? root : nearest;
        if(onX) {
           if(p.x() < root.p.x()) {
               Node nearOnLeft = nearest(root.left, p, newNear, !onX);
               if (nearOnLeft.p.distanceTo(p) > newNear.p.distanceTo(p)) {
                   Node nearOnRight = nearest(root.right, p, newNear, !onX);
                   newNear = (nearOnRight.p.distanceTo(p) < newNear.p.distanceTo(p)) ? nearOnRight : newNear;
               } else {
                   newNear = nearOnLeft;
               }
           }else{
               Node nearOnRight = nearest(root.right, p, newNear, !onX);
               if (nearOnRight.p.distanceTo(p) > newNear.p.distanceTo(p)) {
                   Node nearOnLeft = nearest(root.left, p, newNear, !onX);
                   newNear = (nearOnLeft.p.distanceTo(p) < newNear.p.distanceTo(p)) ? nearOnLeft : newNear;
               } else {
                   newNear = nearOnRight;
               }
           }
        }else{
            if(p.y() < root.p.y()) {
                Node nearOnLeft = nearest(root.left, p, newNear, !onX);
                if (nearOnLeft.p.distanceTo(p) > newNear.p.distanceTo(p)) {
                    Node nearOnRight = nearest(root.right, p, newNear, !onX);
                    newNear = (nearOnRight.p.distanceTo(p) < newNear.p.distanceTo(p)) ? nearOnRight : newNear;
                } else {
                    newNear = nearOnLeft;
                }
            }else{
                Node nearOnRight = nearest(root.right, p, newNear, !onX);
                if (nearOnRight.p.distanceTo(p) > newNear.p.distanceTo(p)) {
                    Node nearOnLeft = nearest(root.left, p, newNear, !onX);
                    newNear = (nearOnLeft.p.distanceTo(p) < newNear.p.distanceTo(p)) ? nearOnLeft : newNear;
                } else {
                    newNear = nearOnRight;
                }
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
           if(rect.xmax() < root.p.x()){
               range(root.left,rect,list,!onX);
           }else{
               range(root.right, rect, list, !onX);
           }
        } else{
            if(rect.ymax() < root.p.y()){
                range(root.left,rect,list,!onX);
            }else{
                range(root.right, rect, list, !onX);
            }
        }
    }

    private void checkNul(Object rect) {
        if(rect  == null ) throw new IllegalArgumentException();
    }

    private Node draw(Node root, boolean onX){
       if(root == null) return null;
       if(onX){
           StdDraw.setPenColor(StdDraw.RED);
           new Point2D(root.p.x(),0.0).drawTo(new Point2D(root.p.x(), 1.0));
       }else{
           StdDraw.setPenColor(StdDraw.BLUE);
           new Point2D(0.0, root.p.y()).drawTo(new Point2D(1.0, root.p.y()));
       }
       StdDraw.setPenColor(StdDraw.BLACK);
       draw(root.left, !onX);
        draw(root.right, !onX);
        root.p.draw();
        return root;
    }

    private boolean search(Node root, Point2D p, boolean onX){
       if(root == null) return false;
       if(root.p.equals(p))return true;
       if(onX){
           if(p.x() < root.p.x()) return search(root.left, p, !onX);
           else return search(root.right, p, !onX);
       }else{
           if(p.y() < root.p.y()) return search(root.right, p, !onX);
           else return search(root.right, p , !onX);
       }
    }
    private Node insert(Node root, Point2D point2D, boolean onX){
        if(root == null) return new Node(point2D);
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
}
