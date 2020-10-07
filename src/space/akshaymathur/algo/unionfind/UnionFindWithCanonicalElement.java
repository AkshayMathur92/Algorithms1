package space.akshaymathur.algo.unionfind;

/*
Add a method find()\mathtt{find()}find() to the union-find data type so that find(i)\mathtt{find(i)}find(i) returns the largest element in the connected component containing iii.
The operations, union()\mathtt{union()}union(), connected()\mathtt{connected()}connected(), and find()\mathtt{find()}find() should all take logarithmic time or better.
 */

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.IntStream;

public class UnionFindWithCanonicalElement {
    private UnionfindWithFindMax unionfindWithFindMax;

    public UnionFindWithCanonicalElement(){
        unionfindWithFindMax = new UnionfindWithFindMax(10);
        runTest();

    }
    void runTest() {
        var start = Instant.now();
        assert (unionfindWithFindMax.isConnected(0, 0));
        assert (!unionfindWithFindMax.isConnected(0, 1));
        assert (unionfindWithFindMax.find(0) == 0);
        unionfindWithFindMax.union(0, 1);
        assert (unionfindWithFindMax.find(0) == 1);
        assert (unionfindWithFindMax.isConnected(0, 1));
        assert (unionfindWithFindMax.isConnected(1, 0));
        unionfindWithFindMax.union(1, 2);
        assert (unionfindWithFindMax.find(0) == 2);
        assert (unionfindWithFindMax.isConnected(1, 2));
        assert (unionfindWithFindMax.isConnected(2, 1));
        assert (unionfindWithFindMax.isConnected(0, 2));
        assert (unionfindWithFindMax.isConnected(2, 0));
        unionfindWithFindMax.union(0, 3);
        assert (unionfindWithFindMax.find(0) == 3);
        assert (unionfindWithFindMax.isConnected(0, 3));
        assert (unionfindWithFindMax.isConnected(1, 3));
        assert (unionfindWithFindMax.isConnected(2, 3));
        assert (!unionfindWithFindMax.isConnected(0, 4));
        assert (!unionfindWithFindMax.isConnected(1, 4));
        assert (!unionfindWithFindMax.isConnected(2, 4));
        assert (!unionfindWithFindMax.isConnected(3, 4));
        assert (!unionfindWithFindMax.isConnected(0, 5));
        assert (!unionfindWithFindMax.isConnected(1, 5));
        assert (!unionfindWithFindMax.isConnected(2, 5));
        assert (!unionfindWithFindMax.isConnected(3, 5));
        unionfindWithFindMax.union(4, 5);
        assert (unionfindWithFindMax.find(4) == 5);
        assert (unionfindWithFindMax.isConnected(4, 5));
        assert (!unionfindWithFindMax.isConnected(0, 4));
        assert (!unionfindWithFindMax.isConnected(1, 4));
        assert (!unionfindWithFindMax.isConnected(2, 4));
        assert (!unionfindWithFindMax.isConnected(3, 4));
        assert (!unionfindWithFindMax.isConnected(0, 5));
        assert (!unionfindWithFindMax.isConnected(1, 5));
        assert (!unionfindWithFindMax.isConnected(2, 5));
        assert (!unionfindWithFindMax.isConnected(3, 5));
        unionfindWithFindMax.union(5, 6);
        unionfindWithFindMax.union(6, 7);
        assert (unionfindWithFindMax.find(4) == 7);
        unionfindWithFindMax.union(1, 7);
        assert (unionfindWithFindMax.isConnected(1, 7));
        assert (unionfindWithFindMax.isConnected(1, 4));
        var stop = Instant.now();
        System.out.println("TEST RAN SUCCESSFULY In " + Duration.between(start, stop).toNanos());
    }
}

class UnionfindWithFindMax implements IUnionFindADT {

    private int[] parent;
    private int n;
    private int[] size;
    private int[] max;

    public UnionfindWithFindMax(int n){
        this.n = n;
        parent = IntStream.rangeClosed(0 , n).toArray();
        max = IntStream.rangeClosed(0 , n).toArray();
        size = new int[n + 1];
        Arrays.fill(size, 1);
    }

    @Override
    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if(pRoot != qRoot){
            if(size[pRoot] > size[qRoot]){
                parent[qRoot] = pRoot;
                size[pRoot] += size[qRoot];
                max[pRoot] = Math.max(max[pRoot], max[qRoot]);
            }else{
                parent[pRoot] = qRoot;
                size[qRoot] += size[pRoot];
                max[qRoot] = Math.max(max[pRoot], max[qRoot]);
            }
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int p){
        while(parent[p] != p){
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public int find(int p){
        return max[root(p)];
    }
}


