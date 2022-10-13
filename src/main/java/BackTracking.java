import java.util.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BackTracking {

    public static void main(String[] args) {
        BackTracking bc = new BackTracking();
        char[][] b = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        int[] nums = {2,3,6,7};
       bc.generateParenthesis(3);
    }

    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList();
        if (n == 0) {
            ans.add("");
        } else {
            for (int c = 0; c < n; ++c)
                for (String left: generateParenthesis(c))
                    for (String right: generateParenthesis(n-1-c))
                        ans.add("(" + left + ")" + right);
        }
        return ans;
    }
    public List<String> generateAbbreviations(String word){
        List<String> ans = new ArrayList<String>();
        backtrack(ans, new StringBuilder(), word, 0, 0);
        return ans;
    }

    // i is the current position
    // k is the count of consecutive abbreviated characters
    private void backtrack(List<String> ans, StringBuilder builder, String word, int i, int k){
        int len = builder.length(); // keep the length of builder
        if(i == word.length()){
            if (k != 0) builder.append(k); // append the last k if non zero
            ans.add(builder.toString());
        } else {
            // the branch that word.charAt(i) is abbreviated
            backtrack(ans, builder, word, i + 1, k + 1);

            // the branch that word.charAt(i) is kept
            if (k != 0) builder.append(k);
            builder.append(word.charAt(i));
            backtrack(ans, builder, word, i + 1, 0);
        }
        builder.setLength(len); // reset builder to the original state
    }

    /*public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<>();
        StringBuilder string = new StringBuilder();
        generateAbbreviationssRec(res,word,0,string);
        return res;
    }*/

    private void generateAbbreviationssRec(List<String> res, String word, int start, StringBuilder string) {
        if(start == word.length()){
            res.add(string.toString());
            return;
        }
        for(int end = start ; end < word.length() ;end++){
            if(start == end) {
                string.append(word.charAt(end));
                generateAbbreviationssRec(res, word, end+1, string);
                string.deleteCharAt(string.length() - 1);
            }
            if (string.length() == 0 || !Character.isDigit(string.charAt(string.length() - 1))) {
                string.append(end-start+1);
                generateAbbreviationssRec(res, word, end+1, string);
                string.deleteCharAt(string.length() - 1);
            }
        }
    }

    public List<String> letterCombinations(String digits) {
        if(digits== null || digits.isEmpty()){
            return new ArrayList<>();
        }
        Map<Integer,StringBuilder> map = new HashMap<>();
        int j = 2;
        StringBuilder val = null;
        for(int i = 0 ; i < 26; i++){
            if( i % 3 == 0) {
                val = new StringBuilder();
                map.put(j++,val);
            }
            char start = (char) ((int)'a'+ i);
            val = val.append(start);
        }
        List<String> res = new ArrayList<>();
        StringBuilder string = new StringBuilder();
        letterCombinationsRec(res,digits,map,0,string);
        return res;
    }

    private void letterCombinationsRec(List<String> res, String digits, Map<Integer, StringBuilder> map,  int stringLength, StringBuilder string ) {
        if(stringLength == digits.length()){
            res.add(string.toString());
            return;
        }
        String value = map.get(Integer.parseInt(digits.substring(stringLength,stringLength+1))).toString();
        for(int mapStringIndex = 0 ; mapStringIndex < value.length() ;mapStringIndex++ ){
            string.append(value.charAt(mapStringIndex));
            letterCombinationsRec(res,digits,map,stringLength+1,string);
            string.deleteCharAt(string.length()-1);
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        combineWithoutConsRec(res,target,candidates,0,tmp);
        return res;
    }

    private void combineWithoutConsRec(List<List<Integer>> res, int target, int[] candidates, int start, List<Integer> tmp) {
        if(target == 0){
            res.add(new ArrayList<>(tmp));
            return;
        }
        if(start == candidates.length || target < 0){
            return;
        }
        for(int end = start ; end < candidates.length; end++){
            if(target- candidates[end] >= 0) {
                tmp.add(candidates[end]);
                combineWithoutConsRec(res, target-candidates[end],candidates, end, tmp);
                tmp.remove(tmp.size() - 1);
            }
        }

    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        combineRec(res,n,k,1,tmp);
        return res;
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        combine3Rec(res,n,k,1,tmp);
        return res;
    }

    private void combine3Rec(List<List<Integer>> res, int n, int k, int idx, List<Integer> tmp) {
        if(tmp.size() == k && n == 0){
            res.add(new ArrayList<>(tmp));
            return;
        }
        if(tmp.size() > k || n < 0){
            return;
        }
        for(int i =idx ; i <= 9; i++){
            if(n-i >= 0) {
                tmp.add(i); n-=i;
                combine3Rec(res, n, k, i + 1, tmp);
                tmp.remove(tmp.size() - 1); n+=i;
            }
        }
    }

    private void combineRec(List<List<Integer>> res, int n, int k, int idx,List<Integer> tmp) {
        if(tmp.size() == k){
            res.add(new ArrayList<>(tmp));
            return;
        }
        for(int i =idx ; i <= n; i++){
            tmp.add(i);
            combineRec(res,n,k,i+1,tmp);
            tmp.remove(tmp.size()-1);
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> tmp = IntStream.of(nums).boxed().collect(Collectors.toList());
        permuteRec(nums,0,res,tmp);
        return res;
    }
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        // count the occurrence of each number
        HashMap<Integer, Integer> counter = new HashMap<>();
        for (int num : nums) {
            if (!counter.containsKey(num))
                counter.put(num, 0);
            counter.put(num, counter.get(num) + 1);
        }

        LinkedList<Integer> comb = new LinkedList<>();
        this.backtrack(comb, nums.length, counter, results);
        return results;
    }


    protected void backtrack(
            LinkedList<Integer> comb,
            Integer n,
            HashMap<Integer, Integer> counter,
            List<List<Integer>> results) {

        if(comb.size() == n){
            results.add(new ArrayList<>(comb));
        }
        for(Map.Entry<Integer,Integer> entry : counter.entrySet()){
            int num = entry.getKey();
            int count = entry.getValue();
            if(count == 0){
                continue;
            }
            comb.add(num);
            counter.put(num,count-1);
            backtrack(comb,n,counter,results);
            comb.remove(comb.size()-1);
            counter.put(num,count);
        }

    }

    private void permuteRec(int[] nums, int idx, List<List<Integer>> res, List<Integer> tmp) {
        if(idx == nums.length){
            res.add(new ArrayList<>(tmp));
            return;
        }
        for(int j = idx ; j < nums.length;j++ ){
            Collections.swap(tmp,idx,j);
            permuteRec(nums,idx+1,res,tmp);
            Collections.swap(tmp,j,idx);

        }
    }

    private void swap(List<Integer> tmp,int idx1, int idx2){
        int num1 = tmp.get(idx1);
        tmp.set(idx1, tmp.get(idx2));
        tmp.set(idx2,num1);
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        Set<String> values = new HashSet<>();
        int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        for(int i = (int)Math.pow(2, n); i < (int)Math.pow(2, n + 1);i++){
            List<Integer> element = new ArrayList<>();
            StringBuilder tmp = new StringBuilder();
            String bitmask = Integer.toBinaryString(i).substring(1);
            for(int j  =0 ; j<n;j++){
                if(bitmask.charAt(j) == '1'){
                    element.add(nums[j]);
                    tmp.append(nums[j]).append(",");
                }
            }
            if(!values.contains(tmp.toString())){
                values.add(tmp.toString());
                res.add(element);
            }
        }
        return res;
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> arr = new ArrayList<>();
        subsetsRec(nums,res,0,arr);
        return res;
    }

    private void subsetsRec(int[] nums, List<List<Integer>> res, int i,List<Integer> arr) {
        if(nums.length == i){
            res.add(new ArrayList<Integer>(arr));
            return;
        }
        arr.add(nums[i]);
        subsetsRec(nums,res,i+1,arr);
        arr.remove(arr.size()-1);
        subsetsRec(nums, res, i+1, arr);
    }

    public List<String> letterCasePermutation(String s) {
        List<String> res = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        letterCasePermutationRec(res,s,0,stringBuilder);
        return res;
    }

    private void letterCasePermutationRec(List<String> res, String s, int idx,StringBuilder stringBuilder) {
        if(idx == s.length()){
            res.add(stringBuilder.toString());
            return;
        }
        stringBuilder.append(s.charAt(idx));
        letterCasePermutationRec(res,s,idx+1,stringBuilder);
        if(Character.isLetter(s.charAt(idx))) {
            stringBuilder.delete(idx,stringBuilder.length());
            if (s.charAt(idx)>='A' && s.charAt(idx)<='Z')
                stringBuilder.append((char)(s.charAt(idx)+ ('a' - 'A')));
            else if (s.charAt(idx)>='a' && s.charAt(idx)<='z')
                stringBuilder.append((char)(s.charAt(idx)-( 'a' - 'A')));
            letterCasePermutationRec(res, s, idx + 1, stringBuilder);
        }
    }

    public boolean exist(char[][] board, String word) {
        boolean res = false;
        for(int i = 0 ; i< board.length;i++){
            for(int j =0 ; j<board[i].length;j++){
                if(board[i][j] == word.charAt(0)){
                    res = callDFS(board,i,j,word,0);
                    if(res){
                       return res;
                    }
                }
            }
        }
        return res;
    }

    private boolean callDFS(char[][] board, int x, int y, String word, int index) {
        if(index == word.length()-1 && word.charAt(index) == board[x][y]){
            return true;
        }
        if(word.charAt(index) != board[x][y])
            return false;

        index++;
        char tmp = board[x][y];
        boolean res = false;
        board[x][y] = '#';
        int[] x1 = {-1,1,0,0};
        int[] y1 = {0,0,-1,1};
        for(int dir = 0 ; dir < 4 ; dir++){
            int xDir = x + x1[dir];
            int yDir = y + y1[dir];
            if(xDir < 0 || xDir >= board.length || yDir < 0 || yDir >= board[0].length || board[xDir][yDir] == '#'|| word.charAt(index) != board[xDir][yDir]){
                continue;
            }
            if(callDFS(board,xDir,yDir,word,index)){
                return true;
            }
        }

        board[x][y] = tmp;
        return res;
    }

}
