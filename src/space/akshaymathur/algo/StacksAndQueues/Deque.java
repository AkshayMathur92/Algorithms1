package space.akshaymathur.algo.StacksAndQueues;


import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // a helper linked list data type
    private class Node {
        public Item val;
        public Node next;
        public Node prev;
    }

    private class ItemIterator implements Iterator<Item>{

        private Node itr;

        public ItemIterator(Node head) {
            itr = head;
        }

        @Override
        public boolean hasNext() {
            return itr != null;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            var toReturn = itr.val;
            itr = itr.next;
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node head;
    private Node tail;
    private Integer size;
    // construct an empty deque
    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }
    // add the item to the front
    public void addFirst(Item item) {
        if(item == null) throw new IllegalArgumentException();
        Node toAdd = new Node();
        toAdd.next = head;
        toAdd.prev = null;
        toAdd.val = item;
        if(head != null) {
            head.prev = toAdd;
            head = toAdd;
        }else {
            head = toAdd;
            tail = head;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if(item == null) throw new IllegalArgumentException();
        Node toAdd = new Node();
        toAdd.next = null;
        toAdd.prev = tail;
        toAdd.val = item;
        if(tail != null) {
            tail.next = toAdd;
            tail = toAdd;
        }else {
            tail = toAdd;
            head = tail;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        Node toReturn = head;
        if(toReturn != null) {
            head = head.next;
            if(head != null) head.prev = null;
            else tail = null;
        }else {
            throw new NoSuchElementException();
        }
        size--;
        return toReturn.val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Node toReturn = tail;
        if(toReturn != null) {
            tail = tail.prev;
            if(tail != null) tail.next = null;
            else head = null;
        }else {
            throw new NoSuchElementException();
        }
        size--;
        return toReturn.val;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ItemIterator(head);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> intDeq = new Deque<>();
        intDeq.addFirst(1);
        assert (intDeq.removeLast() == 1);
        assert (intDeq.isEmpty());
        intDeq.addFirst(1);
        intDeq.addFirst(2);
        intDeq.addFirst(3);
        intDeq.addLast(4);
        intDeq.addLast(5);
        for(Integer i : intDeq) {
            StdOut.print(i + " ");
        }
        StdOut.print (intDeq.removeFirst() == 3 && intDeq.size() == 4);
        StdOut.print (intDeq.removeFirst() == 2  && intDeq.size() == 3);
        StdOut.print (intDeq.removeFirst() == 1  && intDeq.size() == 2);
        StdOut.print (intDeq.removeFirst() == 4  && intDeq.size() == 1);
        StdOut.print (intDeq.removeLast() == 5  && intDeq.size() == 0);
        intDeq.addLast(1);
        assert(intDeq.removeFirst() == 1);
        intDeq.addLast(2);
        intDeq.addLast(3);
        assert (intDeq.removeFirst() == 2);
        assert (intDeq.removeFirst() == 3);
    }

}
