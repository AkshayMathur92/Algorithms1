package space.akshaymathur.algo.unionfind;

import java.util.stream.IntStream;

public class QuickUnion implements IUnionFindADT{
    private int parent[];
    private int n;

    public QuickUnion(int n){
        this.n = n;
        parent = IntStream.rangeClosed(0, n).toArray();

    }

    @Override
    public void union(int p, int q) {
        parent[root(q)] = root(p);
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
