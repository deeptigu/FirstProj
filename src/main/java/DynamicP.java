import java.util.*;
import java.util.Arrays;

public class DynamicP {

    public static void main(String[] args) {
        DynamicP dp = new DynamicP();
        int[] arr = {0,1,0,3,2,3};
        int[] b = {4,5,11,13,15,20};
        dp.numDecodings("12");
    }

    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        Arrays.fill(dp,1);
        for(int row = m-2; row >= 0 ;row--){
            for(int col = n-2 ; col >= 0 ;col--){
                dp[row][col] = dp[row+1][col]+dp[row][col+1];
            }
        }
        return dp[0][0];
    }

    public int numDecodings(String s) {
        int[] dp = new int[s.length()];
        if(s.charAt(s.length()-1) != '0'){
            dp[s.length()-1] = 1;
        }
        for(int i = s.length()-2 ; i >=0 ; i--){
            if(s.charAt(i) != '0'){
                dp[i] += dp[i+1];
            }
            int a = Integer.parseInt(s.substring(i,i+2));
            if(a >= 10 && a<=26){
                dp[i] = i < s.length()-2 ? dp[i]+dp[i+2] : dp[i]+1;
            }
        }
        return dp[0];
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        wordBreakTrack(s,0,new HashSet<String>(wordDict),res,new StringBuilder());
        return res;
    }

    private void wordBreakTrack(String s, int startIndx, HashSet<String> dict, List<String> res, StringBuilder strSofar) {
       // String tmp = strSofar.toString();
        if(startIndx == s.length()) {
            res.add(new String(strSofar.substring(0,strSofar.length()-1)));
            return;
        }

        for(int end = startIndx ; end < s.length();end++){
            String first = s.substring(startIndx,end+1);
            if(dict.contains(first)){
                strSofar.append(first).append(" ");
                wordBreakTrack(s,end+1,dict,res,strSofar);
                strSofar.delete(strSofar.length()-first.length()-1,strSofar.length());
            }
        }
    }

    public String longestPalindrome(String s) {
        if(s.length() == 1 ){
            return s;
        }
        int start = 0, end =0;
        for(int i = 0 ; i < s.length();i++){
            int len1 = expandCenter(s,i,i);
            int len2 = expandCenter(s,i,i+1);
            int len = len1 > len2 ? len1 : len2;
            if(end-start > 0) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start,end+1);
    }

    private int expandCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;

    }

    public static int floor(int nums[], int key){
        int l=0;
        int r=nums.length-1;
        while(l<=r){
            int m=l+(r-l)/2;
            if(nums[m]>=key)
                r=m-1;
            else
                l=m+1;
        }
        return l;
    }

    public int findNumberOfLIS(int[] nums) {
        int max =1;
        int[] dp = new int[nums.length];
        int[] count = new int[nums.length];
        dp[0] =1;
        count[0] = 1;
        for(int i=1; i <nums.length;i++ ){
            for(int j = i-1 ; j >=0 ; j--){
                if(nums[j] < nums[i] && dp[i] < dp[j]+1 ){
                    dp[i] = dp[j]+1;
                    count[i] = 1;
                    if(max < dp[i]){
                        max = dp[i];
                    }
                } else if(nums[j] < nums[i] && dp[i] == dp[j]+1 ){
                    count[i]++;
                }
            }
        }
        int total =0;
        for(int i=1; i <nums.length;i++ ){
            if(dp[i] == max){
                total += count[i];
            }
        }
        return total;
    }

    public int lengthOfLIS(int[] nums) {
        List<Integer> inc = new ArrayList<>();
        inc.add(nums[0]);

        for(int i = 1 ; i < nums.length ; i++ ){
            if(inc.get(inc.size()-1) < nums[i] ){
                inc.add(nums[i]);
            }else {
                int low = 0;
                int high = inc.size()-1;
                int target = nums[i];
                while(low < high){
                    int mid = (low + high)/2;
                    if(inc.get(mid) == target){
                        low = mid;
                        break;
                    }
                    if(inc.get(mid) < target){
                        low = mid+1; //low = mid;
                    }else{
                        high = mid; //high = mid-1;
                    }
                }
                inc.set(low,target);
            }
        }
        return inc.size();
    }

    public int maxProduct(int[] nums) {
        int max = nums[0] ;
        int multiplySofarPositive = 0, multiplySofarNegative = 0;
        if(nums[0] >= 0 ){
            multiplySofarPositive = nums[0];
        }else {
            multiplySofarNegative = nums[0]; //-2
        }
        for(int i = 1 ; i < nums.length ;i++){
            if(nums[i] < 0){
                int tmp = multiplySofarNegative*nums[i];
                multiplySofarNegative = Math.min(nums[i],multiplySofarPositive*nums[i]);
                multiplySofarPositive = tmp; //

            }else{
                int tmp = multiplySofarNegative*nums[i];
                multiplySofarPositive = Math.max(nums[i],multiplySofarPositive*nums[i] );//3
                multiplySofarNegative = tmp; // -6
            }
            max = Math.max(max,multiplySofarPositive);
        }
        return max ;
    }

    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp,amount+1);
        dp[0] = 0;
        for(int i =1 ; i <= amount ; i++){
            for(int coin : coins){
                if(i >= coin){
                    dp[i] = Math.min(dp[i],dp[i-coin]+1);
                }
            }
        }
        return dp[amount] != amount+1 ? dp[amount] : -1;
    }

    public int robII(int[] nums) {
        if(nums.length == 1){
            return nums[0];
        }
        int first = rob(nums,0,nums.length-2);
        int sec = rob(nums,1,nums.length-1);
        return first > sec ? first :sec;
    }
    public int rob(int[] nums,int start,int end) {
        int prevOfPrev = 0 , prev = 0;
        for(int i = start ; i <= end;i++){
            int tmp = prev;
            prev = Math.max(nums[i]+prevOfPrev, prev);
            prevOfPrev = tmp;
        }
        return prev;
    }


    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE , sumSofar = nums[0];
        for(int i = 1 ; i < nums.length ;i++){
            sumSofar = Math.max(sumSofar+nums[i],nums[i]);
            max = Math.max(max,sumSofar);
        }
        return max;
    }

    public int rob(int[] nums) {
        int prevOfPrev = 0 , prev = 0;
        for(int i = 0 ; i < nums.length;i++){
            int tmp = prev;
            prev = Math.max(nums[i]+prevOfPrev, prev);
            prevOfPrev = tmp;
        }
        return prev;
    }

    public int findTargetSumWays(int[] nums, int S) {
        int total = Arrays.stream(nums).sum();
        int[] dp = new int[2 * total + 1];
        dp[nums[0] + total] = 1;
        dp[-nums[0] + total] += 1;

        for (int i = 1; i < nums.length; i++) {
            int[] next = new int[2 * total + 1];
            for (int sum = -total; sum <= total; sum++) {
                if (dp[sum + total] > 0) {
                    next[sum + nums[i] + total] += dp[sum + total];
                    next[sum - nums[i] + total] += dp[sum + total];
                }
            }
            dp = next;
        }

        return Math.abs(S) > total ? 0 : dp[S + total];
    }
    public int[] countBits(int n) {
        int[] res = new int[n+1];
        res[0] = 0;
        res[1] = 1;
        for(int  i = 2 ; i <= n ;i++){
            int pow = (int)Math.floor(Math.log(i)/Math.log(2));
            if(i == Math.pow(2,pow)){
                res[i] = 1;
                continue;
            }
            int nearest = (int)Math.pow(2,pow);
            int secondValue = i - nearest;
            res[i] = res[secondValue] + res[nearest];
        }
        return res;
    }

    int[] sum ;
    public int sumRange(int left, int right) {
        if(left == 0){
            return sum[right];
        }
        return sum[right] - sum[left-1];
    }


    public int climbStairs(int n) {
        int one = 1, two = 2, res = 0;
        for(int i =3 ;i<=n;i++){
            res = one +two;
            one = two;
            two = res;
        }
        return res;
    }
}
