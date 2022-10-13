

import java.util.LinkedList;
import java.util.Queue;

public class SerialiseDeserialise {

  public static void main(String[] args){
    TreeNode node = new TreeNode(1);
    node.left = new TreeNode(2);
    node.right = new TreeNode(3);
    node.right.left = new TreeNode(4);
    node.right.right = new TreeNode(5);
    node.right.left.left = new TreeNode(6);
    node.right.left.right = new TreeNode(7);
    SerialiseDeserialise serialiseDeserialise = new SerialiseDeserialise();
    String str = serialiseDeserialise.serialize(node);
    serialiseDeserialise.deserialize(str);
  }

    public String serialize(TreeNode root) {
      StringBuilder res = new StringBuilder();
      Queue<TreeNode> q = new LinkedList<>();
      q.add(root);
      while(!q.isEmpty()){
        TreeNode node = q.poll();
        res.append(node != null ? node.val : "null");
        res.append(",");
        if(node != null) {
          q.add(node.left);
          q.add(node.right);
        }
      }
      return res.toString();
    }
  TreeNode root = null;
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
      String[] values = data.split(",");
      TreeNode root = new TreeNode(Integer.parseInt(values[0]));
      TreeNode node = null, prevNode = null;
      Queue<TreeNode> q = new LinkedList<>();
      q.add(root);
      for (int i =1 ;i< values.length; i++) {
          if (!values[i].equalsIgnoreCase("null")) {
            node = new TreeNode(Integer.parseInt(values[i]));
            q.add(node);
            if(i %2 ==0){
              prevNode.right = node;
            }else {
              prevNode = q.poll();
              prevNode.left = node;
            }
        }else {
            if(i%2 == 1){
              prevNode = q.poll();
            }
          }
      }
      return root;
    }


  public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }





}
