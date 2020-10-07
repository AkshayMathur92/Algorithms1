package space.akshaymathur.algo.StacksAndQueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while(k-- > 0){
           rq.enqueue((StdIn.readString()));
        }
        for(String i : rq){
            StdOut.println(i);
        }
    }
}