import java.util.*;

public class StringLocal {

    public static void main(String[] args){

        int[][] grid = {{0,1,0},{0,0,1},{1,1,1},{0,0,0}};
        //int[] arr = {3,2,1};
        StringLocal hl = new StringLocal();
        String[] arr = {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"};
       String  arr2 = "/home//foo/";
        System.out.println(hl.calculate("10+3-2*2"));
    }
    public int calculate3(String s){
        if (s == null || s.isEmpty()) return 0;
        int length = s.length();
        int prev =0 , curr =0, res =0;
        char sign ='+';
        Stack<String> stack = new Stack<>();
        for(int i = 0 ; i < length; i++){
            Character currentChar = s.charAt(i);
            if(Character.isDigit(currentChar)) {
                while (i < length && s.charAt(i) - '0' >= 0 && s.charAt(i) - '0' <= 9) {
                    curr = curr * 10 + s.charAt(i) - '0';
                    i++;
                }
                i--;
                if (sign == '+') {
                    res += curr;
                    prev = curr;
                } else if (sign == '-') {
                    res -= curr;
                    prev = -curr;
                } else if (sign == '*') {
                    res -= prev;
                    res += prev * curr;

                    prev = curr * prev;
                } else if (sign == '/') {
                    res -= prev;
                    res += prev / curr;

                    prev = prev / curr;
                }
                curr = 0;
            }else if(currentChar != '(' || currentChar != ')'){
                sign = currentChar;
            }else if(currentChar == '('){
                res -= prev;
                stack.push(res+"");
                stack.push(prev+"");
                stack.push(sign+"");

                sign = '+';
                res = 0;
            }else if(currentChar == ')'){
                sign = stack.pop().charAt(0);
                prev = Integer.parseInt(stack.pop());
                int last =0;
                switch (sign){
                    case '+': res += prev; break;
                    case '-': res = prev-res; break;
                    case '*': res = prev * res;break;
                    case '/': res = prev/res;break;
                }
                last = res;
                res = Integer.parseInt(stack.pop())+res;
                prev = last;
            }
        }
        return res;
    }

    public int calculateWithOnlyPlusMinusParaenthese(String s) {
        if (s == null || s.isEmpty()) return 0;
        int length = s.length();
        int prev =0 , curr =0, res =0;
        char sign ='+';
        Stack<String> stack = new Stack<>();
        for(int i = 0 ; i < length; i++) {
            Character currentChar = s.charAt(i);
            if (Character.isDigit(currentChar)) {
                while (i < length && s.charAt(i) - '0' >= 0 && s.charAt(i) - '0' <= 9) {
                    curr = curr * 10 + s.charAt(i) - '0';
                    i++;
                }
                i--;
                switch (sign){
                    case '+': res += curr; break;
                    case '-': res -= curr; break;
                }
                curr =0;
            }else if(currentChar == '+' || currentChar == '-'){
                sign = currentChar;
            }else if(currentChar == '('){
                stack.push(res+"");
                stack.push(sign+"");

                res = 0;
                sign = '+';
            }else if(currentChar == ')'){
                sign = stack.pop().charAt(0);
                prev = Integer.parseInt(stack.pop());
                if(sign == '+'){
                    res += prev;
                } else if(sign =='-'){
                    res = prev - res;
                }
            }
        }
        return res;
    }
    public int calculate(String s) {
        if (s == null || s.isEmpty()) return 0;
        int length = s.length();
        int prev =0 , curr =0, res =0;
        char sign ='+';
        for(int i = 0 ; i < length; i++){
            Character currentChar = s.charAt(i);
            if(Character.isDigit(currentChar)) {
                while (i < length && s.charAt(i) - '0' >= 0 && s.charAt(i) - '0' <= 9) {
                    curr = curr * 10 + s.charAt(i) - '0';
                    i++;
                }
                i--;
                if (sign == '+') {
                    res += curr;
                    prev = curr;
                } else if (sign == '-') {
                    res -= curr;
                    prev = -curr;
                } else if (sign == '*') {
                    res -= prev;
                    res += prev * curr;

                    prev = curr * prev;
                } else if (sign == '/') {
                    res -= prev;
                    res += prev / curr;

                    prev = prev / curr;
                }
                curr = 0;
            }else if(!Character.isWhitespace(currentChar)){
               sign = currentChar;
            }
        }
        return res;
    }


        public int calculate1(String s) {
            if (s == null || s.isEmpty()) return 0;
            int length = s.length();
            int currentNumber = 0, lastNumber = 0, result = 0;
            char operation = '+';
           for(int i = 0 ; i < length; i++){
               Character currentChar = s.charAt(i);
               if(Character.isDigit(currentChar)){
                   currentNumber = currentNumber*10 + currentChar ;
               }
               if(!Character.isDigit(currentChar) && !Character.isWhitespace(currentChar) || i ==length-1 ){
                   if(operation == '+' || operation == '-'){
                        result += lastNumber;
                        lastNumber = operation == '-' ? -currentNumber :currentNumber;
                   }else if(operation == '*'){
                       lastNumber = lastNumber * currentNumber;
                   }else if(operation == '/'){
                       lastNumber = lastNumber / currentNumber;
                   }
                   operation = currentChar;
                   currentNumber = 0;
               }
           }
           result +=lastNumber;
           return result;
        }



    public int evalRPN(String[] tokens) {
        Stack<Integer> val = new Stack<>();
        Set<String> operators = new HashSet<>();
        operators.add("*");
        operators.add("-");
        operators.add("*");
        operators.add("/");
        for(String c :tokens){
            if(operators.contains(c)){
                int op1 = val.pop();
                int op2 = val.pop();
                int r = eval(op1 , op2,c) ;
                val.push(r);
            }else{
                val.push(Integer.parseInt(c));
            }
        }
        return val.pop();
    }

    private int eval(int op1, int op2, String c) {
        int res =0;
        switch(c){
            case "+": res = op1+op2;break;
            case "-": res = op2-op1 ;break;
            case "*": res = op2*op1 ;break;
            case "/": res = op2/op1 ;break;
        }
       return res;
    }


    public String minWindow(String s, String t) {
        Map<Character,Integer> templateChars = new HashMap<>();
        for(char c :t.toCharArray()){
            templateChars.put(c,templateChars.getOrDefault(c,0)+1);
        }
        int r = 0, l = 0,len = 0,end =0,start =0;
        Map<Character,Integer> stringMap = new HashMap<>();
        int count = 0;
        while(r < s.length()){
            char c = s.charAt(r);
            if(templateChars.containsKey(c)) {
                stringMap.put(c, stringMap.getOrDefault(c, 0) + 1);
                if(stringMap.get(c) <= templateChars.get(c))
                    count++;
            }
            while(l<r && count >= t.length()){
                char first = s.charAt(l);
                if(r-l+1 <= len || len ==0){
                    len = r-l+1;
                    start = l;
                }

                if(stringMap.containsKey(first)){
                    stringMap.put(first, stringMap.get(first) - 1);
                    if(stringMap.get(first) < templateChars.get(first))
                        count--;
                }
                l++;
            }
            r++;
        }
    return s.substring(start,len);
    }

    public void sortColors(int[] nums) {
        int start = -1 , end = nums.length,mid = start+1;
        while(mid<end){
            if(nums[mid] == 0){
                swap(nums,++start,mid++);
            }else if(nums[mid] == 2){
                swap(nums,--end,mid);
            }else{
                mid++;
            }
        }
    }

    private void swap(int[] nums, int start, int end) {
        int tmp = nums[start];
        nums[start] = nums[end];
        nums[end] = tmp;
    }

    public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[2][len2+1];
        for(int i = 0; i<=len2;i++){
            for(int j = 0; j<=len1;j++){
                if(j ==0){
                    dp[i%2][j] = i;
                }
                if(i ==0){
                    dp[i%2][j] = j;
                }
                if(word1.charAt(j-1) == word2.charAt(i-1)){
                    dp[i%2][j] = dp[(i-1)%2][j-1];
                }else{
                    dp[i%2][j] = 1+ Math.min(Math.min(dp[(i-1)%2][j-1],dp[(i-1)%2][j]),dp[i%2][j-1]);
                }
            }
        }
        return dp[(len2)%2][len1];
    }
    public String simplifyPath(String path) {

        // Initialize a stack
        Stack<String> stack = new Stack<String>();
        String[] components = path.split("/");

        // Split the input string on "/" as the delimiter
        // and process each portion one by one
        for (String directory : components) {

            // A no-op for a "." or an empty string
            if (directory.equals("." +
                    "") || directory.isEmpty()) {
                continue;
            } else if (directory.equals("..")) {

                // If the current component is a "..", then
                // we pop an entry from the stack if it's non-empty
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.add(directory);
            }
        }

        // Stich together all the directory names together
        StringBuilder result = new StringBuilder();
        for (String dir : stack) {
            result.append("/");
            result.append(dir);
        }

        return result.length() > 0 ? result.toString() : "/" ;
    }


