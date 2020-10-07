package space.akshaymathur.algo.unionfind;

import java.util.Arrays;
import java.util.stream.IntStream;

public class WeightedQuickUnionPathCompression implements IUnionFindADT{

    private int n ;

    public int getN() {
        return n;
    }

    public int[] getParent() {
        return parent;
    }

    public int getSize(int p) {
        return size[root(p)];
    }

    private int[] parent;
    private int[] size;

    public WeightedQuickUnionPathCompression(int n){
        this.n = n ;
        parent = IntStream.rangeClosed(0, n).toArray();
        size = new int[n + 1]; // inclusive range
        Arrays.fill(size, 1);
    }

    @Override
    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if(pRoot != qRoot) {
            if (size[pRoot] > size[qRoot]) {
                parent[qRoot] = pRoot;
                size[pRoot] += size[qRoot];
            } else {
                parent[pRoot] = qRoot;
                size[qRoot] += size[pRoot];
            }
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }

    public int root(int node){
        while(parent[node] != node){
            parent[node] = parent[parent[node]]; //the only difference from only weighted union find
            node = parent[node];
        }
        return node;
    }

    public int getRoot(int q) {
        return root(q);
    }
}
