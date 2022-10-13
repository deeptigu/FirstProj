public class Trie {
    TrieNode root ;
    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode tmp = root;
        for(char c : word.toCharArray()){
            if(tmp.node[c -'a'] == null){
                tmp.node[c -'a'] = new TrieNode();
            }
            tmp = tmp.node[c -'a'];
        }
        tmp.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode tmp = root;
        for(char c : word.toCharArray()){
            if(tmp.node[c -'a'] == null){
                return false;
            }
            tmp = tmp.node[c -'a'];
        }
        return tmp !=null && tmp.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode tmp = root;
        for(char c : prefix.toCharArray()){
            if(tmp.node[c -'a'] == null){
                return false;
            }
            tmp = tmp.node[c -'a'];
        }
        return tmp !=null;
    }
    private class TrieNode{
        TrieNode[] node = new TrieNode[26];
        boolean isEnd;
    }

}