    public List<String> fullJustify(String[] words, int maxWidth) {
        int i = 0, n = words.length , j = 0;
        List<String> res = new ArrayList<>();
        while(i < n) {
            j = i+1;
            int currLength = words[i].length();
            while (j < words.length && currLength + words[j].length() + (j - i ) < maxWidth) {
                currLength += words[j].length();
                j++;
            }
            int totalSpaces = maxWidth - currLength;
            if(j-i-1 == 0){
                res.add(leftJustify(totalSpaces,i,j,words));
            }else {
                res.add(middleJustify(totalSpaces,i,j,words));
            }
            i = j ;
        }
        return res;
    }

   /* public String  middleJustify(int totalSpaces,int i,int j,String[] words){

        int spaceSections = j-i-1;
        int spaces = totalSpaces/spaceSections;
        int extraSpaces = totalSpaces % spaceSections;

        StringBuilder sb = new StringBuilder(words[i]);
        for(int k = i+1; k < j; k++){
            int spacesToApply = spaces + (extraSpaces-- > 0?1:0);
            int c = 0;
            while(c<spacesToApply){
                sb.append(" ");
                c++;
            }
            sb.append(words[k]);
        }
        return sb.toString();
    }*/
    private String middleJustify(int totalSpaces, int start, int end, String[] words) {
        int midsections = end-start-1;
        int midspace = totalSpaces/midsections;
        int extraSpaces = totalSpaces%midsections;
        StringBuilder str = new StringBuilder(words[start]);
        for(int i = start+1 ; i < end ; i++){
            int spaces = midspace + (extraSpaces-- > 0 ? 1 :0);
            while(spaces > 0){
                str.append(" ");
                spaces--;
            }
            str.append(words[i]);
        }
        return str.toString();
    }

    private String leftJustify(int totalSpaces, int start, int end, String[] words) {
        int middleSpace  = end-start-1;
        int rightSpaces = totalSpaces-middleSpace;
        StringBuilder str = new StringBuilder(words[start]);
        for(int i = start+1 ; i < end ; i++){
            str.append(" "+words[i]);
        }
        for(int c =0 ;c<rightSpaces;c++){
            str.append(" ");
        }
        return str.toString();
    }

}
