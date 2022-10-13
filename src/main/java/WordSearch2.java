import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WordSearch2 {
    TrieNode root;
    public List<String> findWords(char[][] board, String[] words) {
        createTrie(words);
        Set<String> res = new HashSet<>();
        for(int i = 0 ; i<board.length;i++){
            for(int j = 0 ; j < board[0].length;j++){
                if(root.node[board[i][j]] != null){
                    checkAndAddWords(board,i,j,res,root);
                }
            }
        }
      return res.stream().collect(Collectors.toList());
    }

    private void checkAndAddWords(char[][] board, int x, int y, Set<String> res,TrieNode trieNode) {
        TrieNode nxt = trieNode.node[board[x][y]-'a'];
        char tmp = board[x][y];
        board[x][y] = '#';
        int[] x1 = {-1,1,0,0};
        int[] y1 = {0,0-1,1};
        for(int i = 0; i<4 ;i++){
            int xf = x1[i]+x;
            int yf= y1[i]+y;
            if(xf >= 0 && xf < board.length && yf >=0 && yf < board[0].length && board[xf][yf] != '#'
                    && nxt.node[board[xf][yf]-'a'] != null){
                    if(nxt.node[board[xf][yf]-'a'].isEnd){
                        res.add(nxt.node[board[xf][yf]-'a'].word);
                    }
                    checkAndAddWords(board,xf,yf,res,nxt);
            }

        }
        board[x][y] = tmp;
    }

    void createTrie(String[] words){
        root = new TrieNode();
        for(String word : words){
            TrieNode tmp = root;
            for(char ch : word.toCharArray()){
                int indx = ch - 'a';
                if(tmp.node[indx] == null){
                    tmp.node[indx] = new TrieNode();
                }
                tmp = tmp.node[indx];
            }
            tmp.isEnd = true;
            tmp.word = word;
        }
    }

    private class TrieNode{
        TrieNode[] node = new TrieNode[26];
        boolean isEnd;
        String word;
    }
}
