import javafx.util.Pair;

import java.util.*;

public class DFS {

    public static void main(String[] args) {

        BFS.TreeNode root = new BFS.TreeNode(1);
        root.left = new BFS.TreeNode(2);
        root.right = new BFS.TreeNode(3);
        root.left.left = new BFS.TreeNode(4);
        root.left.right = new BFS.TreeNode(5);
        root.right.left = new BFS.TreeNode(6);
        root.right.right = new BFS.TreeNode(7);


        TreeNode root1 = new TreeNode(1);
        TreeNode p = root1.left = new TreeNode(2);
        root1.right = new TreeNode(5);
        root1.left.left = new TreeNode(3);
        TreeNode q =root1.left.right = new TreeNode(4);
        //root1.right.left = new TreeNode(7);
        root1.right.right = new TreeNode(6);
      //  root1.left.right.left = new TreeNode(3);
//        root1.left.right.right = new TreeNode(5);

        int[][] grid = {{1,2},{5,2},{4,1},{2,4},{3,1},{3,4}};
        int[] arr = {2, 4, 5, 0, 1, 3};
        DFS hl = new DFS();
      //  hl.lowestCommonAncestor(root1,p,q);
        hl.flatten(root1);
        System.out.println( );

    }

    public void flatten(TreeNode root) {
        if(root == null){
            return;
        }
        flatten(root.left);
        flatten(root.right);
        TreeNode left = root.left;
        if(left != null){
            while(left.right != null){
                left = left.right;
            }
            left.right = root.right;
            root.right = root.left;
            root.left = null;
        }
    }


   /* public boolean sequenceReconstruction(int[] nums, List<List<Integer>> sequences) {
        List<Set<Integer>> adj = new ArrayList<>();
        for(int i =0 ;i<nums.length;i++){
            adj.add(i,new HashSet<>());
        }
        for(int i =0 ;i<sequences.size();i++){
            if(sequences.get(i).get(1) >= nums.length){
                return false;
            }
            if(adj.get(sequences.get(i).get(1)-1)== null){
                return false;
            }
            adj.get(sequences.get(i).get(0)-1).add(sequences.get(i).get(1)-1);
        }
        if(nums.length < adj.size()){
            return false;
        }
        boolean[] isVisited = new boolean[nums.length];
        int count=0;
        boolean goodPath = dfsSeq(adj,nums[0],isVisited,nums,0);

        for(int i =0 ; i < nums.length;i++){


        }
    }*/

   /* private boolean dfsSeq(List<Set<Integer>> adj, int adjIndx, boolean[] isVisited, int[] nums, int numIdx) {
        if(adjIndx == nums[nums.length-1] && adj.get(adjIndx).size() == 0){
            return true;
        }
        isVisited[nums[numIdx]-1] = true;
        if(numIdx < nums.length && (adj.get(nums[numIdx]-1).contains(nums[numIdx])) && isVisited[]){

        }
    }*/

   /* public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        List<PriorityQueue<Pair<Integer,Integer>>> grpIdToItems = new ArrayList<>();
        LinkedList<Integer>[] adj = new LinkedList<>[n];
        int[] indegree = new int[n];
        for(int i = 0 ; i < beforeItems.size();i++){
            indegree[i] = beforeItems.get(i).size();
            for(Integer dependency : beforeItems.get(i)){
                if(adj[dependency] == null){
                    adj[dependency] = new LinkedList<>();
                }
                adj[dependency].add(i);
            }
        }
        createGrpMap(grpIdToItems,group,beforeItems,m);
        for(int i = 0 ; i < grpIdToItems.size()-1 ;i++){
            if(grpIdToItems.get(i).peek().getValue() != 0){

            }
        }


    }*/

