public class Main_Verily {
    /* Verly ->    adeluna@verily.com
A cord is a string stored as a tree.
In the tree,
* leaf nodes have both a length and a substring
* internal nodes store the total length of all their children,
  but no substring


Cord c:
               [len=8]
             /          \
[ABC, len=3]             [len=5]
                        /        \
              [DEF,len=3]       [GH,len=2]

charAt(cord, index) = character at that index
charAt(c, 0) = A
charAt(c, 3) = D
charAt(c, 4) = E
*/

    /**
     * instanceof
     */

    public static void main(String[] args) {
        Cord left = new LeafNode("ABC");
        Cord right = new InternalNode(
                new LeafNode("DEF"), new LeafNode("GH"));

        Cord c = new InternalNode(left, right);
        System.out.println(getCharForIndex(c, 3));
        System.out.println("cord length: " + c.length());
    }

    static Character getCharForIndex(Cord root, int idx) {
        if (root == null) {
            return null;
        }
        if (root.len < idx) {
            return null;
        }
        if (root instanceof LeafNode) {
            return ((LeafNode) root).getContents().charAt(idx);
        }
        //  int leftLen = 0;
        InternalNode head = (InternalNode) root;
        if (head.getLeft() != null && head.getLeft().len > idx) {
            Cord left = head.getLeft();
            return getCharForIndex(left, idx);
        } else if (head.getRight() != null) {
            Cord right = head.getRight();
            int len = head.getLeft() != null ? head.getLeft().len : 0;
            int contentIdx = idx - len ;
            if (contentIdx < 0 || contentIdx > head.getRight().len) {
                return null;
            }
            return getCharForIndex(right, contentIdx);
        }
        return null;
    }

    public String substring(Cord node, int start, int end) {
        if(start == end) return "";
        if(node instanceof LeafNode) {
            return ((LeafNode) node).getContents().substring(start, end);
        }
        InternalNode internalNode = (InternalNode) node;
        if(start > internalNode.getLeft().len - 1) {
            return substring(internalNode.getRight(), start - internalNode.getLeft().len, end - internalNode.getLeft().len);
        } else if(end - 1 <= internalNode.getLeft().len - 1){
            return substring(internalNode.getLeft(), start, end);
        } else {
            int rightStart = end - ((InternalNode) node).getLeft().len;
            return substring(internalNode.getLeft(), start, internalNode.getLeft().len) + substring(internalNode.getRight(), 0, rightStart);
        }
    }



abstract static class Cord {
    protected int len;

    public int length()  {
        return this.len;
    }
}

static class LeafNode extends Cord {
    private String contents;

    public LeafNode(String contents) {
        this.len = contents.length();
        this.contents = contents;
    }
    public String getContents() {
        return this.contents;
    }
}

static class InternalNode extends Cord {
    private Cord left;
    private Cord right;

    public InternalNode(Cord left, Cord right) {
        this.left = left;
        this.right = right;
        this.len = left.length() + right.length();
    }

    public Cord getLeft() {
        return this.left;
    }

    public Cord getRight() {
        return this.right;
    }
}
}

