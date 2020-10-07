package space.akshaymathur.algo.analysis;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class BitonicArray {
    public static void main(String ...s){
        int[] nums = new int[]{1,3,5,7,6,4,2,0, -1, -4 , -16 };
        if(bitonicSearch(0, nums.length -1, 0, nums) >= 0){
            System.out.println("FOUND");
        }else{
            System.out.println("NOT FOUND");
        }
    }

    static int bitonicSearch(int start, int end, int target, int... nums){
        var inflection = searchInflectionPoint(start, end, nums);
        if(inflection >= 0 ){
            return Math.max(binarySearchAscending(start,inflection,target,nums), binarySearchDecending(inflection,end,target,nums));
        }
        return inflection;
    }

    static int searchInflectionPoint(int start, int end, int ...nums) {
        int mid = start + (end - start) / 2;
        if (mid > 0 && mid < nums.length - 1) {
            if (nums[mid - 1] < nums[mid] && nums[mid] < nums[mid + 1]) {
                return mid;
            } else if (nums[mid - 1] > nums[mid]) {
                return searchInflectionPoint(start, mid, nums);
            } else {
                return searchInflectionPoint(mid, end, nums);
            }
        }
        return -1;
    }
    
    static int bitonicSearch2(int start, int end, int target, int ...nums){
        if(start == end) return (nums[start] == target)? start : -1;
        int mid = start + (end - start) /2 ;
        if(mid == start){
            if(nums[mid] < nums[mid+1]){
                return binarySearchAscending(start, end, target, nums);
            }
            else{
                return binarySearchDecending(start,end, target, nums);
            }
        }
        if(nums[mid - 1] < nums[mid] && nums[mid + 1] < nums[mid]){
            //point of inflection
            if(nums[mid] == target) return mid;
            else return Math.max(binarySearchAscending(start, mid, target, nums), binarySearchDecending(mid, end, target, nums));
        }else if(nums[mid - 1] > nums[mid]){
            if(target > nums[mid]){
                return bitonicSearch2(start, mid, target, nums);
            }else{
                return Math.max(bitonicSearch2(start,mid, target,nums), binarySearchDecending(mid, end, target, nums));
            }
        }else{
            if(target > nums[mid]){
                return bitonicSearch2(mid, end, target, nums);
            }else{
                return Math.max(bitonicSearch2(mid, end, target, nums), binarySearchAscending(start, mid, target, nums));
            }
        }
    }

    private static int binarySearchDecending(int start, int end, int target, int[] nums) {
        return Arrays.binarySearch(IntStream.of(nums).boxed().toArray(Integer[]::new), start, end + 1, target , Comparator.reverseOrder());
    }

    private static int binarySearchAscending(int start, int mid, int target, int[] nums) {
        return Arrays.binarySearch(nums, start, mid + 1, target);
    }

}
