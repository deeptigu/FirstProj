public class TreeSolns {

    public static void main(String[] args) {


        TreeSolns hl = new TreeSolns();
        //hl.findMin();
        System.out.println();
    }
    String findKNodes(Node root, int k){
        StringBuilder st = new StringBuilder();
        findKNodes(root, k, st);
        return st.substring(0,st.length());
    }
    void findKNodes(Node root, int k, StringBuilder stringBuilder){
        if(root == null){
            return ;
        }
        if(k == 0 && root != null){
            stringBuilder.append(root.getData()).append(",");
            return ;
        }
        findKNodes(root.leftChild,k-1,stringBuilder);
        findKNodes(root.rightChild,k-1,stringBuilder);
    }
    public static int findHeight(Node root) {
        if(root == null){
            return 0;
        }
        int left = findHeight(root.leftChild);
        int right = findHeight(root.rightChild);
        return (left > right ? left : right)+1;
    }

    public static String findAncestors(Node root, int k) {
        StringBuilder st = new StringBuilder();
        if(findAncestors(root, k, st)){
            return st.substring(0,st.length());
        }
        return "";
    }

    public static boolean findAncestors(Node root, int k, StringBuilder stringBuilder) {
        if (root == null) {
            return false;
        }
        if (root.getData() == k) {
            return true;
        }
        stringBuilder.append(root.getData()).append(",");
        if (root.getData() < k) {
             return findAncestors(root.rightChild, k, stringBuilder);
        } else {
             return findAncestors(root.leftChild, k, stringBuilder);
        }
    }

    public static int findMin(Node root)
    {
       if(root == null){
            return Integer.MAX_VALUE;
       }
       if(root.leftChild != null){
           return findMin(root.leftChild);
       }else
           return root.getData();


    }
}
