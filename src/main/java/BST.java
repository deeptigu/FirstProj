public class BST {

    public static void main(String[] args){

        int[][] grid = {{0,1,0},{0,0,1},{1,1,1},{0,0,0}};
        int[] arr = {1,3,4,2,2};
        BST hl = new BST();
        hl.findDuplicate(arr);
        System.out.println();
    }

    public int findDuplicate(int[] nums) {
        int n = nums.length, dup =0;
        int low = 1 , high = n-1;
        while(low <= high){
            int mid = low + (high-low)/2;
            int count = 0;
            for(int i  = 0;i<nums.length;i++){
                if(mid >= nums[i]){
                    count++;
                }
            }
            if(count > mid){
                dup = mid;
                high = mid-1;
            }else {
                low = mid +1;
            }
        }
        return dup;
    }

    public char nextGreatestLetter(char[] letters, char target) {
        int low = 0 , high = letters.length-1;
        while(low < high){
            int mid = low + (high -low)/2;
            if(letters[mid] == target){
                return letters[mid+1];
            }

            if(letters[mid]  < target){
                low = mid+1;
            }else{
                high = mid;
            }
        }
        return letters[low];
    }

    public int peakIndexInMountainArray(int[] arr) {
        int low = 0 , high = arr.length-1;
        while (low <= high){
            int mid = low + (high-low)/2;
            if(arr[mid] > arr[mid+1] && arr[mid-1] < arr[mid]){
                return mid;
            }
            if(arr[mid] < arr[mid+1]){
                low = mid;
            }else {
                high = mid+1;
            }
        }
        return -1;
    }

}
