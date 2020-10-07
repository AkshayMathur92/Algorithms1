package space.akshaymathur.algo.unionfind;

import java.util.Arrays;
import java.util.stream.IntStream;

public class WeightedQuickUnion implements IUnionFindADT{

    private int n ;
    private int parent[];
    private int size[];

    public WeightedQuickUnion(int n){
        this.n = n ;
        parent = IntStream.rangeClosed(0, n).toArray();
        size = new int[n + 1]; // inclusive range
        Arrays.fill(size, 1);
    }

    @Override
    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if(qRoot != pRoot) {
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

    private int root(int node){
        while(parent[node] != node){
            node = parent[node];
        }
        return node;
    }

    public int getRoot(int i) {
        return root(i);
    }
}
