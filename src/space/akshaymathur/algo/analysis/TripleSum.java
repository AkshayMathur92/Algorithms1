package space.akshaymathur.algo.analysis;


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

@FunctionalInterface
interface TripleSumAlgo{
    void findTripleSum(int... ints);
}


public class TripleSum {

    public static void main(String ...s){

//        for(int n : new int[]{10, 100, 1000, 10000}){
//            int[] ints = new Random(1).ints(n, -n, n).toArray();
//            StdOut.println(Arrays.toString(ints));
//            for(TripleSumAlgo algo : new TripleSumAlgo[]{naive, withHashMap, withSorting, wikipedia}){
//                var stopwatch = new Stopwatch();
//                algo.findTripleSum(ints);
//                StdOut.println("Time taken "+ stopwatch.elapsedTime() + " \n\n");
//            }
//            StdOut.println("*****************");
//        }
        leetcode.findTripleSum(-1,0,1,2,-1,-4);
        leetcode.findTripleSum(-2,0,1,1,2);
        leetcode.findTripleSum(-2,0,0,2,2);
    }


   static TripleSumAlgo naive = (int ... ints) -> {
        int count = 0;
        for(int i = 0; i < ints.length; i ++){
            for(int j = i+1 ; j < ints.length; j ++){
                for(int k = j+1; k < ints.length; k ++){
                    if(ints[i] + ints[j]+ ints[k] == 0 ){
//                        StdOut.printf("Triplet found %d %d %d \n", ints[i] , ints[j],ints[k]);
                        count++;
                    }
                }
            }
        }
        StdOut.println("Total Count = "+ count);
    };

    //does not work with duplicates (due to hash map)
    static TripleSumAlgo withHashMap = (int ... ints) -> {
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for(int i = 0 ; i < ints.length; i ++){
            map.put(ints[i], i);
        }
        for(int i = 0; i < ints.length; i ++){
            for(int j = i + 1; j < ints.length; j ++){
                int key = -(ints[i] + ints[j]);
                if(map.containsKey(key) && map.get(key) > j){
                    count++;
//                    StdOut.printf("Triplet found %d %d %d \n", ints[i] , ints[j],ints[map.get(key)]);
                }
            }
        }
        StdOut.println("Total Count = "+ count);
    };

    // this also does not work with duplicates
    static TripleSumAlgo withSorting = (int ... ints) -> {
        Arrays.sort(ints);
        int count = 0;
        for(int i =0; i < ints.length; i++){
            for(int j = i + 1; j < ints.length; j++){
                int found = Arrays.binarySearch(ints, -(ints[i]+ints[j]));
                if( found >=0 && i != found && j != found && ints[i] <= ints[j] && ints[j] <= ints[found]){
//                    StdOut.printf("Triplet found %d %d %d \n", ints[i] , ints[j],ints[found]);
                    count ++;
                }
            }
        }
        StdOut.println ("Total Count = "+ count);
    };

    // works with duplicates
    static TripleSumAlgo wikipedia = (int ... ints) -> {
        Arrays.sort(ints);
        int count = 0;
        for(int i =0; i < ints.length - 3; i++){
            var a = ints[i];
            var start = i + 1;
            int end = ints.length - 1;
            while(start < end) {
                var b = ints[start];
                var c = ints[end];
                if(a + b + c == 0){
//                    StdOut.printf("Triplet found %d %d %d \n", a ,b, c);
                    count ++;
                    start++;
                    end--;
                } else if (a + b + c > 0){
                    end --;
                } else {
                    start ++;
                }
            }
        }
        StdOut.println ("Total Count = "+ count);
    };

    static TripleSumAlgo leetcode = (int ... ints) -> {
        Arrays.sort(ints);
        for(var start = 0; start < ints.length - 2; start++){
                while(start - 1 >= 0  && ints[start - 1] == ints[start]) start ++;
            var end = ints.length - 1;
            for(var mid = start + 1; mid < end;){
                var value = ints[start] + ints[mid] + ints[end];
                if(value == 0){
                    System.out.println(Arrays.toString(new int[]{ints[start], ints[mid], ints[end]}));
                    while(mid < ints.length - 1 && ints[mid] == ints[mid + 1]) mid ++;
                    while(end < mid && ints[end] == ints[mid -1]) end --;
                    mid ++;
                    end --;
                }else if (value > 0 ){
                    end--;
                }else{
                    mid++;
                }
            }
        }
    };
}
