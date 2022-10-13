import javafx.util.Pair;

import java.math.BigInteger;
import java.util.*;

public class ArraysLocal {

    public static void main(String[] args){

        int[][] grid = {{1,1},{2,2},{3,3}};
        int[] arr = {2,4,3};
        ArraysLocal hl = new ArraysLocal();

        System.out.println(hl.maxPoints(grid));
    }
    public int findMin(int[] nums) {
        int low =0 , high = nums.length-1;
        while(low < high){
            int mid = low + (high-low)/2;
            if(mid+1 <= high && nums[mid] > nums[mid+1]){
                return nums[mid+1];
            }
            if(mid-1 >= 0 && nums[mid] < nums[mid-1]){
                return nums[mid];
            }
            if(nums[low] > nums[mid]){
                high = mid;
            }else if(nums[low] < nums[mid]){
                low = mid+1;
            }else {
                    low++;
            }
        }
        return nums[low];
    }

    public int findMinI(int[] nums) {
        if(nums[0] <= nums[nums.length-1]){
            return nums[0];
        }
        return binaryMin(nums,0,nums.length-1);
    }

    private int binaryMin(int[] nums, int start, int end) {

        if(start>end){
            return -1;
        }
        int mid = start +(end-start)/2;
        if(mid-1 >=0 && nums[mid-1] > nums[mid]){
            return nums[mid];
        }
        if(nums[start] > nums[mid]){
            return binaryMin(nums,start,mid);
        }else {
            return binaryMin(nums,mid+1,end);
        }

    }

    public int maxPoints(int[][] points) {
        int count = 0;
        int len = points.length;
        for(int i = 0; i < len ;i++){
            int[] curr = points[i];
            Map<Pair<Integer,Integer>,Integer> slopeVsCounter = new HashMap<>();
            for(int j = i+1 ;j < len; j++){
                Pair<Integer,Integer> r = calculatePt(curr,points[j],slopeVsCounter);
                count = Math.max(count,Math.max(r.getKey(),r.getValue()));
            }
        }
        return count+1;
    }

    private Pair<Integer,Integer> calculatePt(int[] curr, int[] point, Map<Pair<Integer, Integer>, Integer> slopeVsCounter) {
        int x1 = curr[0];
        int y1 = curr[1];
        int x2 = point[0];
        int y2 = point[1];
        int dup = 0;
        int count = 0;
        Pair<Integer,Integer> res = null;

        if(x1 == x2 && y1 == y2){
            dup += 1;
        }else if(x1 == x2){
            res = new Pair<>(Integer.MAX_VALUE,Integer.MAX_VALUE);
        }else if(y1 == y2){
            res = new Pair<>(0,0);
        }else {
            int deltaX = x1-x2, deltaY = y1-y2;
            if (deltaX < 0) {
                deltaX = -deltaX;
                deltaY = -deltaY;
            }
            Integer gcd = BigInteger.valueOf(deltaX).gcd(BigInteger.valueOf(deltaY)).intValue();
            res =  new Pair<Integer, Integer>(deltaX / gcd, deltaY / gcd);
        }
        slopeVsCounter.put(res,slopeVsCounter.getOrDefault(res,0)+1);
        count = Math.max(count,slopeVsCounter.get(res));
        return new Pair<Integer,Integer>(count,dup);
    }

    int MOD = (int) 1e9 + 7;

    private int getElement(int[] arr, int n, int i) {
        return (i == -1 || i == n) ? Integer.MIN_VALUE : arr[i];
    }

    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        long ans = 0;
        // Deque is faster than Stack
        Deque<Integer> stack = new LinkedList<>();

        // 2 sentinels: set arr[-1] & arr[n] as MIN_VALUE
        for (int i = -1; i <= n; i++) {
            // Find left closest element, e <= A[i]
            while (!stack.isEmpty() && getElement(arr, n, stack.peek()) > getElement(arr, n, i)) {
                // for each stack.pop(),
                // i is its right boundary and stack.peek() is left boundary.
                int currIdx = stack.pop();
                int left = stack.peek(), right = i;
                // calculate contribution
                ans = (ans + (long) (currIdx - left) * (right - currIdx) * arr[currIdx]) % MOD;
            }
            stack.push(i);
        }

