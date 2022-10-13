package premQues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {

    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer,List<Integer>> indexTovalues = new HashMap<>();
        callTree(indexTovalues,root,0,res);
        return res;
    }

    private void callTree(Map<Integer, List<Integer>> indexTovalues, TreeNode root,int index,List<List<Integer>> res) {
        if(root == null){
            return;
        }

        if(!indexTovalues.containsKey(index)){
            List<Integer> val = new ArrayList<>();
            indexTovalues.put(index,val);
            res.add(val);
        }
        indexTovalues.get(index).add(root.val);
        callTree(indexTovalues,root.left,index-1,res);
        callTree(indexTovalues,root.right,index+1,res);
    }


    Node prev = null;
    Node head = null, tail = null;
    public Node treeToDoublyList(Node root) {
        treeToDoublyListRec(root);
        head.left = tail;
        tail.right = head;
        return head;
    }
    public void treeToDoublyListRec(Node root) {
        if(root == null){
            return ;
        }
        treeToDoublyListRec(root.left);
        if(tail != null){
            tail.right = root;
            root.left = tail;
        }else {
            head = root;
        }
        tail = root;
        treeToDoublyListRec(root.right);
    }

    public Node lowestCommonAncestor(Node p, Node q) {
        int pDepth = depth(p);
        int qDepth = depth(q);

        while (pDepth > qDepth){
            p = p.parent;
        }
        while (qDepth > pDepth){
            q = q.parent;
        }
        while(q != p){
            q = q.parent;
            p = p.parent;
        }
        return p;
    }

    private int depth(Node p) {
        int c = 0;
        while(p != null){
            p = p.parent;
            c++;
        }
        return c;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    };

    public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode() {}
        TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
         this.left = left;
        this.right = right;
     }
     }
}
