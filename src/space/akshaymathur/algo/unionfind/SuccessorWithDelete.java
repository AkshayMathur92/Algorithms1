package space.akshaymathur.algo.unionfind;

import java.util.stream.IntStream;

public class SuccessorWithDelete {

    int[] init;
    QuickUnion unionFind;
    int N = 10;

    public SuccessorWithDelete(){
        unionFind = new QuickUnion(N);
        init = IntStream.rangeClosed(0 , N).toArray();
        runTest();
    }

    public void remove(int q){
        if(init[q] != -1 ) {
            unionFind.union(q+ 1, q);
            init[q] = -1;
        }
    }

    public int findSuccessor(int q){
        if(init[q+1] != -1){
            return init[q+1];
        }else{
            return unionFind.getRoot(q + 1);
        }
    }

    private void runTest(){
        assert (findSuccessor(4) == 5);
        assert (findSuccessor(3) == 4);
        remove(4);
        assert (findSuccessor(3) == 5);
        assert (findSuccessor(6) == 7);
        assert (findSuccessor(7) == 8);
        assert (findSuccessor(8) == 9);
        remove(7);
        remove(8);
        assert (findSuccessor(6) == 9);
        remove(5);
        assert (findSuccessor(3) == 6);
        remove(6);
        assert (findSuccessor(3) == 9);
        System.out.println("Successor Test Complete without error");
    }

}


