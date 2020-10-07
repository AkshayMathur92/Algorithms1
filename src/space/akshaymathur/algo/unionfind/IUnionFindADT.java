package space.akshaymathur.algo.unionfind;

public interface IUnionFindADT {
    void union(int p, int q);
    boolean isConnected(int p, int q);
}
