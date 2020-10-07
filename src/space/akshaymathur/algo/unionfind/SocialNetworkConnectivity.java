package space.akshaymathur.algo.unionfind;

import java.util.Random;

/*
Social network connectivity.
Given a social network containing nnn members and a log file containing mmm timestamps at which times pairs of members formed friendships, design an algorithm to determine the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend).
Assume that the log file is sorted by timestamp and that friendship is an equivalence relation.
The running time of your algorithm should be mlog⁡nm \log nmlogn or better and use extra space proportional to nnn.
 */
public class SocialNetworkConnectivity {
    private SocialConnectionUnion socialConnectionUnion;
    private int TOTAL_MEMBERS = 10;

    public SocialNetworkConnectivity(){
        socialConnectionUnion = new SocialConnectionUnion(TOTAL_MEMBERS);
    }

    public void startTest(){
        Random random = new Random();
        int iteration = 0;
        while(true) {
            int p = random.nextInt(TOTAL_MEMBERS);
            int q = random.nextInt(TOTAL_MEMBERS);
            System.out.println(p + " became friends with " + q);
            if (socialConnectionUnion.addConnection(p, q)) {
                System.out.println("ALL CONNECTED after " + iteration + " iteration");
                break;
            }
            iteration++;
        }
    }
}


class SocialConnectionUnion extends WeightedQuickUnionPathCompression {

    public SocialConnectionUnion(int n) {
        super(n);
    }

    public boolean addConnection(int p, int q){
        super.union(p, q);
        return getSize(p) == getN();
    }
}