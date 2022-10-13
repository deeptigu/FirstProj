import java.util.Arrays;

public class REcursion {

    int k;
    int res;
    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        kthSmallestRec(root);
        return res;
    }
    public boolean isMatch1(String s, String p) {
        if(s.isEmpty()){
            return p.isEmpty() || (p.length() ==1 && p.charAt(0) == '*');
        }
        if(p.isEmpty()){
            return s.isEmpty();
        }
        boolean firstMatch = s.charAt(0) == p.charAt(0)  || p.charAt(0) == '.';
        boolean res = false;
        if(firstMatch || p.charAt(0) == '*'){
            res =  isMatch(s.substring(1),p.substring(1));
        }
        if(!res){
            res = isMatch(s.substring(1),p.substring(0));
        }
        return res;
    }

    public boolean isMatch(String s, String p) {
        int i = s.length()-1 , j = p.length()-1;
        while(i >= 0 && j >=0){
            if((s.charAt(i) == p.charAt(j)) || (p.charAt(j) == '.')){
                i--;
                j--;
                continue;
            }
            if(p.charAt(j) == '*'){
                j = j-1;
                while(i>=0 && s.charAt(i) == p.charAt(j)){
                    i--;
                }
                j--;
            }else {
                return false;
            }
        }
        if(i < 0){
            while(j>=0 && p.charAt(j) == '*'){
                 j -= 2;
            }
            if( j> 0){
                return false;
            }
        }
        else if(j < 0 ){
            return false;
        }
        return true;
    }

    public void kthSmallestRec(TreeNode root){
        if(root == null){
            return;
        }
        kthSmallestRec(root.left);
        k--;
        if(k ==0){
            res = root.val;
        }
        kthSmallestRec(root.right);
    }


   /* public int findTargetSumWays(int[] nums, int target) {
        int total = Arrays.stream(nums).sum();
        int s = total*2 +1;
        int[][] dp = new int[nums.length][s];
        dp[0][total+nums[0]] = 1;
        dp[0][total-nums[0]] = 1;
        for(int i = 1 ; i < nums.length;i++){
            for(int sum = 0 ; sum < s; sum++){
                if(dp[i-1][sum] != 0){
                    dp[i][sum+nums[i]-total] += dp[i-1][sum]
                }
            }
        }
    }*/



    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode next;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

