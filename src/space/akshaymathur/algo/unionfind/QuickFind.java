package space.akshaymathur.algo.unionfind;

import java.util.stream.IntStream;

public class QuickFind implements IUnionFindADT {

    private int[] data;
    private int n;

    public QuickFind(int n){
        assert (n > 0);
        this.n = n;
        data = IntStream.rangeClosed(0, n).toArray();
    }

    @Override
    public void union(int p, int q) {
        int newvalue= data[p];
        int valueToReplace = data[q];
        for(int i =0; i < n; i ++){
            if(data[i] == valueToReplace){
                data[i] = newvalue;
            }
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return data[p] == data[q];
    }
}
