package space.akshaymathur.algo.priorityQ;


public class MaxPQ<K extends Comparable<K>> {

    int n;
    Comparable[] arr;

    public MaxPQ(int cap){
        arr = new Comparable[cap + 1];
    }

    private void percolateUp(int i){
        while(i > 1){
            int parent = i / 2;
            if(arr[parent].compareTo(arr[i]) < 0){
                swap(arr, i , parent);
            }
            else break;
            i = parent;
        }
    }

    private void percolateDown(int i) {
        while(true){
            int maxChild = (i * 2 + 1 < n)? i *2 + 1 : -1;
            maxChild = (maxChild > 0 ) ? (arr[i*2].compareTo(arr[i*2 + 1])> 0 )? i * 2 : i * 2 + 1 : (i * 2 < n) ? i * 2: - 1;
            if (maxChild == - 1) break;
            swap(arr, i ,maxChild);
            i = maxChild;
        }
    }

    private void swap(Comparable[] arr, int i, int parent) {
        Comparable temp = arr[parent];
        arr[parent] = arr[i];
        arr[i] = temp;
    }

    public void insert(K key){
       if(n == arr.length - 1){
           throw new ArrayIndexOutOfBoundsException();
       }
        n++;
        arr[n] = key;
        percolateUp(n);
    }

    public K delMax(){
        K maxKey = (K) arr[1];
        swap(arr, 1, n);
        percolateDown(1);
        n--;
        return maxKey;
    }

    public K getMax(){
        return (K) arr[1];
    }

    public boolean isEmpty(){
        return n == 1;
    }
}
