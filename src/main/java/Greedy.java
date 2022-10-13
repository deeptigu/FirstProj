import javafx.util.Pair;

import java.util.*;
import java.util.Arrays;

public class Greedy {
/*
Raft/paxos
Hyperloglog - Count unique values fast
Count min sketch
    Hierarchial timing wheels
    GeoHash
    Quadtree

 */
public static void main(String[] args){

    int[][] grid = {{3,9},{7,12},{3,8},{6,8},{9,10},{2,9},{0,9},{3,9},{0,6},{2,8}};
    int[] arr = {2,4,5,0,1,3};
    Greedy hl = new Greedy();
    hl.rearrangeString("aabbcc",3);
    System.out.println();
}

    public String rearrangeString(String s, int k) {
        PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->b[1]-a[1]);
        HashMap<Character,Integer> map=new HashMap<>();
        for(int i=0;i<s.length();i++)
        {
            map.put(s.charAt(i),map.getOrDefault(s.charAt(i),0)+1);
        }
        for(Map.Entry<Character,Integer> entry:map.entrySet())
            pq.add(new int[]{(int)entry.getKey(),entry.getValue()});
        StringBuilder st = new StringBuilder();
      /* Queue<int[]> q = new LinkedList<Integer[]>();
       StringBuilder st = new StringBuilder();
        while(!pq.isEmpty()){
            int[] maxFreq = pq.poll();
            st.append((char) maxFreq[0]);
            maxFreq[1]--;
            q.add(maxFreq);
            if(q.size() >= k){
                int[] fre = q.poll();
                if(fre[1] > 0)
                    pq.add(fre);

            }
        }*/
        return s.length() == st.length() ? st.toString() : "";
    }
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> res = new ArrayList<>();
        PriorityQueue<int[]> employeeProcessingIndex = new PriorityQueue<>((a,b) -> schedule.get(a[0]).get(a[1]).start - schedule.get(b[0]).get(b[1]).start);
        for(int i =0;i<schedule.size();i++){
            int[] idx = {i,0};
            employeeProcessingIndex.add(idx);
        }
        int[] first = employeeProcessingIndex.peek();
        int prevEnd = schedule.get(first[0]).get(0).start;
        while(!employeeProcessingIndex.isEmpty()){
         int[] interval =  employeeProcessingIndex.poll();
         if(prevEnd < schedule.get(interval[0]).get(interval[1]).start){
             res.add(new Interval(prevEnd,schedule.get(interval[0]).get(interval[1]).start));
         }
         prevEnd = Math.max(prevEnd,schedule.get(interval[0]).get(interval[1]).end);
         if(schedule.get(interval[0]).size() > interval[1]+1){
             employeeProcessingIndex.add(new int[]{interval[0], interval[1]+1});
         }
        }
        return res;
    }
    public int findArrows(int[][] points){
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] < o2[1])
                    return -1;
                else if(o1[1] == o2[1])
                    return 0;
                else return 1;
            }
        });
        if(points.length == 0){
            return 0;
        }
        int numOfArrows = 1;
        int end = points[0][1];
        List<Pair<Integer,Integer>> res = new ArrayList<>();
        res.add(new Pair(points[0][0],points[0][1]));
        for(int i = 1 ; i < points.length; i++){
            if(points[i][0] > end){
                end = points[i][1];
                numOfArrows++;
                res.add(new Pair<>(points[i][0],points[i][1]));
            }
        }
        return numOfArrows;
    }
    public int findMinArrowShots(int[][] points) {
            List<Pair<Integer,Integer>> res = new ArrayList<>();
            Arrays.sort(points, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    if (o1[0] < o2[0])
                        return -1;
                    else if(o1[0] == o2[0])
                        return 0;
                    else return 1;
                }
            });
            int x=0 , y =0, count =1;
            Pair<Integer,Integer> point = new Pair<>(points[0][0],points[0][1]);
            for (int i = 1 ; i <points.length;i++){
                x = points[i][0];
                y = points[i][1];
                if(point.getValue() < points[i][0]){
                    count++;
                    point = new Pair<>(points[i][0],points[i][1]);
                }else{
                    x = Math.max(point.getKey(), x);
                    y = Math.min(point.getValue(),y);
                    point = new Pair<>(x,y);
                }

            }
            return count;
    }

    public int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        int maxFreq = 0;
        for(int i  =0 ; i<26;i++){
            int idx = tasks[i]-'A';
            freq[idx]++;
            maxFreq = Math.max( freq[idx],maxFreq);
        }
        //sort  Arrays.sort(freq);
        int idleTime = (maxFreq-1) * n;
        for(int i = freq.length-2; i >=0 && idleTime >0 ;i--){
            idleTime -= Math.min(maxFreq-1,freq[i]);
        }
        idleTime = Math.max(0,idleTime);
        return tasks.length+idleTime;
    }

    public int maxProfit(int[] prices) {
        int profit = 0, min = Integer.MAX_VALUE;
        for(int i = 0; i < prices.length ; i++){
            if(min > prices[i]){
                min = prices[i];
            } else if(profit < prices[i] - min){
                profit = prices[i] - min;
            }
        }
        return profit;
    }


    public boolean canJumpGreedy(int[] nums) {
        int minPos = nums.length-1;
        for(int i = nums.length-2 ; i >= 0 ;i--){
            if(i + nums[i] >= minPos){
                minPos = i;
            }
        }
       return minPos==0;
    }


    class Interval {
        public int start;
        public int end;

        public Interval() {}

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    };
}
