package space.akshaymathur.algo.analysis;

import java.util.HashMap;

class Solution {

    public static void main(String ...s){
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(rabinKarp("abcabcbb"));
        System.out.println(rabinKarp("cdd"));
    }
    public static int lengthOfLongestSubstring(String str) {
        char[] s = str.toCharArray();
        //init map with char of current substring and their position
        HashMap<Character, Integer> pos = new HashMap<>();
        var begin = 0;
        var end = s.length;
        var itr = begin + 1;
        var max_begin = 0;
        var max_end = 0;
        pos.put(s[0], 0);
        //iterate from begin and put elements in map with pos
        while(itr < end){ // iterate
            if(pos.containsKey(s[itr])){
                //duplicate char found
                var delete_till = pos.get(s[itr]);
                for(; begin <= delete_till; begin++){
                    pos.remove(s[begin]);
                }
            }else{
                //new char add to map and increase so_far
                if(itr - begin > max_end - max_begin){
                    //update max found till now
                    max_begin = begin;
                    max_end = itr;
                }
            }
            pos.put(s[itr], itr);
            itr++;
        }
        return max_end - max_begin + 1;
    }

    public static int rabinKarp(String str){
        int starts_at = 0, max_len = 1, end = str.length();
        char[] s = str.toCharArray();

        for(int end_at = 0; end_at < end; end_at++){
            for(int j = starts_at; j < end_at; j ++){
                if(s[end_at] == s[j]){
                    max_len = Math.max(max_len, end_at - starts_at);
                    starts_at = j + 1;
                    break;
                }
            }
        }
        return Math.max(max_len, end - starts_at);
    }
}