package space.akshaymathur.algo.StacksAndQueues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Object[] items;
    private int head = 0;
    private int tail = 0;
    public RandomizedQueue() {
        items = new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return head == tail;
    }

    // return the number of items on the randomized queue
    public int size() {
        return tail - head;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (tail != items.length) {
            items[tail] = item;
            tail++;
        }else {
            expandIfRequired();
            enqueue(item);
        }
    }


    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        shrinkIfRequired();
        head++;
        return (Item)items[head - 1];
    }

    private void expandIfRequired() {
        int currentCapacity = items.length;
        int wasteCapacity = currentCapacity - size();
        int size = size();
        if (wasteCapacity > size() * 2) {
            copy(items, head, tail, items);
            head = 0;
            tail = head + size;
        }else if (tail == items.length) {
            Object[] newItems = new Object[size() * 2];
            copy(items, head, tail, newItems);
            items = newItems;
        }
    }

    private void shrinkIfRequired() {
        int currentCapacity = items.length;
        int wasteCapacity = currentCapacity - size();
        int size = size();
        if (size()  * 2 < wasteCapacity) {
            Object[] newItems = new Object[size()];
            copy(items, head,tail, newItems);
            items = newItems;
            head = 0;
            tail = head + size;
        }
    }

    private void copy(Object[] src, int srcHead, int srcTail, Object[] dest) {
        for(int i = srcHead, di = 0;  i >=0 && i < srcTail; i++, di++) {
            dest[di] = src[i];
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        return (Item) items[StdRandom.uniform(head, tail)];
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ItemIterator();
    }

    private class ItemIterator implements Iterator<Item> {

        private final Object[] itrItems;
        private int end;

        public ItemIterator() {
            itrItems = new Object[size()];
            end = size();
            copy(items,head,tail, itrItems);
        }

        @Override
        public boolean hasNext() {
            return end > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int random = StdRandom.uniform(0, end);
            Item found = (Item) itrItems[random];
            itrItems[random] = itrItems[end - 1];
            end--;
            return found;
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        assert(q.size() == 3);
        for(Integer i : q) {
            StdOut.print(i + " ");
        }
        for(int i = 0 ; i< q.size(); i++) {
            StdOut.print(q.sample() + " ");
        }
        assert (q.dequeue() == 1);
        assert (q.size() == 2);
        assert (q.dequeue() == 2);
        assert (q.dequeue() == 3);
        assert (q.isEmpty());

    }

}