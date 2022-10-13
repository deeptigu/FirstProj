import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import static java.util.Arrays.*;

public class MicroTest {

    public static void main(String[] args){

        int[][] grid = {{1,3},{-2,2}};
        //int[] arr = {2,1,5,6,2,3};
        MicroTest hl = new MicroTest();

        System.out.println(hl.kClosest(grid,1));
    }

    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> dist(b)-dist(a));
        int n = points.length;
        for(int i = 0 ; i < n ;i++){
            if(pq.size() >= K) {
                int[] max = pq.peek();
                if (dist(max) > dist(points[i])) {
                    pq.poll();
                }
            }
            pq.offer(points[i]);

        }
        int[][] res = new int[K][2];
        for(int i = 0 ; i < K ;i++){
            res[i] = pq.poll();
        }
        return res;
    }

    private int dist(int[] b) {
        return b[0]*b[0]+b[1]*b[1];
    }



    public int minDeletions(String s) {
        Integer[] freq = new Integer[26];
        Arrays.fill(freq,0);
        for(int i =0 ; i< s.length() ;i++){
            freq[s.charAt(i) - 'a']++;
        }
        Arrays.sort(freq, Collections.reverseOrder());
        int deletions =0;
       // int frequencies =0;
        int maxAllowed = s.length();
        for(int i =0 ; i<26 || freq[i] > 0;i++){
            if(freq[i] > maxAllowed){
                deletions += freq[i]-maxAllowed;
                freq[i] = maxAllowed;
            }
            maxAllowed = freq[i]-1;
        }
        return deletions;
    }
}
