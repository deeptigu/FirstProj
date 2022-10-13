package premQues;

import java.util.Map;

/**
 * Given two sparse vectors, compute their dot product.
 *
 * Implement class SparseVector:
 *
 * SparseVector(nums) Initializes the object with the vector nums
 * dotProduct(vec) Compute the dot product between the instance of SparseVector and vec
 * A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently and compute the dot product between two SparseVector.
 *
 * Follow up: What if only one of the vectors is sparse?
 * Input: nums1 = [1,0,0,2,3], nums2 = [0,3,0,4,0]
 * Output: 8
 * Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
 * v1.dotProduct(v2) = 1*0 + 0*3 + 0*0 + 2*4 + 3*0 = 8
 * // Your SparseVector object will be instantiated and called as such:
 * // SparseVector v1 = new SparseVector(nums1);
 * // SparseVector v2 = new SparseVector(nums2);
 * // int ans = v1.dotProduct(v2);
 * A sparse vector is a vector that has mostly zero values, while a dense vector is a vector where most of the elements are non-zero. It is inefficient to store a sparse vector as a one-dimensional array (Approach 1). Instead, we can store the non-zero values and their corresponding indices in a dictionary, with the index being the key (Approach 2).
 * Alternatively, we can represent elements of a sparse vector as an array of pairs for each non-zero value. Each pair has an integer index and a numerical value (Approach 3).
 */
public class SparseVector {
    Map<Integer,Integer> mappings;

    SparseVector(int[] nums) {
        for(int i =0; i < nums.length;i++){
            if(nums[i] != 0){
                mappings.put(i,nums[i]);
            }
        }
    }

    public int dotProduct(SparseVector vec) {
        int ans = 0;
        for(Integer l : mappings.keySet()){
            if(vec.mappings.containsKey(l))
             ans += mappings.get(l) * vec.mappings.get(l);
        }
        return ans;
    }

}
