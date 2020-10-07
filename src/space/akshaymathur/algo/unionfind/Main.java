package space.akshaymathur.algo.unionfind;

import space.akshaymathur.algo.unionfind.*;

import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        //Quick find approach
        IUnionFindADT simpleUnionFind = new QuickFind(9);
        runTest(simpleUnionFind);

        //Quick union approach
        IUnionFindADT quickFind = new QuickUnion(9);
        runTest(quickFind);

        //Weighted union approach
        IUnionFindADT quickUnion = new WeightedQuickUnion(9);
        runTest(quickUnion);

        //Weighted union with Path Compression
        IUnionFindADT weightedQuickUnionPathCompression = new WeightedQuickUnionPathCompression(9);
        runTest(weightedQuickUnionPathCompression);

        //Social Connectivity Test
        new SocialNetworkConnectivity().startTest();

        //Max Find
        new UnionFindWithCanonicalElement();

        //Successor
        new SuccessorWithDelete();
    }

    static void runTest(IUnionFindADT unionFindADT){
        var start = Instant.now();
        assert (unionFindADT.isConnected(0,0) );
        assert (!unionFindADT.isConnected(0, 1));
        unionFindADT.union(0, 1);
        assert (unionFindADT.isConnected(0,1) );
        assert (unionFindADT.isConnected(1,0) );
        unionFindADT.union(1, 2);
        assert (unionFindADT.isConnected(1,2) );
        assert (unionFindADT.isConnected(2,1) );
        assert (unionFindADT.isConnected(0,2) );
        assert (unionFindADT.isConnected(2,0) );
        unionFindADT.union(0,3);
        assert (unionFindADT.isConnected(0,3) );
        assert (unionFindADT.isConnected(1,3) );
        assert (unionFindADT.isConnected(2,3) );
        assert (!unionFindADT.isConnected(0, 4));
        assert (!unionFindADT.isConnected(1, 4));
        assert (!unionFindADT.isConnected(2, 4));
        assert (!unionFindADT.isConnected(3, 4));
        assert (!unionFindADT.isConnected(0, 5));
        assert (!unionFindADT.isConnected(1, 5));
        assert (!unionFindADT.isConnected(2, 5));
        assert (!unionFindADT.isConnected(3, 5));
        unionFindADT.union(4,5);
        assert (unionFindADT.isConnected(4,5));
        assert (!unionFindADT.isConnected(0, 4));
        assert (!unionFindADT.isConnected(1, 4));
        assert (!unionFindADT.isConnected(2, 4));
        assert (!unionFindADT.isConnected(3, 4));
        assert (!unionFindADT.isConnected(0, 5));
        assert (!unionFindADT.isConnected(1, 5));
        assert (!unionFindADT.isConnected(2, 5));
        assert (!unionFindADT.isConnected(3, 5));
        unionFindADT.union(5,6);
        unionFindADT.union(6,7);
        unionFindADT.union(1,7);
        assert (unionFindADT.isConnected(1,7));
        assert (unionFindADT.isConnected(1,4));
        var stop = Instant.now();
        System.out.println("TEST RAN SUCCESSFULY In " + Duration.between(start, stop).toNanos());
    }
}
