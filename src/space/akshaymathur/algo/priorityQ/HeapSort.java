package space.akshaymathur.algo.priorityQ;

import space.akshaymathur.algo.sorting.Sorting;

public class HeapSort implements Sorting {
    Comparable[] arr;
    int n;
    @Override
    public String name() {
        return "Heap Sort";
    }

    @Override
    public void sort(Comparable[] arr) {
        n = arr.length - 1;
        this.arr = arr;
        buildHeap();
    }

    private void buildHeap(){
        for(int i = n / 2; i >= 0; i --){
            percolateDown(i);
        }
        while(n >= 0){
            swap(arr, 0 , n);
            n--;
            percolateDown(0);
        }
    }

    private void percolateDown(int i) {
        while(true){
            int maxChild = maxchild(i);
            if (maxChild == - 1) break;
            if(arr[maxChild].compareTo(arr[i]) > 0) {
                swap(arr, i, maxChild);
                i = maxChild;
            }else break;
        }
    }

    private void swap(Comparable[] arr, int maxChild, int i) {
        Comparable temp = arr[i];
        arr[i] = arr[maxChild];
        arr[maxChild] = temp;
    }

    int maxchild(int i){
        int leftChild = i * 2 + 1;
        int rightChild = i * 2 + 2;
        if(leftChild > n) return -1;
        if(rightChild > n) return leftChild;
        return arr[leftChild].compareTo(arr[rightChild]) > 0 ? leftChild : rightChild;
    }
}