    private void createGrpMap(List<PriorityQueue<Pair<Integer,Integer>>> grpIdToItems, int[] group, List<List<Integer>> beforeItems,int m) {
        for(int item = 0; item < group.length;  item++){
            if(group[item] == -1){
                group[item] = m;
            }
            if(grpIdToItems.get(group[item])  == null){
                PriorityQueue<Pair<Integer,Integer>> itemToIndegree = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue));
                grpIdToItems.add(group[item],itemToIndegree);
            }
            grpIdToItems.get(group[item]).add(new Pair<>(item,beforeItems.get(item).size()));
        }

    }


    int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        maxPathSumRec(root);
        return maxSum;
    }
    public int maxPathSumRec(TreeNode root) {
        if(root == null){
            return 0;
        }
        int left = Math.max(maxPathSumRec(root.left),0);
        int right = Math.max(maxPathSumRec(root.right),0);
        maxSum = Math.max(left+right+root.val,maxSum);
        return left > right ? left+root.val : right+root.val;
    }

    public int widthOfBinaryTree(TreeNode root) {

        LinkedList<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        Integer maxWidth = 0;

        queue.addLast(new Pair<>(root, 0));
        while (queue.size() > 0) {
            Pair<TreeNode, Integer> head = queue.getFirst();
            Integer currLevelSize = queue.size();
            Pair<TreeNode, Integer> elem = null;
            for (int i = 0; i < currLevelSize; ++i) {
                elem = queue.removeFirst();
                TreeNode node = elem.getKey();
                if (node.left != null)
                    queue.addLast(new Pair<>(node.left, 2 * elem.getValue()));
                if (node.right != null)
                    queue.addLast(new Pair<>(node.right, 2 * elem.getValue() + 1));
            }

            // calculate the length of the current level,
            //   by comparing the first and last col_index.
            maxWidth = Math.max(maxWidth, elem.getValue() - head.getValue() + 1);
        }

        return maxWidth;
    }




    TreeNode prev = null;
    public boolean isValidBST(TreeNode root) {
        if(root == null){
            return true;
        }
        if(!isValidBST(root.left)){
            return false;
        }
        if(prev != null && prev.val >= root.val){
            return false;
        }
        prev = root;
        return isValidBST(root.right);
    }

    private boolean isValidBST(TreeNode root, int minValue, int maxValue) {
        if(root == null){
            return true;
        }
        if(root.val <= minValue || root.val >= maxValue){
            return false;
        }
         return isValidBST(root.left,minValue,root.val) && isValidBST(root.right,root.val,maxValue);

    }


    public TreeNode constructMaximumBinaryTree(int[] nums) {
        PriorityQueue<int[]> valueIndex = new PriorityQueue<>((a,b)->b[0]-a[0]);
        for(int i =0 ; i < nums.length; i++){
            valueIndex.add(new int[]{nums[i],i});
        }
        TreeNode root = callDfsTree(valueIndex,0,nums.length-1);
        return root;
    }

    private TreeNode callDfsTree(PriorityQueue<int[]> valueIndex, int start, int end) {
        if(start == end){
            return null;
        }
        int[] root = valueIndex.poll();
        TreeNode node = new TreeNode(root[0]);
        node.left = callDfsTree(valueIndex,start,root[1]-1);
        node.right = callDfsTree(valueIndex,root[1]+1,end);
        return node;
    }

    int res = 0;
    public int pathSum2(TreeNode root, int targetSum){
        List<Long> path = new ArrayList<>();
        Integer res = 0;
        pathSumRec1(root,targetSum,path);
        return res;
    }

    private void pathSumRec1( TreeNode root, int targetSum, List<Long> path) {
        if(root == null){
            return;
        }
        if(path.isEmpty()){
            path.add((long)root.val);
        }else{
            long last = path.get(path.size()-1);
            path.add(last+root.val);
        }
        int lastIndex = path.size()-1;
        if(path.get(lastIndex)== targetSum){
            res++;
        }
        for(int i = 0 ; i < path.size()-1 ;i++){
            if((int)(path.get(lastIndex)-path.get(i)) == targetSum){
                res++;
            }
        }
        pathSumRec1(root.left,targetSum,path);
        pathSumRec1(root.right,targetSum,path);
        path.remove(path.size()-1);

    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        pathSumRec(res,root,targetSum,path);
        return res;
    }

    private void pathSumRec(List<List<Integer>> res, TreeNode root, int targetSum,List<Integer> path) {
        if(root == null){
            return;
        }
        path.add(root.val);
        targetSum -= root.val;
        if(root.left == null && root.right == null && targetSum ==0){
            res.add(new ArrayList<>(path));
            path.remove(path.size()-1);
            return;
        }
        pathSumRec(res,root.left,targetSum,path);
        pathSumRec(res,root.right,targetSum,path);
        path.remove(path.size()-1);
    }

    TreeNode rootSub=null;
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        rootSub = subRoot;
        return isSubtreeRec(root,subRoot);
    }

    public boolean isSubtreeRec(TreeNode root, TreeNode subRoot) {
        if(subRoot == null  && root == null){
            return true;
        }
        if((root == null && subRoot != null) || (root != null && subRoot == null)){
            return false;
        }
        if(subRoot != rootSub && root.val != subRoot.val){
            return false;
        }
        boolean res =false;
        if(root.val == subRoot.val){
            res = isSubtreeRec(root.left,subRoot.left) && isSubtreeRec(root.right,subRoot.right);
        }
        if(subRoot == rootSub){
            res = res || isSubtreeRec(root.left,subRoot) || isSubtreeRec(root.right,subRoot);
        }
        return  res;
    }


    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if(root1 == null && root2 == null){
            return null;
        }
        TreeNode res = null;
        if(root1 != null && root2 != null){
            res = new TreeNode(root1.val+root2.val);
            res.left = mergeTrees(root1.left,root2.left);
            res.right = mergeTrees(root1.right,root2.right);
        }
        if(root1 == null){
            res = root2;
        }
        if(root2 == null){
            res = root1;
        }
        return res;

    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        if(p.val > q.val){
            TreeNode tmp = p;
            p = q;
            q = tmp;
        }

        return lowestCommonAncestorRecBS(root,p,q);

    }

    boolean flagP , flagQ;
    private TreeNode lowestCommonAncestorRecBS(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null)
            return null;
        if(root.val < p.val){
            return lowestCommonAncestorRecBS(root.right,p,q);
        }else  if(root.val > q.val){
            return lowestCommonAncestorRecBS(root.left,p,q);
        }
        return root;
    }

    boolean flag1 , flag2;
    public TreeNode lowestCommonAncestorBS(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode res = lowestCommonAncestorRec(root,p,q);
        if(res != null && flag1 && flag2){
            return res;
        }
        return null;
    }

    public TreeNode lowestCommonAncestorRec(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null){
            return null;
        }

        TreeNode left = lowestCommonAncestorRec(root.left,p,q);
        TreeNode right = lowestCommonAncestorRec(root.right,p,q);
        TreeNode res = null;

        if(root == p){
            flag1 = true;
            res = root;
        }
        if(root == q){
            flag2 = true;
            res = root;
        }

        if(left != null && right != null){
            return root;
        }
        if(res == null) {
            if (left != null) {
                return left;
            } else if (right != null) {
                return right;
            }
        }

        return res;
    }
    Integer maxdia = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        if(root == null){
            return 0;
        }
        diameterOfBinaryTreeRec(root);
        return maxdia;
    }
    public int diameterOfBinaryTreeRec(TreeNode root) {
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            return 1;
        }
        int left = diameterOfBinaryTreeRec(root.left);
        int right = diameterOfBinaryTreeRec(root.right);
        maxdia = Math.max(left+right+1,maxdia);
        return Math.max(left,right)+1 ;

    }
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            return 1;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return (left > right ? left : right) +1;
    }

    public boolean hasPathSumRecc(TreeNode root, int targetSum) {
        if(root == null && targetSum == 0){
            return true;
        }
        if(root == null || targetSum == 0){
            return false;
        }

        return hasPathSumRecc(root.left,targetSum-root.val) ||  hasPathSumRecc(root.right,targetSum-root.val);

    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null){
            return true;
        }
        if(p == null || q == null || p.val != q.val){
            return false;
        }
        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }

   // TreeNode prev = null;
    TreeNode leftmost = null;
    public TreeNode connect(TreeNode root) {
        if(root == null){
            return null;
        }
        TreeNode node = root;
        while(node != null){
            leftmost = null;
            TreeNode curr = node;
            prev = null;
            while(curr != null){
                processChild(curr.left);
                processChild(curr.right);
                curr = curr.next;
            }
            node = leftmost;
        }
        return root;
    }

    void processChild(TreeNode child){
        if(prev == null){
            leftmost = child;
        }else {
            prev.next = child;
        }
        prev = child;
    }


    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> ans = new LinkedList();
        Map<Integer,Integer> nodeToPar = new HashMap<>();
        find(root,nodeToPar,target);
        dfs(root,nodeToPar,0,K,ans);
        return ans;
    }
    private void dfs(TreeNode root, Map<Integer, Integer> nodeToPar, int len , int k, List<Integer> ans){
        if(root == null){
            return ;
        }
        len = nodeToPar.get(root.val);
        if(len == k){
           ans.add(root.val);
        }
        dfs(root.left,nodeToPar,len+1,k,ans);
        dfs(root.right,nodeToPar,len+1,k,ans);
    }

    private int find(TreeNode root, Map<Integer, Integer> nodeToPar, TreeNode target) {
        if(root == null){
            return -1;
        }
        if(root == target){
            nodeToPar.put(root.val,0);
            return 0;
        }
        int left = find(root.left,nodeToPar,target);
        if(left >= 0){
            nodeToPar.put(root.val,left+1);
            return left+1;
        }
        int right = find(root.right,nodeToPar,target);
        if(right >= 0){
            nodeToPar.put(root.val,right+1);
            return right+1;
        }
        return -1;
    }



        private void callDfsNext(List<BFS.TreeNode> res, int level, BFS.TreeNode root) {
        if(root == null){
            return;
        }
        if(res.size() <= level){
            res.add(level, root);
        }else {
            res.get(level).next = root;
            res.add(level,root);
        }
        callDfsNext(res,level+1,root.left);
        callDfsNext(res,level+1,root.right);


    }


    private void callDfs(List<List<Integer>> res, int level, BFS.TreeNode root) {
        if(root == null){
            return;
        }
        if(res.size() <= level){
            res.add(level, new ArrayList<>());
        }
        res.get(level).add(root.val);
        callDfs(res,level+1,root.left);
        callDfs(res,level+1,root.right);

    }


    public List<Integer> findMinHeightTrees(int n, int[][] edges) {

        List<Integer> res = new ArrayList<>();
        if(n <= 2){
            for(int i = 0 ;i<n;i++){
                res.add(i);
            }
            return res;
        }
        List<Set<Integer>> adj = new ArrayList<>();
        for(int i = 0 ;i<n;i++){
            adj.add(i,new HashSet<>());
        }
        for(int i =0 ;i<edges.length;i++){
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }

        int remainingNodes = n;
        List<Integer> leaves = new ArrayList<>();
        for(int  i= 0; i < adj.size();i++) {
            if(adj.get(i).size() == 1)
             leaves.add(i);
        }
        remainingNodes -= leaves.size();

        while(remainingNodes > 2){
            List<Integer> newLeaves = new ArrayList<>();
            for(Integer leaf : leaves){
                Integer neighbour = adj.get(leaf).iterator().next();
                adj.get(neighbour).remove(leaf);
                if(adj.get(neighbour).size() == 1) {
                    newLeaves.add(neighbour);
                }
            }
            remainingNodes -= newLeaves.size();
            leaves = newLeaves;
        }
        return leaves;
    }

    public int numberOfPaths(int n, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i = 0 ;i<n;i++){
            adj.add(i,new ArrayList<>());
        }
        for(int i =0 ;i<edges.length;i++){
            adj.get(edges[i][0]-1).add(edges[i][1]-1);
        }
        boolean[] isVisited = new boolean[n];
        List<Set<Integer>> cycles = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        for(int i =0 ; i < n;i++){
            dfs(adj,isVisited,cycles,path,i);
        }
        return cycles.size();
    }

    private void dfs(List<List<Integer>> adj, boolean[] isVisited, List<Set<Integer>> cycles, List<Integer> path, int src) {
        isVisited[src] = true;
        path.add(src);
        for(Integer a : adj.get(src)){
            if(!isVisited[a]){
                dfs(adj,isVisited,cycles,path,a);
            }else if(path.contains(a) && path.size() - 3 == path.indexOf(a)){
                Set<Integer> cycle = new HashSet<>();
                for(int i = path.indexOf(a) ; i<path.size();i++){
                    cycle.add(path.get(i));
                }
                cycles.add(cycle);
            }
        }
        path.remove(path.size()-1);
    }

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
