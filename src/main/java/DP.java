import java.lang.reflect.Array;

public class DP {

    public boolean canJump(int[] nums) {
        boolean[] dp = new boolean[nums.length];
       // Arrays.fill(dp,false);

        for(int i = nums.length-1 ; i >= 0 ;i++){
            if(nums[i]+i >= nums.length-1 ){
                dp[i] = true;
                continue;
            }
            int max = Math.min(nums.length -1,i+nums[i]);
            for(int j = i+1; j<max;j++ ){
                if(dp[j]){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[0];
    }
}
