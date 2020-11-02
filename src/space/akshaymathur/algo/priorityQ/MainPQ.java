package space.akshaymathur.algo.priorityQ;

import edu.princeton.cs.algs4.MinPQ;
import space.akshaymathur.algo.sorting.SortingMain;

public class MainPQ {
    public static void main(String... s){
        MaxPQ<Integer> maxPQ = new MaxPQ<>(10);
        for(Integer i  : SortingMain.generateRandomIntegerArrayOfSize(10)){
            maxPQ.insert(i);
        }
        while(! maxPQ.isEmpty()){
            System.out.println(maxPQ.delMax());;
        }
        Taxicab3(2000);
    }

    public static void Taxicab3(int n)
    {
        // O(n) time and O(n) space
        var pq = new MinPQ<SumOfCubes>();
        for (int i = 1; i <= n; i++)
        {
            pq.insert(new SumOfCubes(i, i));
        }

        // O(n^2*logn) time
        var sentinel = new SumOfCubes(0, 0);
        while (pq.size() > 0)
        {
            var current = pq.delMin();

            if (current.Result() == sentinel.Result())
                System.out.println( sentinel.Result() + " -> " + sentinel.A + " " + sentinel.B + " = " + current.A + " " + current.B);

            if (current.B <= n)
                pq.insert(new SumOfCubes(current.A, current.B + 1));

            sentinel = current;
        }
    }

    private static class SumOfCubes implements Comparable<SumOfCubes> {
        public SumOfCubes(long a, long b){
            this.A = a;
            this.B = b;
        }
        long A;
        long B;
        long Result(){ return A * A  * A  + B * B * B;}

        @Override
        public int compareTo(SumOfCubes sumOfCubes) {
            return this.Result() <= sumOfCubes.Result()? - 1  : 1 ;
        }
    }
}