        return (int) ans;
    }

    public int sumSubarrayMins1(int[] arr) {
        int modulo = 1_000_000_007;
        int n = arr.length;
        long res = 0;
        Deque<Integer> mono = new ArrayDeque();
        for (int idx = 0; idx < n + 1; idx++) {
            while (!mono.isEmpty() && arr[mono.peekLast()] > (idx == n ? 0 : arr[idx])) {
                int minIdx = mono.pollLast();
                int fromIdx = mono.isEmpty() ? -1 : mono.peekLast();
                int subarrayNo = ((minIdx - fromIdx) * (idx - minIdx));
                res = (res + (long)arr[minIdx] * subarrayNo % modulo) % modulo;
            }
            mono.offerLast(idx);
        }
        return (int)res % modulo;
    }

    public double knightProbability1(int N, int K, int sr, int sc) {
        double[][] dp = new double[N][N];
        //double[][] dp2 = new double[N][N];
        dp[sr][sc] = 1;
        int[] dirX = {2, 2, 1, 1, -1, -1, -2, -2};//{-2,-2,-1,-1,1,1,2,2};
        int[] dirY = {1, -1, 2, -2, 2, -2, 1, -1};//{-1,1,-2,2,-2,2,-1,1};
        for(; K>0;K--){
            double[][] dp2 = new double[N][N];
            for(int r = 0;r <N;r++){
                for(int c = 0 ; c <N;c++){
                   // if(dp[r][c] != 0) {
                        for (int d = 0; d < 8; d++) {
                            int newR = r + dirX[d];
                            int newC = c + dirY[d];
                            if(newR >=0 && newR < N && newC >=0 && newC < N){
                                dp2[newR][newC] += dp[r][c]/8.0;
                            }
                        }
                  //  }
                }
            }
            dp = dp2;
        }
        double ans = 0.0;
        for (double[] row: dp) {
            for (double x: row) ans += x;
        }
        return ans;
    }
   public double knightProbability(int N, int K, int sr, int sc) {
       double[][] dp = new double[N][N];
       int[] dr = new int[]{2, 2, 1, 1, -1, -1, -2, -2};
       int[] dc = new int[]{1, -1, 2, -2, 2, -2, 1, -1};

       dp[sr][sc] = 1;
       for (; K > 0; K--) {
           double[][] dp2 = new double[N][N];
           for (int r = 0; r < N; r++) {
               for (int c = 0; c < N; c++) {
                   for (int k = 0; k < 8; k++) {
                       int cr = r + dr[k];
                       int cc = c + dc[k];
                       if (0 <= cr && cr < N && 0 <= cc && cc < N) {
                           dp2[cr][cc] += dp[r][c] / 8.0;
                       }
                   }
               }
           }
           dp = dp2;
       }
       double ans = 0.0;
       for (double[] row: dp) {
           for (double x: row) ans += x;
       }
       return ans;
   }
    class Solution {

        // Get the maximum area in a histogram given its heights
        public int leetcode84(int[] heights) {
            Stack < Integer > stack = new Stack < > ();
            stack.push(-1);
            int maxarea = 0;
            for (int i = 0; i < heights.length; ++i) {
                while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
                    maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
                stack.push(i);
            }
            while (stack.peek() != -1)
                maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() -1));
            return maxarea;
        }

        public int maximalRectangle(char[][] matrix) {

            if (matrix.length == 0) return 0;
            int maxarea = 0;
            int[] dp = new int[matrix[0].length];

            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[0].length; j++) {

                    // update the state of this row's histogram using the last row's histogram
                    // by keeping track of the number of consecutive ones

                    dp[j] = matrix[i][j] == '1' ? dp[j] + 1 : 0;
                }
                // update maxarea with the maximum area from this row's histogram
                maxarea = Math.max(maxarea, leetcode84(dp));
            } return maxarea;
        }
    }

    public int largestRectangleArea(int[] heights) {
        int low = 0 , high = heights.length-1;
        int maxArea = 0 , minHeightLeft = Integer.MAX_VALUE, minHeightright = Integer.MAX_VALUE;
        while(low<high){
            if(heights[low] < heights[high]){
               // minHeightLeft = Math.min(minHeightLeft,heights[low]);
                maxArea = Math.max(maxArea,heights[low]*(high-low));
                low++;
            }else{
               // minHeightright = Math.min(minHeightright,heights[high]);
                maxArea = Math.max(maxArea,heights[high]*(high-low+1));
                high--;
            }
        }
        return maxArea;
    }
    public int minPathSum(int[][] grid) {
        int R = grid.length;
        int C = grid[0].length;
        int row = 0;
        for(int col =1; col < C; col++){
            grid[row][col] += grid[row][col-1];
        }
        int col = 0;
        for( row = 1 ; row < R ;row++){
            grid[row][col] += grid[row-1][col];
        }
        for(int r = 1 ; r < R ;r++){
            for(int c = 1 ;c < C ;c++){
                grid[r][c] += Math.min(grid[r-1][c],grid[r][c-1]);
            }
        }
        return grid[R-1][C-1];
    }

    public int[][] generateMatrix(int n) {
      //  n = (int)Math.sqrt(n);
        int left = 0 ,right = n-1 , up =0 , down = n-1;
        int i = 1;
        int[][] res = new int[n][n];
        while(i <= n*n){
            for(int col = left; col <= right;col++){
               res[up][col] = i++;
            }
            for(int row = up+1; row <= down ;row++){
                res[row][right] = i++;
            }
          //  if(up != down) {
                for (int col = right - 1; col >= left; col--) {
                    res[down][col] = i++;
                }
          //  }
           // if(left != right) {
            for (int row = down - 1; row > up; row--) {
                res[row][left] = i++;
            }
            //}
            left++;
            right--;
            up++;
            down--;
        }
        return res;
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> res = new ArrayList<>();
        int i =0;
        while(i < intervals.length && newInterval[0] > intervals[i][0]){
            res.add(intervals[i++]);
        }
        if(res.size()==0 || (i < intervals.length &&  res.get(res.size()-1)[1] < newInterval[0])){
            res.add(newInterval);
        }else {
            int start = Math.min(newInterval[0], res.get(res.size() - 1)[0]);
            int end = Math.max(newInterval[1], res.get(res.size() - 1)[1]);
            int[] arr = {start, end};
            res.remove(res.size() - 1);
            res.add(arr);
        }
        while(i < intervals.length){
            if (intervals[i][0] <= res.get(res.size() - 1)[1]) {
                int start = Math.min(intervals[i][0], res.get(res.size() - 1)[0]);
                int end = Math.max(intervals[i][1], res.get(res.size() - 1)[1]);
                int[] arr = {start, end};
                res.remove(res.size() - 1);
                res.add(arr);
            }else {
                res.add(intervals[i]);
            }
            i++;
        }
        return res.toArray(new int[res.size()][]);
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> res = new ArrayList<>();
        for(int[] item : intervals){
            if(res.size() == 0 || res.get(res.size()-1)[1] < item[0] ){
                res.add(item);
            }else{
                res.get(res.size()-1)[1] = Math.max(res.get(res.size()-1)[1], item[1]);
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    public boolean canJump(int[] nums) {
        int minIndx = nums.length-1;
        for(int i = nums.length-2; i>=0;i--){
            if(nums[i] + i >= minIndx){
                minIndx = i;
            }
        }
        return minIndx==0;
    }


        public boolean isMatch(String s, String p) {
            int sLen = s.length(), pLen = p.length();
            int sIdx = 0, pIdx = 0;
            int starIdx = -1, sTmpIdx = -1;

            while (sIdx < sLen) {
                // If the pattern caracter = string character
                // or pattern character = '?'
                if (pIdx < pLen && (p.charAt(pIdx) == '?' || p.charAt(pIdx) == s.charAt(sIdx))) {
                    ++sIdx;
                    ++pIdx;

                    // If pattern character = '*'
                } else if (pIdx < pLen && p.charAt(pIdx) == '*') {
                    // Check the situation
                    // when '*' matches no characters
                    starIdx = pIdx;
                    sTmpIdx = sIdx;
                    ++pIdx;

                    // If pattern character != string character
                    // or pattern is used up
                    // and there was no '*' character in pattern
                } else if (starIdx == -1) {
                    return false;

                    // If pattern character != string character
                    // or pattern is used up
                    // and there was '*' character in pattern before
                } else {
                    // Backtrack: check the situation
                    // when '*' matches one more character
                    pIdx = starIdx + 1;
                    sIdx = sTmpIdx + 1;
                    sTmpIdx = sIdx;
                }
            }

            // The remaining characters in the pattern should all be '*' characters
            for (int i = pIdx; i < pLen; i++) {
                if (p.charAt(i) != '*') {
                    return false;
                }

            }
            return true;
        }



    /*public boolean isMatch(String s, String p) {
        int[][] dp = new int[s.length()][p.length()];
        Arrays.fill(dp,-1);
        isMatchRec(s,p,0,0,dp);
        return dp[s.length()-1] == 1 ? true : false;
    }*/
    public int isMatchRec(String s, String p, int i , int j,int[] dp) {

        if(i == s.length()){
            return  (j == p.length()) || (isOnlyStarPresent(p,j)) ? 1 : 0;
        }
        if(j == p.length()){
            return i == s.length()  ? 1 : 0;
        }

        if(dp[i] == 1 ){
            return dp[i];
        }

        boolean isFirstMatch = p.charAt(j) == s.charAt(i) || p.charAt(j) =='?';
        int res = -1;
        if(isFirstMatch || p.charAt(j) == '*' ){
            res = isMatchRec(s,p,i+1,j+1,dp);
        }
        if(res != 1 && p.charAt(j) == '*' ){
            res = isMatchRec(s,p,i+1,j,dp) ;
            if(res != 1) {
                isMatchRec(s, p, i, j + 1, dp);
            }
        }
        res = 0;
        dp[i] = res;
        return res;
    }

    private boolean isOnlyStarPresent(String p,int idx){
        for(int i = idx; i < p.length();i++){
            if(p.charAt(i) != '*'){
                return false;
            }
        }
        return true;
    }

    public int trap(int[] height) {
        int[] res = new int[height.length];
        int left = height[0];
        for(int i =1 ;i <height.length;i++){
            if(left > height[i]){
                res[i] =left-height[i];
            } else{
                left = height[i];
            }
        }
        int right = height[height.length-1];
        int total =0;
        for(int i = height.length-2 ;i >=0;i--){
            if(right > height[i]){
                res[i] = Math.min(res[i],right-height[i]);
            } else{
                right = height[i];
                res[i] = 0;
            }
            total += res[i];
        }
        return total;
    }

    public String countAndSay(int n) {
        String numFormedSoFar = "1" ;
        String str = null;
        for(int i = 2 ;i <= n;i++){
           str = callForCount(numFormedSoFar);
           numFormedSoFar = str;
        }
        return str;
    }

    private String callForCount(String str) {
        int count = 1;
        StringBuilder res = new StringBuilder();
        for(int i = 1 ;i < str.length();i++){
            if(str.charAt(i-1) == str.charAt(i)){
                count++;
            }else{
                res.append(count).append(str.charAt(i-1));
                count=1;
            }
        }
        res.append(count).append(str.charAt(str.length()-1));
        return res.toString();
    }

    public void nextPermutation(int[] nums) {
        boolean flag = false;
        for(int i = nums.length-2;i>=0;i--){
            int nextIndex = getNextGreaterValue(nums,i);
            if(nextIndex != -1){
                int tmp = nums[i];
                nums[i] = nums[nextIndex];
                nums[nextIndex] = tmp;
                Arrays.sort(nums,i+1,nums.length);
                flag = true;
                break;
            }
        }
        if(!flag){
            Arrays.sort(nums);
        }
    }

    private int getNextGreaterValue(int[] nums, int i) {
        int nextGreaterMin = -1;
        for(int j = i+1 ; j < nums.length;j++){
            if(nums[j] > nums[i]){
                nextGreaterMin = nextGreaterMin != -1 && nums[j] > nums[nextGreaterMin] ? nextGreaterMin :j;
            }
        }
        return nextGreaterMin;
    }

    public int removeDuplicates(int[] nums) {
        int j =0;
        for(int i =1 ; i < nums.length;i++){
            while( i < nums.length && nums[i] == nums[i-1]){
                i++;
            }
            nums[++j] = nums[i];
        }
        return j+1;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        //List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        int k =4;
        List<List<Integer>> res = ksum(nums,target,k,0,nums.length-1);
        return res;
    }

    private List<List<Integer>> ksum(int[] nums, int target, int k, int start, int end) {
        List<List<Integer>> res = new ArrayList<>();
        if (start == nums.length) {
            return res;
        }
        if(nums[start] > target/k || nums[end] < target/k){
            return res;
        }
        if(k == 2){
            twoSum1(res,nums,start,target);
            return res;
        }
        for(int i = start ; i <= end-k+1 ; i++){
            List<List<Integer>> left = ksum(nums,target-nums[i],k-1,i+1,end);
            for(List<Integer> val:left){
                res.add(new ArrayList<>(Arrays.asList(nums[i])));
                res.get(res.size()-1).addAll(val);
            }
        }
        return res;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for(int i =0 ; i < nums.length-2;i++){
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            twoSum1(res,nums,i+1,nums[i]*-1);
        }
        return res;
    }
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int diff = Integer.MAX_VALUE;
        for(int i =0 ; i < nums.length-2;i++){
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            int res = twoSum2(nums,i+1,target-nums[i]);
            diff = Math.min(diff,Math.abs(target-res-nums[i]));
        }
        return target-diff;
    }
    private int twoSum2(int[] nums, int start, int target) {
        int high = nums.length-1;
        int diff = Integer.MAX_VALUE;
        while(start < high){
            int a = nums[start]+nums[high];
            if(diff > Math.abs(target-a)){
                diff = target-a;
            }
            if(a > target){
                high--;
            }else{
                start++;
            }
        }
        return target-diff;
    }

    private void twoSum1(List<List<Integer>> res, int[] nums, int start, int target) {
        int high = nums.length-1;
        int low = start-1;
        while(start < high){
            int a = nums[start]+nums[high];
            if(a == target){
                List<Integer> r = new ArrayList<>();
                r.add(nums[start]);
                r.add(nums[high]);
                r.add(nums[low]);
                res.add(r);
                start++;
                high--;
                while(start < high && nums[start] == nums[start-1]){
                    start++;
                }
                while(high > start && nums[high] == nums[high+1]){
                    high--;
                }
                continue;
            }
            if(a > target){
                high--;
            }else{
                start++;
            }
        }
    }

    public int romanToInt(String s) {
        Map<Character,Integer> values = new HashMap<>();
        values.put('M', 1000);
        values.put('D', 500);
        values.put('C', 100);
        values.put('L', 50);
        values.put('X', 10);
        values.put('V', 5);
        values.put('I', 1);
        int prev = values.get(s.charAt(s.length()-1)) , res = prev;
        for(int i = s.length()-2; i >= 0;i--){
            int a = values.get(i);
            res = a < prev ? res-a : res+a;
            prev = a;
        }
        return res;
    }
    public void test(int[] nums) {
        for(int i =0 ; i<nums.length;i++){
            while(i!=nums[i]){
                int tmp = nums[nums[i]];
                nums[nums[i]] = nums[i];
                nums[i] = tmp;
            }
        }
    }

    public int firstMissingPositive(int[] nums) {
        boolean isContainOne = false;
        int res = 0;
        for(int i =0 ; i<nums.length;i++){
            if(nums[i] == 1){
                isContainOne = true;
                break;
            }
        }
        if(!isContainOne){
            return 1;
        }

        for(int i = 0 ; i< nums.length;i++){
            if(nums[i] < 0 || nums[i] == 0 || nums[i] > nums.length ){
                nums[i] = 1;
            }
        }
        for(int i = 0 ; i< nums.length;i++){
            int idx = Math.abs(nums[i]);
            if(idx == nums.length){
                idx = 0;
            }
            if(nums[idx] > 0){
                nums[idx] = -1 * nums[idx];
            }
        }

        for(int i = 1 ; i<nums.length;i++){
            if(nums[i] >0){
                return i;
            }
        }
        if(nums[0] >0)
            return nums.length;

        return nums.length+1;
    }


    public void gameOfLife(int[][] board) {
        for(int i = 0 ; i<board.length;i++){
            for(int j = 0 ; j< board[0].length;j++){
                    int count = getCount(board,i,j);
                    if(count > 3 || count < 2 || (count == 2 && board[i][j] == 0)) {
                        board[i][j] = ((0%2) * 2)+board[i][j];
                    }else {
                        board[i][j] = (1%2)*2+board[i][j];
                    }
            }
        }
        for(int i = 0 ; i<board.length;i++){
            for(int j = 0 ; j< board[0].length;j++){
                board[i][j] = board[i][j] / 2;
            }
        }
    }

    private int getCount(int[][] board, int x1, int y1) {
        int[] x = {0,-1,+1,-1,+1,-1,0,1};
        int[] y = {-1,-1,-1,0,0,1,1,1};
        int c =0;
        for(int i = 0 ; i<x.length; i++){
            if( x1+x[i] >=0 && x1+x[i] < board.length && y1+y[i] >=0 && y1+y[i] < board[0].length && board[x1+x[i]][y1+y[i]]%2 ==1){
                c++;
            }
        }
        return c;
    }


    private int helper(int mask, int count, int[][] grid, HashMap<Integer, Integer> map) {
        if(mask == 0){
            return count;
        }
        if(map.containsKey(mask)){
            return map.get(mask);
        }
        int cur = Integer.MAX_VALUE;
        int tmp = mask;
        for(int i =0 ; i< grid.length; i++){
            for(int j =0 ; j < grid[0].length;j++){
                if((tmp & ( 1 << (grid[0].length * i + j))) > 0){
                    for(int k = 0 ; k < grid.length ;k++){ // Column modification
                        tmp &= ~(1 << grid[0].length * k + j);
                    }
                    for(int k = 0 ; k < grid[0].length ;k++){ // Row modification
                        tmp &= ~(1 << grid[0].length * i + k);
                    }
                    cur = Math.min(cur,helper(tmp,count+1,grid,map));
                    tmp = mask;
                }
            }
        }
        map.put(mask,cur);
       return count;
    }

    public int removeOnes2(int[][] grid) {
        int mask = 0;
        for(int i =0 ; i< grid.length; i++){
            for(int j =0 ; j < grid[0].length;j++){
                if(grid[i][j] ==1) {
                    mask ^= 1 << ((grid[0].length) * i + j);
                }
            }
        }
        return helper(mask,0,grid,new HashMap<Integer,Integer>());
    }


    public boolean removeOnes(int[][] grid) {

        for(int i =0 ;i < grid.length ;i++){
            if(grid[i][0] == 1){
                for(int j =0 ; j <grid[0].length;j++){
                    grid[i][j] = 1-grid[i][j];
                }
            }
        }

        for(int i =1 ;i < grid.length ;i++){
            for(int j =1 ; j <grid[0].length;j++){
                if(grid[i-1][j] != grid[i][j]){ return false;}
            }
        }
        return true;
    }

    public int numberOfBeams(String[] bank) {
        int prevRowLaserCount = 0, currRowLaserCount = 0;
        int res =0;
        for (int i = 0 ; i < bank.length ;i++){
            for(char ch : bank[i].toCharArray()){
                if(ch == '1'){
                    currRowLaserCount++;
                }
            }
            if(prevRowLaserCount != 0){
                res += prevRowLaserCount *currRowLaserCount;
            }
            if(currRowLaserCount != 0) {
                prevRowLaserCount = currRowLaserCount;
                currRowLaserCount = 0;
            }
        }
        return res;
    }

    public void setZeroes(int[][] matrix) {
        boolean isRowZero= false , isColZero = false;
        if(matrix[0][0] == 0){
            isRowZero =  true;
            isColZero =  true;
        }else {
            for (int i = 1; i < matrix[0].length; i++) {
                if (matrix[0][i] == 0) {
                    isRowZero = true;
                    break;
                }
            }
            for (int i = 1; i < matrix.length; i++) {
                if (matrix[i][0] == 0) {
                    isColZero = true;
                    break;
                }
            }
        }
        for (int i = 1 ; i < matrix.length ;i++){
            for(int j = 1 ; j < matrix[0].length; j++){
                if(matrix[i][j] == 0){
                    matrix[0][j] =0;
                    matrix[i][0] =0;
                }
            }
        }

        //Row conversion
        for (int i = 1 ; i < matrix.length ;i++){
            if(matrix[i][0] == 0){
                for(int j = 0 ; j < matrix[0].length; j++){
                    matrix[i][j] = 0;
                }
            }
        }

        //COlumn con
        for (int j = 0 ; j < matrix[0].length ;j++){
            if(matrix[0][j] == 0){
                for(int i = 1 ; i < matrix.length; i++){
                    matrix[i][j] = 0;
                }
            }
        }
        if(isColZero){
            for(int i = 0 ; i < matrix.length; i++){
                matrix[i][0] = 0;
            }
        }
        if(isRowZero){
            for(int i = 0 ; i < matrix[0].length; i++){
                matrix[0][i] = 0;
            }
        }



    }

    public int missingNumber(int[] nums) {
        java.util.Arrays.sort(nums);
        for(int i =0 ; i<= nums.length;i++){
            if(nums[i] != i){
                return i;
            }
        }
        return -1;
    }
   /* public List<Integer> findDisappearedNumbersRec(int[] nums) {

    }*/

    public int[] productExceptSelf(int[] nums) {
        int[] out = new int[nums.length];
        out[0] = 1;
        int n = nums.length, R =1;
        int L = 1;
        java.util.Arrays.fill(out,1);
        for(int i = 1;i<nums.length;i++){
            out[i] *= nums[i-1] * L;
            R *= nums[n-i];
            out[n-i-1] = out[n-i-1] * R;
            L *= nums[i-1];
        }
        return out;
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        for(int i = 0 ; i < nums.length ; i++){
            int a = Math.abs(nums[i]);
            if(nums[a-1] > 0) {
                nums[a - 1] = nums[a - 1] * -1;
            }

        }
        List<Integer> res = new ArrayList<>();
        for(int i = 0 ; i < nums.length ; i++){
            if(nums[i] > 0){
                res.add(i+1);
            }
        }
        return res;
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> s = new HashMap();
        int i = 0, j = nums.length-1;
        while(i<=j){
            int diff = target - nums[i];
            if(s.containsKey(diff)){
                int[] res = {s.get(diff),i};
                return res;
            }else {
                s.put(nums[i],i);
            }
        }
        return null;
    }


}
